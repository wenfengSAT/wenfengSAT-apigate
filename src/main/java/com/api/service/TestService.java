package com.api.service;

import com.api.model.TestReq;
import com.api.util.Result;

/**
 * 
 * @Description： 测试接口
 * 
 * @author [ Wenfeng.Huang ] on [2019年11月12日下午4:04:10]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
public interface TestService {

	/**
	 * 
	 * @Description： 测试方法
	 * 
	 * @author [ Wenfeng.Huang ]
	 * @Date [2019年11月12日下午4:04:48]
	 * @return
	 *
	 */
	Result testMethod(TestReq req);
}
