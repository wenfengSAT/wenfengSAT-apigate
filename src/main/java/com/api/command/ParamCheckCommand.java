package com.api.command;

import org.apache.commons.chain.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Description： 参数校验
 * 
 * @author [ Wenfeng.Huang ] on [2019年10月22日下午12:15:22]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ParamCheckCommand implements FlowManageCommand {

	/**
	 * 参数校验：校验参数，验证签名，验证请求时效性
	 */
	@Override
	public boolean execute(Context context) throws Exception {
		log.debug("参数校验....");
		return false;
	}

}
