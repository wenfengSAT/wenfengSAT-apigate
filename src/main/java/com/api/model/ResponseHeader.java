package com.api.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @Description： 数据服务应答参数头
 * 
 * @author [ Wenfeng.Huang ] on [2019年10月11日上午10:29:26]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Setter
@Getter
public class ResponseHeader implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 781062671967057879L;

	/**
	 * 响应流水
	 */
	private String rspSeq;

	/**
	 * 响应时间
	 */
	private Date rspTime;

	/**
	 * 应用标识
	 */
	private String processCode;

	/**
	 * 返回结果信息
	 */
	private transient RetInfo retInfo;

	@Setter
	@Getter
	public static class RetInfo {
		/**
		 * 返回码
		 */
		@JsonProperty("RET_CODE")
		private String retCode;

		/**
		 * 返回消息
		 */
		@JsonProperty("RET_MSG")
		private String retMsg;

		public RetInfo() {
			super();
		}

		public RetInfo(String retCode, String retMsg) {
			this.retCode = retCode;
			this.retMsg = retMsg;
		}
	}

	public void buildRetInfo(String retCode, String retMsg) {
		this.retInfo = new RetInfo(retCode, retMsg);
	}

	public Date getRspTime() {
		return ObjectUtil.isNull(rspTime) ? rspTime : DateUtil.date(rspTime);
	}

	public void setRspTime(Date rspTime) {
		this.rspTime = DateUtil.date(rspTime);
	}

}
