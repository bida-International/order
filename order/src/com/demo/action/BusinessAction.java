package com.demo.action;

import com.demo.page.PageBean;
import com.demo.page.PageBiz;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("businessAction")
@Scope("prototype")
public class BusinessAction extends BaseAction implements ServletRequestAware
{
    private static final long serialVersionUID = 1L;

    @Resource
    private PageBiz pageBiz;
  
    public int pageNumber;
    private PageBean pageBean;
    public String orderId;
    public String time;
    public String time1;
    private HttpServletRequest request;

	public PageBean getPageBean()
    {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean)
    {
        this.pageBean = pageBean;
    }  
    public void setServletRequest(HttpServletRequest arg0)
    {
        request = arg0;
    }
    //查询全部大于100美金订单
    public String getLargeOrder(){
    	int pageSize = 10;
    	pageBean = pageBiz.getLargeOrder(pageSize, pageNumber, orderId, time, time1);
    	return "largeorder";
    }
}