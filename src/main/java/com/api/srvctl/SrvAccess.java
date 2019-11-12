package com.api.srvctl;

import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.api.exception.BaseException;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Description： 调用目标服务
 * 
 * @author [ Wenfeng.Huang ] on [2019年11月12日下午7:24:50]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Component
@Slf4j
public class SrvAccess implements SrvAccessHandler {

	private RestTemplate restTemplate = new RestTemplate();

	private final String REQUEST_SOURCE_KEY = "request_source_key";
	private final String REQUEST_SOURCE_VALUE = "Open-api";

	public Map<String, Object> accessSrv(Map<String, Object> busiReqParams) throws Exception {
		log.info("SrvAccess busiReqParams：{}", busiReqParams);
		HttpEntity<String> requestEntity = this.buildRequestEntity(String.valueOf(busiReqParams.get("requestStr")));
		String url = this.getRoutingUrlByProcessCode(String.valueOf(busiReqParams.get("processCode")));
		String responseStr = this.postRequest(url, requestEntity);
		busiReqParams.put("responseStr", responseStr);
		return busiReqParams;
	}

	private String getRoutingUrlByProcessCode(String processCode) throws BaseException {
		// TODO 通过processCode获取URL...
		String url = "";
		log.info("SrvAccess url：{}", url);
		return url;
	}

	/**
	 * 请求服务
	 * 
	 * @param url
	 *            请求地址
	 * @param requestEntity
	 *            请求实体
	 * @return
	 */
	public String postRequest(String url, HttpEntity<String> requestEntity) {
		String responseStr = "";
		try {
			ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
			responseStr = stringResponseEntity.getBody();
			log.info("SrvAccess响应参数：{}", stringResponseEntity.getBody());
		} catch (Exception e) {
			log.error("请求服务发生异常：", e);
		}
		return responseStr;
	}

	/**
	 * 构建请求实体
	 * 
	 * @param requestStr
	 *            请求报文
	 * @return
	 */
	public HttpEntity<String> buildRequestEntity(String requestStr) {
		log.info("SrvAccess请求参数：{}", requestStr);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.set(REQUEST_SOURCE_KEY, REQUEST_SOURCE_VALUE);
		HttpEntity<String> requestEntity = new HttpEntity<String>(requestStr, headers);
		return requestEntity;
	}

	@Override
	public Map<String,Object> accessSrv(String processCode, Route route, Map<String, Object> busiReqParams) throws Exception {
		return null;
	}
}
