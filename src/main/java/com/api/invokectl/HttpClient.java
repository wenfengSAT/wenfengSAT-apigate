package com.api.invokectl;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.api.model.DataSrvReq;
import com.api.model.DataSrvRsp;
import com.api.model.RequestHeader;
import com.api.model.ResponseHeader;
import com.api.util.FlowManageContext;
import com.api.model.AopCommandContext;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Description： 基于Http协议统一调用入口类
 * 
 * @author [ Wenfeng.Huang ] on [2019年11月12日下午8:16:04]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Slf4j
@Component
public class HttpClient {

	@Autowired
	private FlowManageContext flowManageContext;

	/**
	 * HTTP流程管控接入模块
	 * 
	 * @param dataSrvReq
	 *            请求参数对象
	 * @return 具体的返回业务处理结果对象
	 */
	@SuppressWarnings("finally")
	public DataSrvRsp invoke(DataSrvReq dataSrvReq) {
		log.info("     +----------------------------------------------------------+");
		log.info("     |                                                          |");
		log.info("     |                HttpClient invoker begin......            |");
		log.info("     |                                                          |");
		log.info("     +----------------------------------------------------------+");
		if (log.isDebugEnabled()) {
			log.debug("HttpClient input parms:{}", JSON.toJSONString(dataSrvReq));
		}
		long beginTime = System.currentTimeMillis();

		AopCommandContext context = initAopContext(dataSrvReq);

		try {
			flowManageContext.execute(context);
		} catch (Exception e) {
			log.error("HttpInvoke exception error : {}", e.getMessage(), e);
			exceProcess(context, e);
		} finally {
			// TODO 记录HTTP请求情况
			log.info("服务：{}  处理耗时：{}", dataSrvReq.getRequestHeader().getProcessCode(),
					(System.currentTimeMillis() - beginTime));
			return buildDataSrvRsp(dataSrvReq, context.getRespCode(), context.getRespDesc(), context.getResponseStr());
		}
	}

	/**
	 * 异常统一处理 TO-DO 可做业务的补充处理
	 * 
	 * @param context
	 * @param e
	 */
	private void exceProcess(AopCommandContext context, Exception e) {
		// if (e instanceof BaseException) { }
		context.setRespCode("10090");
		context.setRespDesc(e.getMessage());
	}

	/**
	 * 转换AopContext
	 *
	 * @param dataSrvReq
	 * @return
	 * @throws BaseException
	 */
	private AopCommandContext initAopContext(DataSrvReq dataSrvReq) {
		AopCommandContext context = new AopCommandContext();
		RequestHeader requestHeader = dataSrvReq.getRequestHeader();
		Map<String, Object> msgBody = dataSrvReq.getMsgBody();
		context.setRequestStr(JSON.toJSONString(msgBody));
		context.setReqSeq(requestHeader.getReqSeq());
		context.setAppKey(requestHeader.getAppKey());
		context.setAccessToken(requestHeader.getAccessToken());
		context.setAppId(requestHeader.getAppId());
		context.setOpenId(requestHeader.getOpenId());
		context.setProcessCode(requestHeader.getProcessCode());
		context.setSign(requestHeader.getSign());
		context.setTimestamp(requestHeader.getTimestamp());
		return context;
	}

	/**
	 * 组装返回参数
	 *
	 * @param dataSrvReq
	 * @param code
	 * @param desc
	 * @param msgbody
	 * @return
	 */
	private DataSrvRsp buildDataSrvRsp(DataSrvReq dataSrvReq, String code, String desc, String msgbody) {
		ResponseHeader.RetInfo retInfo = new ResponseHeader.RetInfo(code, desc);
		ResponseHeader responseHeader = new ResponseHeader();
		RequestHeader requestHeader = Objects.isNull(dataSrvReq) ? new RequestHeader() : dataSrvReq.getRequestHeader();
		responseHeader.setRspSeq(requestHeader == null ? "" : requestHeader.getReqSeq());
		responseHeader.setRspTime(new Date());
		responseHeader.setRetInfo(retInfo);
		if (Objects.nonNull(dataSrvReq) && Objects.nonNull(dataSrvReq.getRequestHeader())) {
			responseHeader.setProcessCode(dataSrvReq.getRequestHeader().getProcessCode());
		}
		return new DataSrvRsp(responseHeader, msgbody);
	}

}
