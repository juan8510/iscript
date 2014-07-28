package com.lezo.iscript.yeam.io;

import java.io.Serializable;

public class IoRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7718380647031578364L;
	private String header;
	private Object data;

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}