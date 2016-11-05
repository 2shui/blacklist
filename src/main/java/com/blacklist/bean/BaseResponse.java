package com.blacklist.bean;

import java.io.Serializable;

import com.blacklist.utils.JsonUtil;

public class BaseResponse implements Serializable {
	private static final long serialVersionUID = -2814755200098941271L;

	public BaseResponse() {

	}

	public BaseResponse(String tsno) {
		this.tsno = tsno;
	}

	private Object response; //返回数据

	/**
	 * 交易流水号
	 */
	private String tsno = "";

	/**
	 * 处理结果代码<br>
	 * 100 - 成功
	 */
	private String code = "";

	/**
	 * 结果描述<br>
	 */
	private String desc = "";

	/**
	 * 服务器时间
	 */
	private String sysTime = "";
	
	public Object getResponse() {
		return response;
	}
	public BaseResponse setResponse(Object response) {
		this.response = response;
		return this;
	}
	public String getTsno() {
		return tsno;
	}
	public void setTsno(String tsno) {
		this.tsno = tsno;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public BaseResponse setDesc(String desc) {
		this.desc = desc;
		return this;
	}
	public String getSysTime() {
		return sysTime;
	}
	public void setSysTime(String sysTime) {
		this.sysTime = sysTime;
	}

	public static BaseResponse success(String seqNo) {
		BaseResponse baseResponse = new BaseResponse(seqNo);
		baseResponse.setCode("100");
		baseResponse.setDesc("操作成功");
		baseResponse.setSysTime(""+System.currentTimeMillis());
		return baseResponse;
	}

	public static BaseResponse fail(String seqNo) {
		BaseResponse baseResponse = new BaseResponse(seqNo);
		baseResponse.setCode("500");
		baseResponse.setDesc("操作失败");
		baseResponse.setSysTime(""+System.currentTimeMillis());
		return baseResponse;
	}
	
	public static BaseResponse forbidden(String seqNo, String desc) {
		BaseResponse baseResponse = new BaseResponse(seqNo);
		baseResponse.setCode("403");
		baseResponse.setDesc(desc);
		baseResponse.setSysTime(""+System.currentTimeMillis());
		return baseResponse;
	}
	
	public static BaseResponse empty(String seqNo, String desc) {
		BaseResponse baseResponse = new BaseResponse(seqNo);
		baseResponse.setCode("404");
		baseResponse.setDesc(desc);
		baseResponse.setSysTime(""+System.currentTimeMillis());
		return baseResponse;
	}

	@Override
	public String toString() {
		return JsonUtil.toString(this);
	}
}
