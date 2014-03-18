package com.demo.action;

import com.demo.biz.AddBiz;
import com.demo.dao.*;
import com.demo.dao.Courier.DhlZkDao;
import com.demo.dao.Courier.DhlfqDao;
import com.demo.dao.Courier.FedexDao;
import com.demo.dao.user.CaiGouAdminDao;
import com.demo.dao.user.CaiGouDao;
import com.demo.dao.user.CaiWuDao;
import com.demo.dao.user.CangKuYuanDao;
import com.demo.dao.user.GuKeDao;
import com.demo.dao.user.UserDao;
import com.demo.dao.user.YeWu1Dao;
import com.demo.dao.user.YeWuDao;
import com.demo.entity.*;
import com.demo.entity.Courier.DhlFq;
import com.demo.entity.Courier.Dhlzk;
import com.demo.entity.Courier.Fedex;
import com.demo.entity.Courier.YunFeiTable;
import com.demo.entity.order.OrderTable;
import com.demo.entity.order.Order_Detail;
import com.demo.entity.user.CaiGou;
import com.demo.entity.user.CaiGouAdmin;
import com.demo.entity.user.CaiWuTable;
import com.demo.entity.user.CangKuYuan;
import com.demo.entity.user.GuKeTable;
import com.demo.entity.user.UserInfo;
import com.demo.entity.user.YeWu;
import com.demo.entity.user.YeWu1;
import com.demo.list.PageModel;
import com.demo.page.PageBean;
import com.demo.page.PageBiz;
import com.demo.vo.LoginInfo;
import com.opensymphony.xwork2.ActionContext;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("clipArtAction")
@Scope("prototype")
public class ClipArtAction extends BaseAction implements ServletRequestAware
{
	  private static final long serialVersionUID = 1L;
	  @Resource
	  private PageBiz pageBiz;
      @Resource
      private OrderDao orderDao;
	  private PageBean pageBean;
	  public int pageNumber;
	  public String orderId;
	  private HttpServletRequest request;
	  public PageBean getPageBean() {
		return pageBean;
	}
	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
	public String getorders(){
		try {
			LoginInfo us = (LoginInfo)getFromSession("logininfo");
			  int pageSize = 10;
			  pageBean = pageBiz.selArtistsGetOrders(pageSize, pageNumber, orderId, us.getUserId());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		  return "getorders";
	  }
	 //美工处理订单
    public String chuli(){
    	  try
          {
              String[] ch = request.getParameter("bulletinId").split("-");
              String[] str = new String[ch.length];
              for(int i = 0; i < ch.length; i++)
              {
                  List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i]));
                  if(ls.size() != 0)
                  {
                      ls.get(0).setId(Long.parseLong(ch[i]));
                      ls.get(0).setArtProcessing(1l);
                      orderDao.merge(ls.get(0));   
                      str[i] = i + ".操作成功！";
                  }
              }
              ActionContext.getContext().put("strsd", str);
          }
          catch(Exception e)
          {
              e.printStackTrace();
          }
     
    	return getorders();
    }
    //已经处理
    public String getDisposed(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.selDisposed(pageSize, pageNumber, orderId, us.getUserId());
    	return "Disposed";
    }
    //美工返回未处理订单
    public String untreated(){
    	  try
          {
              String[] ch = request.getParameter("bulletinId").split("-");
              String[] str = new String[ch.length];
              for(int i = 0; i < ch.length; i++)
              {
                  List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i]));
                  if(ls.size() != 0)
                  {
                      ls.get(0).setId(Long.parseLong(ch[i]));
                      ls.get(0).setArtProcessing(0l);
                      orderDao.merge(ls.get(0));   
                      str[i] = i + ".操作成功！";
                  }
              }
              ActionContext.getContext().put("strsd", str);
          }
          catch(Exception e)
          {
              e.printStackTrace();
          }
     
    	return getDisposed();
    }
    //不能处理的订单
    public String upCanNotHandle(){
    	  try
          {
              String[] ch = request.getParameter("bulletinId").split("-");
              String[] str = new String[ch.length];
              for(int i = 0; i < ch.length; i++)
              {
                  List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i]));
                  if(ls.size() != 0)
                  {
                      ls.get(0).setId(Long.parseLong(ch[i]));
                      ls.get(0).setArtProcessing(2l);
                      orderDao.merge(ls.get(0));   
                      str[i] = i + ".操作成功！";
                  }
              }
              ActionContext.getContext().put("strsd", str);
          }
          catch(Exception e)
          {
              e.printStackTrace();
          }
    	return getorders();
    }
    //得到不能处理的订单
    public String CanNothandle(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.selCanNotHandle(pageSize, pageNumber, orderId, us.getUserId());
    	return "cannothandle";
    }
    //未处理
    public String upUntreated(){
    	  try
          {
              String[] ch = request.getParameter("bulletinId").split("-");
              String[] str = new String[ch.length];
              for(int i = 0; i < ch.length; i++)
              {
                  List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i]));
                  if(ls.size() != 0)
                  {
                      ls.get(0).setId(Long.parseLong(ch[i]));
                      ls.get(0).setArtProcessing(0l);
                      orderDao.merge(ls.get(0));   
                      str[i] = i + ".操作成功！";
                  }
              }
              ActionContext.getContext().put("strsd", str);
          }
          catch(Exception e)
          {
              e.printStackTrace();
          }
          return CanNothandle();
    }
    public void setServletRequest(HttpServletRequest arg0)
    {
        request = arg0;
    }
}

