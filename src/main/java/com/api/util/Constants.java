package com.api.util;

/**
 * 
 * @Description： 常量
 * 
 * @author [ Wenfeng.Huang ] on [2019年11月12日下午7:09:00]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
public interface Constants {

	/** 鉴权服务器返回码 */
	String AUTHENTICATION_CODE = "error";

	/** 鉴权服务器鉴权成功编码 */
	String AUTHENTICATION_SUCCESS = "00000";

	/** 系统错误 */
	String SYSTEM_EXCEPTION = "90001";

	/** 系统错误 */
	String SYSTEM_EXCEPTION_MESSAGE = "System error.";

	/** 参数错误 */
	String PARAMS_FAILURE = "90004";

	/** 参数错误 */
	String PARAMS_FAILURE_MESSAGE = "Parameter error";

	/** 无效的服务编码 */
	String PARAMS_PROCESS_CODE_FAILURE = "90005";

	/** 无效的服务编码 */
	String PARAMS_PROCESS_CODE_FAILURE_FAILURE_MESSAGE = "processCode invalid";

	/** POST请求方式 */
	String POST_REMOTE_REQUEST_MODE = "POST";

}
