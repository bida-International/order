package com.demo.utils;

public class AppException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AppException() {
		
	}
	public AppException(String msg) {
		super(msg);
	}
	public AppException(String msg, Throwable e) {
		super(msg, e);
	}
}
