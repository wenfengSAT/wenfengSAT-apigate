package com.api.srvctl;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @Description： 服务路由对象
 * 
 * @author [ Wenfeng.Huang ] on [2019年11月12日下午8:18:08]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Setter
@Getter
public class Route {

	/**
	 * 业务中心编码，如：TSP,LION,CMS
	 */
	private String modeCode;

	/**
	 * 采用协议 HTTP,dobbo
	 */
	private String protocol;

	/**
	 * 远程服务调用地址
	 */
	private String endPoint;

	/**
	 * 接出服务实现类
	 */
	private String accessImpl;

	/**
	 * 路由关键字
	 */
	private String routeKey;

	/**
	 * 路由值
	 */
	private String routeValue;
}
