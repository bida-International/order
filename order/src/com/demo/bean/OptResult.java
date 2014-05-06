package com.demo.bean;

import java.io.Serializable;

public class OptResult implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer result;
	private String msg;
	
	public OptResult() {
		
	}
	
	public OptResult(Integer result, String msg) {
		this.result = result;
		this.msg = msg;
	}
	
	/** 0.Ê§°Ü, 1.³É¹¦ */
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
