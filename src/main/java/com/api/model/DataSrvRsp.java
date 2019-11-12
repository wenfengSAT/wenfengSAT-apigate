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
 * @Description： 数据服务应答参数
 * 
 * @author [ Wenfeng.Huang ] on [2019年10月11日上午10:28:32]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Setter
@Getter
public class DataSrvRsp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("ResponseHeader")
	private ResponseHeader responseHeader;

	/**
	 * 返回结果
	 */
	@JsonProperty("Msgbody")
	private Map<String, Object> msgBody;

	public DataSrvRsp() {
		super();
	}

	public DataSrvRsp(ResponseHeader responseHeader, String msgBody) {
		Map<String, Object> msgBodyMap = new HashMap<String, Object>();
		this.responseHeader = responseHeader;
		if (StringUtils.isNoneBlank(msgBody)) {
			msgBodyMap = JSON.parseObject(msgBody);
		}
		this.msgBody = msgBodyMap;
	}
}
