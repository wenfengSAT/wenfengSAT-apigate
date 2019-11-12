package com.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.api.enumerate.InvokeState;
import com.api.model.DataSrvReq;
import com.api.model.DataSrvRsp;
import com.api.model.RequestHeader;
import com.api.model.ResponseHeader;

import cn.hutool.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Description： 服务公共入口
 * 
 * @author [ Wenfeng.Huang ] on [2019年10月11日上午10:09:27]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Slf4j
@RestController
@RequestMapping("sv")
public class CommonController {

	@Value("${dubbo.application.name}")
	private String applicationName;
	@Value("${dubbo.registry.id}")
	private String registryId;
	@Value("${dubbo.registry.address}")
	private String registryAddress;

	/**
	 * 
	 * @Description： 接口公共入口
	 * 
	 * @author [ Wenfeng.Huang ]
	 * @Date [2019年11月12日下午8:14:17]
	 * @param dataSrvReq
	 * @return
	 *
	 */
	@RequestMapping(value = "/v1/services/api", method = RequestMethod.POST, consumes = "application/json")
	public <T> DataSrvRsp queryData(@RequestBody DataSrvReq dataSrvReq) {
		String processCode = dataSrvReq.getRequestHeader().getProcessCode();
		Map<String, String> msgBody = new HashMap<String, String>();
		InvokeState invokeState;
		try {
			invokeState = InvokeState.valueOf(processCode);
		} catch (Exception e) {
			msgBody.put("resultCode", "9999");
			msgBody.put("resultDesc", "服务编码不存在！");
			return buildDataSrvRsp(dataSrvReq, msgBody);
		}
		String requestStr = JSON.toJSONString(dataSrvReq.getMsgBody());
		long begin = System.currentTimeMillis();
		JSONObject reqJson = JSON.parseObject(requestStr);
		T service = getDubboService(invokeState.version, invokeState.service);
		if (Objects.isNull(invokeState.reqObj)) {
			msgBody = ReflectUtil.invoke(service, invokeState.method);
		} else {
			Object reqObj = JSON.toJavaObject(reqJson, invokeState.reqObj);
			msgBody = ReflectUtil.invoke(service, invokeState.method, reqObj);
		}
		DataSrvRsp result = buildDataSrvRsp(dataSrvReq, msgBody);
		log.info("{} 耗时：{}", invokeState.desc, System.currentTimeMillis() - begin);
		if (log.isDebugEnabled()) {
			log.debug("{} result:{}", invokeState.method, JSON.toJSONString(result));
		}
		return result;
	}

	/**
	 * 
	 * @Description： 动态获取dubbo远程服务
	 * 
	 * @author [ Wenfeng.Huang ]
	 * @Date [2019年10月9日下午4:52:17]
	 * @param version
	 * @param interfaceClass
	 * @return
	 *
	 */
	public <T> T getDubboService(String version, Class<?> interfaceClass) {
		ApplicationConfig application = new ApplicationConfig();
		application.setName(applicationName);
		RegistryConfig registryConfig = new RegistryConfig();
		registryConfig.setAddress(registryAddress);
		ReferenceConfig<T> rc = new ReferenceConfig<T>();
		rc.setApplication(application);
		rc.setInterface(interfaceClass);
		rc.setId(registryId);
		rc.setTimeout(60000);// 超时时间
		rc.setRetries(-1);// 重试次数
		rc.setVersion(version);
		return rc.get();
	}

	/**
	 * 
	 * @Description： 组装返回参数
	 * 
	 * @author [ Wenfeng.Huang ]
	 * @Date [2019年11月12日下午8:15:00]
	 * @param dataSrvReq
	 * @param msgBody
	 * @return
	 *
	 */
	private DataSrvRsp buildDataSrvRsp(DataSrvReq dataSrvReq, Map<String, String> msgBody) {
		String code = msgBody.get("resultCode");
		String desc = msgBody.get("resultDesc");
		String msgbody = msgBody.get("responseStr");
		ResponseHeader.RetInfo retInfo = new ResponseHeader.RetInfo(code, desc);
		ResponseHeader responseHeader = new ResponseHeader();
		RequestHeader requestHeader = Objects.isNull(dataSrvReq) ? new RequestHeader() : dataSrvReq.getRequestHeader();
		responseHeader.setRspSeq(requestHeader == null ? "" : requestHeader.getReqSeq());
		responseHeader.setRspTime(new Date());
		responseHeader.setRetInfo(retInfo);
		if (Objects.nonNull(dataSrvReq) && Objects.nonNull(dataSrvReq.getRequestHeader())) {
			responseHeader.setProcessCode(dataSrvReq.getRequestHeader().getProcessCode());
		}
		return new DataSrvRsp(responseHeader, msgbody);
	}

}
