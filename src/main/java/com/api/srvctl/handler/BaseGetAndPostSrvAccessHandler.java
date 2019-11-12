package com.api.srvctl.handler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.api.exception.BaseException;
import com.api.srvctl.Route;
import com.api.srvctl.SrvAccessHandler;
import com.api.util.Constants;
import com.api.util.ErrorCode;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Description：Get,Post请求通用远程调用方式处理类
 * @author [ Wenfeng.Huang ] on [2019年11月12日下午7:11:26]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Slf4j
@Component
public class BaseGetAndPostSrvAccessHandler implements SrvAccessHandler {

	@Override
	public Map<String, Object> accessSrv(String processCode, Route route, Map<String, Object> busiReqParams) throws Exception {

		String endPoint = route.getEndPoint();
		if (StringUtils.isBlank(endPoint)) {
			log.warn("服务编码:{}对应的远程服务调用地址为空", processCode);
			throw new BaseException(ErrorCode.END_POINT_IS_NULL.getCode(), ErrorCode.END_POINT_IS_NULL.getMsg());
		}

		String requestStr = String.valueOf(busiReqParams.get("requestStr"));
		String routeValue = route.getRouteValue();
		String remoteRequestMode = Constants.POST_REMOTE_REQUEST_MODE;
		if (StringUtils.isNotBlank(routeValue)) {
			// 默认设置POST请求

		}

		String responseStr = "";
		if (Constants.POST_REMOTE_REQUEST_MODE.equals(remoteRequestMode)) {
			responseStr = this.remotePostRequest(processCode, endPoint, requestStr);
		} else {
			responseStr = this.remoteGetRequest(processCode, endPoint, requestStr);
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (StringUtils.isBlank(responseStr)) {
			returnMap.put("resultCode", "-1");
			returnMap.put("resultDesc", "请求失败");
		} else {
			returnMap.put("resultCode", "0000");
			returnMap.put("responseStr", responseStr);
		}
		return returnMap;
	}

	/**
	 * POST远程调用
	 * 
	 * @param processCode
	 *            应用服务编号
	 * @param endPoint
	 *            远程调用地址
	 * @param requestStr
	 *            请求参数
	 * @return
	 */
	private String remotePostRequest(String processCode, String endPoint, String requestStr) {
		log.info("服务编码:{}对应的请求URL为:{}, 调用方式为POST, 请求参数为:{}", processCode, endPoint, requestStr);
		JSONObject requestJsonObj = JSONObject.parseObject(requestStr);
		return HttpUtil.post(endPoint, requestJsonObj);
	}

	/**
	 * GET远程调用
	 * 
	 * @param processCode
	 *            应用服务编号
	 * @param endPoint
	 *            远程调用地址
	 * @param requestStr
	 *            请求参数
	 * @return
	 */
	private String remoteGetRequest(String processCode, String endPoint, String requestStr) {
		JSONObject requestJsonObj = JSONObject.parseObject(requestStr);
		StringBuilder urlStringSb = new StringBuilder();
		urlStringSb.append(endPoint).append("?");
		Iterator<String> iterator = requestJsonObj.keySet().iterator();
		while (iterator.hasNext()) {
			String requestKey = iterator.next();
			String requestValue = requestJsonObj.getString(requestKey);
			// 替换特殊字符
			requestValue = this.replaceSpecialChar(requestValue);
			urlStringSb.append(requestKey).append("=").append(requestValue);
			if (iterator.hasNext()) {
				urlStringSb.append("&");
			}
		}
		String urlString = urlStringSb.toString();
		log.info("服务编码:{}对应的请求URL为:{}, 调用方式为GET", processCode, urlString);
		String responseStr = HttpUtil.get(urlString);
		return responseStr;
	}

	/**
	 * 替换特殊字符
	 * 
	 * @param requestValue
	 *            请求参数值
	 * @return
	 */
	private String replaceSpecialChar(String requestValue) {
		return requestValue.replace("&", "%26").replace("+", "%2B").replace("/", "%2F").replace("?", "%3F")
				.replace("%", "%25").replace("#", "%23").replace("=", "%3D");

	}

}
