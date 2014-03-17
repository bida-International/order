package com.demo.utils;

import javax.servlet.ServletContext;

public class ApplicationUtils {

	protected static ServletContext ctx = null;

	public static void setServletContext(ServletContext ctx) {
		ApplicationUtils.ctx = ctx;
	}

	public static ServletContext getServletContext() {
		return ctx;
	}

	public static void put(String key, Object value) {
		ctx.setAttribute(key, value);
	}

	public static void remove(String key) {
		ctx.removeAttribute(key);
	}

	public static Object get(String key) {
		return ctx.getAttribute(key);
	}
	
}
