package com.demo.file;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class OnlineUserStatsListener 
implements HttpSessionListener,HttpSessionActivationListener {
	public void sessionDidActivate(HttpSessionEvent arg0) {
		System.out.println("Session:" + arg0.getSession().getId() + "已激活!");
		
	}
	public void sessionWillPassivate(HttpSessionEvent arg0) {
		System.out.println("Session:" + arg0.getSession().getId() + "即将无效!");
	}

	public void sessionCreated(HttpSessionEvent arg0) {
		ServletContext application =
			arg0.getSession().getServletContext();
		Integer total = (Integer)application.getAttribute("onlineusernum");
		if(total == null){
			total = 1;
		}else{
			total ++;
		}
		application.setAttribute("onlineusernum", total);
		System.out.println("建立新的会话,其ID为:" + arg0.getSession().getId());
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		ServletContext application =
			arg0.getSession().getServletContext();
		Integer total = (Integer)application.getAttribute("onlineusernum");
		if(total != null){
			application.setAttribute("onlineusernum", total-1);
		}
		System.out.println("销毁会话,其ID为:" + arg0.getSession().getId());
	}

}
