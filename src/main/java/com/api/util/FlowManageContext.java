package com.api.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ChainBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.api.command.FlowManageCommand;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Description： 流程管理职责链执行类
 * @author [ Wenfeng.Huang ] on [2019年11月12日下午8:19:53]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class FlowManageContext {


    // 线程安全职责链缓存
    public static ConcurrentHashMap<String, ChainBase> commandsCache = new ConcurrentHashMap<String, ChainBase>();

    /**
     * 执行职责链
     * 
     * @param context
     * @return
     * @throws Exception
     */
    public boolean execute(Context context) throws Exception {
        ChainBase chainBase = this.getChainBase(context);
        boolean result = chainBase.execute(context);
        return result;
    }

    /**
     * 获取职责链
     * 
     * @param context
     * @return
     * @throws ClassNotFoundException
     */
    public ChainBase getChainBase(Context context) throws ClassNotFoundException {
        Object appIdObj = context.get("appId");
        if (Objects.isNull(appIdObj)) {
            log.warn("流程管理职责链执行:当前传入AppId为空");
            return null;
        }
        String appId = String.valueOf(appIdObj);
        // 判断当前appId是否已经生成了注册了职责链
        ChainBase oldChainBase = commandsCache.get(appId);
        ChainBase returnChainBase;
        if (Objects.isNull(oldChainBase)) {
            ChainBase newChainBase = this.generateChainBase(context);
            returnChainBase = newChainBase;
            commandsCache.put(appId, newChainBase);
        } else {
            returnChainBase = oldChainBase;
        }
        return returnChainBase;
    }
    
    /**
     * 生成职责链对象
     * 
     * @param context
     * @return
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("static-access")
    private ChainBase generateChainBase(Context context) throws ClassNotFoundException {
		List<String> excuteCommandList = this.analysisCommandJsonArray(getDefaultCommand());
        ChainBase newChainBase = new ChainBase();
        for (String className : excuteCommandList) {
            Class<?> clazz = Class.forName(className);
            String[] classNames = className.split("\\.");
            String clazzName = classNames[classNames.length - 1];
            Object registerBean = LoadBeanUtils.registerBean(this.toLowerCaseFirstOne(clazzName), clazz);
            if (registerBean instanceof FlowManageCommand) {
                newChainBase.addCommand((FlowManageCommand) registerBean);
            }
        }
        return newChainBase;
    }

    /**
     * 
     * @Description：获取默认流程，如果流程新增或者删除则需要修改此方法
     * 
     * @author [ Wenfeng.Huang@desay-svautomotive.com ]
     * @Date [2019年10月23日下午12:14:11]
     * @return
     *
     */
    public JSONArray getDefaultCommand() {
        JSONArray commandJsonArray = new JSONArray();
        commandJsonArray.add(JSONObject.parseObject("{'command':'com.api.command.ContextPreHandleCommand','order':'1','isInput':'true'}"));
        commandJsonArray.add(JSONObject.parseObject("{'command':'com.api.command.ParamCheckCommand','order':'2','isInput':'true'}"));
        commandJsonArray.add(JSONObject.parseObject("{'command':'com.api.command.AccessControlCommand','order':'3','isInput':'true'}"));
        commandJsonArray.add(JSONObject.parseObject("{'command':'com.api.command.BlackAndWhiteControlCommand','order':'4','isInput':'true'}"));
        commandJsonArray.add(JSONObject.parseObject("{'command':'com.api.command.RouteFusesCommand','order':'5','isInput':'true'}"));
        commandJsonArray.add(JSONObject.parseObject("{'command':'com.api.command.ContextSufHandleCommand','order':'6','isInput':'true'}"));
        return commandJsonArray;
    }
    
    /**
     * 解析职责链Json数组获取所需执行职责链数据
     * 
     * @param commandJsonArray
     *            设置的JSON数组数据
     * @return
     */
    public static List<String> analysisCommandJsonArray(JSONArray commandJsonArray) {
        List<Map<String, String>> commandJsonList = sortCommandJsonArray(commandJsonArray);
        List<String> getCommandList = new ArrayList<String>();
        for (Map<String, String> commandJsonMap : commandJsonList) {
            getCommandList.add(commandJsonMap.get("command"));
        }
        return getCommandList;
    }

    /**
     * 对职责链执行顺序进行排序
     * 
     * @param commandJsonArray
     *            设置的JSON数组数据
     * @return
     */
    @SuppressWarnings("unchecked")
    private static List<Map<String, String>> sortCommandJsonArray(JSONArray commandJsonArray) {
        try {
            List<Map<String, String>> commandJsonList = new ArrayList<Map<String, String>>();
            for (int i = 0; i < commandJsonArray.size(); i++) {
                JSONObject commandJsonObj = commandJsonArray.getJSONObject(i);
				Map<String, String> commandJsonMap = JSONObject.toJavaObject(commandJsonObj, Map.class);
                commandJsonList.add(commandJsonMap);
            }
            Collections.sort(commandJsonList, new Comparator<Map<String, String>>() {
                public int compare(Map<String, String> o1, Map<String, String> o2) {
                    return o1.get("order").compareTo(o2.get("order"));
                }
            });
            return commandJsonList;
        } catch (Exception e) {
            log.error("Analysis command json array error ", e);
        }
        return new ArrayList<Map<String, String>>();
    }

    /**
     * 首字母转小写
     * 
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
}
