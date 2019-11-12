package com.api.command;

import java.util.Map;
import java.util.concurrent.Callable;

import com.api.srvctl.Route;
import com.api.srvctl.SrvAccessFactory;
import com.api.srvctl.SrvAccessHandler;

/**
 * 
 * @Description： 有返回值线程业务处理类
 * 
 * @author [ Wenfeng.Huang ] on [2019年11月12日下午7:00:18]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@SuppressWarnings("rawtypes")
public class SrvProcessCallable implements Callable {

	private final String processCode;
	private final Route route;
	private final Map<String, Object> busiReqParams;

	public SrvProcessCallable(String processCode, Route route, Map<String, Object> busiReqParams) {
		this.processCode = processCode;
		this.route = route;
		this.busiReqParams = busiReqParams;
	}

	@Override
	public Object call() throws Exception {
		SrvAccessHandler srvAccess = SrvAccessFactory.getSrvService(processCode, route);
		Map<String, Object> accessSrv = srvAccess.accessSrv(processCode, route, busiReqParams);
		return accessSrv;
	}
}
