package com.api.command;

import org.apache.commons.chain.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.util.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Description： 后置 流程处理
 * 
 * @author [ Wenfeng.Huang ] on [2019年11月12日下午4:52:00]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ContextSufHandleCommand implements FlowManageCommand {

	/**
	 * 执行流程前处理：处理返回结果，封装结果参数
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Context context) {
		log.debug("后置流程处理....");
		context.put("respCode", ErrorCode.SUCCESS.getCode());
		context.put("respDesc", ErrorCode.SUCCESS.getMsg());
		context.put("responseStr", "{'key':'value'}");
		return false;
	}
}
