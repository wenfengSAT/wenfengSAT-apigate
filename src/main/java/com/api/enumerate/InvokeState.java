package com.api.enumerate;

import com.api.service.*;
import com.api.model.*;

/**
 * 
 * @Description： 业务编码-服务接口
 * 
 * @author [ Wenfeng.Huang ] on [2019年10月11日上午9:39:54]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
public enum InvokeState {
	/**
     * 测试
     */
	test("test", TestService.class, "testMethod", TestReq.class, "1.0.0", "测试接口"),
    ;
	
	public String processCode;// 服务编码
	public Class<?> service;// 服务接口类
	public String method;// 服务调用方法
	public Class<?> reqObj;// 服务调用方法请求参数实体类
	public String version;// 服务版本号
	public String desc;// 接口描述

	InvokeState(String processCode, Class<?> service, String method, Class<?> reqObj, String version, String desc) {
		this.processCode = processCode;
		this.service = service;
		this.method = method;
		this.reqObj = reqObj;
		this.version = version;
		this.desc = desc;
	}
}