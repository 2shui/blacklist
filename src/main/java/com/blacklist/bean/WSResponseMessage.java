package com.blacklist.bean;

public class WSResponseMessage {
	private String msg;
	private String user;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public WSResponseMessage(String msg, String user) {
		this.msg = msg;
		this.user = user;
	}
}
