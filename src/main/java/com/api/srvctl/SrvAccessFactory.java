package com.api.srvctl;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.api.exception.BaseException;
import com.api.srvctl.handler.DubboSrvAccessHandler;
import com.api.util.ErrorCode;
import com.api.util.LoadBeanUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Description： 获取接出服务的对象
 * 
 * @author [ Wenfeng.Huang ] on [2019年11月12日下午8:18:39]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Setter
@Getter
@Slf4j
public class SrvAccessFactory {

	private static final String PROTOCOL_HTTP = "http";
	private static final String PROTOCOL_TCP = "tcp";
	public static final String PROTOCOL_DUBBO = "dubbo";

	public static SrvAccessHandler getSrvService(String processCode, Route route) throws BaseException {
		SrvAccessHandler handler = null;
		String accessImpl = route.getAccessImpl();
		if (StringUtils.isBlank(accessImpl)) {
			String protocol = route.getProtocol();
			if (Objects.equals(protocol, PROTOCOL_HTTP)) {
				handler = new SrvAccess();
			} else if (Objects.equals(protocol, PROTOCOL_DUBBO)) {
				handler = LoadBeanUtils.getBean(DubboSrvAccessHandler.class);
			} else if (Objects.equals(protocol, PROTOCOL_TCP)) {
				// TODO 处理TCP协议...
			} else {
				log.warn("{}服务，协议[{}]不支持!", processCode, protocol);
				throw new BaseException(ErrorCode.PROTOCOL_UNDEFINE.getCode(), "协议[" + protocol + "]不支持!");
			}
		} else {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			try {
				handler = (SrvAccessHandler) (classLoader.loadClass(accessImpl)).newInstance();
			} catch (Exception e) {
				log.error("构造接出服务[{}]异常，实现类[{}]", processCode, accessImpl, e);
				throw new BaseException(ErrorCode.ACCESSIMPL_INSTANCE_ERROR.getCode(), "构造服务[" + processCode + "]出错!");
			}
		}
		return handler;
	}
}
