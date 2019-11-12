package com.api.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @Description： 数据服务请求参数头
 * 
 * @author [ Wenfeng.Huang ] on [2019年10月11日上午10:28:45]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Setter
@Getter
public class RequestHeader {
	/**
	 * 请求流水
	 */
	private String reqSeq;

	/**
	 * 应用密钥:加解密密钥
	 */
	private String appKey;

	/**
	 * 接入令牌:接入令牌需要OAuth2.0授权后商户获取
	 */
	private String accessToken;

	/**
	 * 应用标识
	 */
	private String appId;

	/**
	 * 用户标识
	 */
	private String openId;

	/**
	 * 业务编码:对外暴露的接口
	 */
	private String processCode;

	/**
	 * 签名:签名值，对参数进行签名，防篡改
	 */
	private String sign;

	/**
	 * 时间戳
	 */
	private Date timestamp;
}
