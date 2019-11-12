package com.api.srvctl.handler;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.api.srvctl.Route;
import com.api.srvctl.SrvAccessHandler;
import com.api.util.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Description： 微服务远程调用
 * 
 * @author [ Wenfeng.Huang ] on [2019年11月12日下午7:16:15]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Slf4j
@Component
public class DubboSrvAccessHandler implements SrvAccessHandler {

	@Override
	public Map<String, Object> accessSrv(String processCode, Route route, Map<String, Object> busiReqParams)
			throws Exception {
		log.debug("微服务远程调用...");
		return Result.ok().result("{'key':'value'}");
	}
}
