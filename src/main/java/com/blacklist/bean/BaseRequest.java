package com.blacklist.bean;

import java.io.Serializable;

public class BaseRequest implements Serializable {
	private static final long serialVersionUID = 3808921480675557346L;

	private String tsno = null;//交易流水号

	public String getTsno() {
		return tsno;
	}

	public void setTsno(String tsno) {
		this.tsno = tsno;
	}
}
