package com.api.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @Description： 数据服务请求参数
 * 
 * @author [ Wenfeng.Huang ] on [2019年10月11日上午10:28:18]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Setter
@Getter
public class DataSrvReq implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("RequestHeader")
	private transient RequestHeader requestHeader;

	/**
	 * 传入参数
	 */
	@JsonProperty("Msgbody")
	private Map<String, Object> msgBody;

	public DataSrvReq() {
		super();
	}

	public DataSrvReq(RequestHeader requestHeader, String msgBody) {
		Map<String, Object> msgBodyMap = new HashMap<String, Object>();
		this.requestHeader = requestHeader;
		if (StringUtils.isNoneBlank(msgBody)) {
			msgBodyMap = JSON.parseObject(msgBody);
		}
		this.msgBody = msgBodyMap;
	}
}
