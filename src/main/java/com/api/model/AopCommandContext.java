package com.api.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @Description： 统一数据传递对象
 * 
 * @author [ Wenfeng.Huang ] on [2019年11月12日下午5:15:59]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Getter
@Setter
public class AopCommandContext extends ContextBase implements Context {

	public static final Object RET_SUCCESS = "0000";

	private static final long serialVersionUID = 2891920681939104966L;

	/** 入参原始报文 */
	@NotBlank(message = "入参原始报文不能为空", groups = { Default.class })
	private String requestStr;

	/** 入参响应报文 */
	@NotBlank(message = "入参响应报文不能为空", groups = { Default.class })
	private String responseStr;

	/** 请求流水 */
	@NotBlank(message = "请求流水不能为空", groups = { Default.class })
	private String reqSeq;

	/** 应用密钥 */
	@NotBlank(message = "应用密钥不能为空", groups = { Default.class })
	private String appKey;

	/** 接入令牌 */
	@NotBlank(message = "接入令牌不能为空", groups = { Default.class })
	private String accessToken;

	/** 应用标识 */
	@NotBlank(message = "应用标识不能为空", groups = { Default.class })
	private String appId;

	/** 用户标识 */
	@NotBlank(message = "用户标识不能为空", groups = { Default.class })
	private String openId;

	/** 业务编码 */
	@NotBlank(message = "业务编码不能为空", groups = { Default.class })
	private String processCode;

	/** 签名 */
	@NotBlank(message = "签名不能为空", groups = { Default.class })
	private String sign;

	/** 接口版本 */
	@NotBlank(message = "接口版本号不能为空", groups = { Default.class })
	private String version;

	/** 时间 */
	@NotNull(message = "时间不能为空", groups = { Default.class })
	private Date timestamp;

	/** 返回码 */
	@NotBlank(message = "返回码不能为空", groups = { Default.class })
	private String respCode;

	/** 返回信息 */
	@NotBlank(message = "返回信息不能为空", groups = { Default.class })
	private String respDesc;

	/** 请求URL */
	@NotBlank(message = "请求URL不能为空", groups = { Default.class })
	private String URL;

	/** 请求URI */
	@NotBlank(message = "请求URI不能为空", groups = { Default.class })
	private String URI;

	/** 请求IP */
	@NotBlank(message = "请求=ip不能为空", groups = { Default.class })
	private String ip;
	/** 请求URI */
	@NotBlank(message = "请求port不能为空", groups = { Default.class })
	private String port;

	public Date getTimestamp() {
		return ObjectUtil.isNull(timestamp) ? timestamp : DateUtil.date(timestamp);
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = DateUtil.date(timestamp);
	}
}
