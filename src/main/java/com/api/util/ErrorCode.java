package com.api.util;

import cn.hutool.core.util.StrUtil;

/**
 * 
 * @Description： 错误码定义枚举
 * 
 * @author [ Wenfeng.Huang ] on [2019年8月23日下午6:48:31]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
public enum ErrorCode {
	SUCCESS("0000", "操作成功"),
	SYSTEM_EXCEPTION("90001", "系统错误！"),
    PROTOCOL_UNDEFINE("90002", "协议不支持"),
    ACCESSIMPL_INSTANCE_ERROR("90003", "构造接出服务出错！"),
    END_POINT_IS_NULL("90004", "远程服务调用地址为空！"),
    REMOTE_CALL_TIMEOUT("90005", "远程服务[{0}]请求处理超时,耗时：[{1}]ms"),
    REMOTE_UNDEFINE("90006", "远程服务[{0}]未定义！"),
    REQUESTMETHODNOTSUPPORTED("90007", "请求方式不支持"),
    MEDIATYPENOTSUPPORTED("90008", "请求协议格式不支持"),
	PARAMETER_ERROR("10100", "参数[{}]错误"), 
	INTERFACE_REQUEST_ERROR("10102", "请求[{}]接口失败"), 
	COMMON_ERROR("10103","[{}]"),;

	private String msg;
	private String code;

	private ErrorCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getMsg() {
		return this.msg;
	}

	public String getCode() {
		return this.code;
	}

	public String getFormat(Object... str) {
		return StrUtil.format(this.getMsg(), str);
	}
	
	public static void main(String[] args) {
		System.err.println(ErrorCode.PARAMETER_ERROR.getCode());
		System.err.println(ErrorCode.PARAMETER_ERROR.getFormat("orderId"));
	}
}
