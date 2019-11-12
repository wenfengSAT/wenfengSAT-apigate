package com.api.command;

import org.apache.commons.chain.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Description： 访问控制
 * 
 * @author [ Wenfeng.Huang ] on [2019年11月12日下午4:45:52]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AccessControlCommand implements FlowManageCommand {

	/**
	 * 统计分配用户调用接口次数，做到预警/拒绝访问
	 */
	@Override
	public boolean execute(Context context) throws Exception {
		log.debug("访问控制....");
		return false;
	}

}
