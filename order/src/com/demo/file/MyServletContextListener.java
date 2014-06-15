package com.demo.file;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletContextListener implements ServletContextListener,
		ServletContextAttributeListener {
	private void print(String msg){
		System.out.println(new java.sql.Timestamp(System.currentTimeMillis()) + ":" + msg);
	}
	public void contextDestroyed(ServletContextEvent arg0) {
		//arg0.getServletContext().getAttribute("xx")
//		print("上下文即将销毁.");
	}

	public void contextInitialized(ServletContextEvent arg0) {
//		print("上下文已初始化.");
//		arg0.getServletContext().setAttribute("foo", "bar");
	}

	public void attributeAdded(ServletContextAttributeEvent arg0) {
//		print("添加属性:" + arg0.getName() + ":" + arg0.getValue());
	}

	public void attributeRemoved(ServletContextAttributeEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void attributeReplaced(ServletContextAttributeEvent arg0) {
		// TODO Auto-generated method stub

	}

}
