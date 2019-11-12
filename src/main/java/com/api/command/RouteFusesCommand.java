package com.api.command;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.chain.Context;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.api.exception.BaseException;
import com.api.srvctl.Route;
import com.api.util.ErrorCode;
import com.api.util.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Description： 路由熔断
 * 
 * @author [ Wenfeng.Huang ] on [2019年11月12日下午4:59:40]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Component
@Slf4j
public class RouteFusesCommand implements FlowManageCommand {

	private long SEVICE_CALL_TIMEOUT = 10000;

	/**
	 * 路由熔断:服务调用，处理服务超时，服务异常，提前返回异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Context context) throws Exception {
		long startTime = System.currentTimeMillis();
		String appId = (String) context.get("appId");
		String processCode = (String) context.get("processCode");
		Map<String, Object> busiReqParams = buildBusiReqParam(context);
		Map<String, Object> serviceCallMap = serviceCall(appId, processCode, busiReqParams);
		if (!"0000".equals(serviceCallMap.get("resultCode"))) {
			log.info("路由熔断流程结束，传出参数为：{}", context);
			return true;
		}
		context.put("responseStr", (String) serviceCallMap.get("responseStr"));
		log.info("路由熔断流程结束, 请求流水:{}, 返回码:{}, 返回描述:{}, 消耗时间:{}", context.get("reqSeq"), context.get("respCode"),
				context.get("respDesc"), System.currentTimeMillis() - startTime);
		return false;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private Map<String, Object> serviceCall(String appId, String processCode, Map<String, Object> busiReqParams)
			throws Exception {

		// 进行服务路由计算
		Route route = getRoute(appId, processCode);

		ExecutorService exec = Executors.newFixedThreadPool(1);
		// 设置任务
		SrvProcessCallable srvProcessCallable = new SrvProcessCallable(processCode, route, busiReqParams);

		Map<String, Object> retMap = new HashMap<>();
		long beginTime = System.currentTimeMillis();
		try {
			Future<Map<String, Object>> future = exec.submit(srvProcessCallable);
			// 任务处理超时时间
			retMap = future.get(SEVICE_CALL_TIMEOUT, TimeUnit.MILLISECONDS);
		} catch (TimeoutException ex) {
			log.error("任务处理超时", ex);
			return Result.error(ErrorCode.REMOTE_CALL_TIMEOUT, processCode, System.currentTimeMillis() - beginTime);
		} catch (Exception ex) {
			Throwable rootCause = ExceptionUtils.getRootCause(ex);
			log.error("RouteFusesCommand任务处理异常,服务名称：{}，请求参数信息{},错误信息：{}, 根源性错误信息:{}", processCode,
					JSON.toJSONString(busiReqParams), ex, rootCause);
			if (rootCause instanceof BaseException) {
				throw ex;
			} else {
				throw new BaseException("9001", "RouteFusesCommand任务处理异常");
			}
		} finally {
			// 关闭线程池
			exec.shutdown();
		}
		return retMap;
	}

	private Route getRoute(String appId, String processCode) throws BaseException {
		//TODO 通过appId和processCode识别对应路由规则
		Route route = new Route();
		route.setProtocol("dubbo");
		return route;
	}

	/**
	 * 构建业务参数Map
	 * 
	 * @param context
	 * @return
	 */
	private Map<String, Object> buildBusiReqParam(Context context) {
		String processCode = (String) context.get("processCode");
		String requestStr = (String) context.get("requestStr");
		Map<String, Object> busiReqParams = new HashMap<String, Object>();
		busiReqParams.put("processCode", processCode);
		busiReqParams.put("requestStr", requestStr);
		return busiReqParams;
	}

}
