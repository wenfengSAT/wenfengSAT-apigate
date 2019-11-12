package com.api.command;

import org.apache.commons.chain.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Description：黑白名单控制
 * 
 * @author [ Wenfeng.Huang ] on [2019年11月12日下午4:47:01]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class BlackAndWhiteControlCommand implements FlowManageCommand {

	/**
	 * 限制IP,IP段
	 */
	@Override
	public boolean execute(Context context) throws Exception {
		log.debug("黑白名单控制....");
		return false;
	}

}
