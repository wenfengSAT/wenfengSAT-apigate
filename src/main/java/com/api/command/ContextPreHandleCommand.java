package com.api.command;

import org.apache.commons.chain.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Description： 前置流程处理
 * 
 * @author [ Wenfeng.Huang ] on [2019年11月12日下午4:49:42]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ContextPreHandleCommand implements FlowManageCommand {

	/**
	 * 执行流程前处理：封装参数，校验参数
	 */
	@Override
	public boolean execute(Context context) {
		log.debug("前置流程处理....");
		return false;
	}

}
