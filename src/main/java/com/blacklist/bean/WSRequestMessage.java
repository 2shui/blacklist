package com.blacklist.bean;

public class WSRequestMessage {
	private String msg;
	private String uid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg, String uid) {
		this.msg = msg;
		this.uid = uid;
	}


}
