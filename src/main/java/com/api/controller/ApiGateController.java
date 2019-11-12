package com.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.api.invokectl.HttpClient;
import com.api.model.DataSrvReq;
import com.api.model.DataSrvRsp;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Description： 服务公共入口
 * 
 * @author [ Wenfeng.Huang ] on [2019年10月11日上午10:09:27]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Slf4j
@RestController
@RequestMapping("sv")
public class ApiGateController {

	@Autowired
	private HttpClient httpInvoke;

	@RequestMapping(value = "/v2/services/api", method = RequestMethod.POST, consumes = "application/json")
	public <T> DataSrvRsp queryData(@RequestBody DataSrvReq dataSrvReq) {
		log.debug("服务处理...");
		return httpInvoke.invoke(dataSrvReq);
	}

}
