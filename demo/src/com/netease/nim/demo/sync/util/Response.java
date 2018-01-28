package com.netease.nim.demo.sync.util;

import java.io.Serializable;

public class Response implements Serializable {

	private String rtnCode;
	private String rtnMsg;
	private Object responseData;

	public Response(String rtnCode, String rtnMsg, Object responseData)
	{
		this.rtnCode = rtnCode;

		this.rtnMsg = rtnMsg;

		this.responseData = responseData;
	}

	public Response(Object responseData)
	{
//	      if (((responseData instanceof BusinessException)) || ((responseData instanceof SystemException))) {
//	        this.rtnCode = "999999";
//	        this.rtnMsg = "本次请求失败!";
//	      } else {
//	        this.rtnCode = "000000";
//	        this.rtnMsg = "本次请求成功!";
//	        this.responseData = responseData;
//	      }
	}

	public String getRtnCode() {
		return this.rtnCode;
	}

	public String getRtnMsg() {
		return this.rtnMsg;
	}

	public Object getResponseData() {
		return this.responseData;
	}

	public void setRtnMsg(String rtnMsg) {
		this.rtnMsg = rtnMsg;
	}

	public void setRtnCode(String rtnCode) {
		this.rtnCode = rtnCode;
	}

}
