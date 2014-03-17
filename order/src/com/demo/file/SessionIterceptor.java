package com.demo.file;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@SuppressWarnings("serial")
public class SessionIterceptor extends AbstractInterceptor{

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
    	System.out.println("+++++++++++++userid+++++++");
        ActionContext ctx=invocation.getInvocationContext();
        String user=(String)ctx.getSession().get("userid");
        System.out.println("+++++user+++");
        if(user!=null){
            return invocation.invoke();
        }
        //如果超时，返回提示页面
        return "login";
    }

}
