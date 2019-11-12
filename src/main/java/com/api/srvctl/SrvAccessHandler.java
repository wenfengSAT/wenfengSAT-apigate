package com.api.srvctl;

import java.util.Map;

/**
 * 
 * @Description： 远程服务调用服务接口，根据服务名，路由规则匹配对应的远程服务发起业务调用
 * 
 * @author [ Wenfeng.Huang ] on [2019年11月12日下午6:53:05]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
public interface SrvAccessHandler {
	
	Map<String,Object> accessSrv(String processCode, Route route, Map<String, Object> busiReqParams) throws Exception;
	
}
