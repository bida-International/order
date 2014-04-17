package com.demo.action;

import com.demo.dao.HuiLvDao;
import com.demo.dao.LeiMuDao;
import com.demo.dao.OrderDao;
import com.demo.dao.OrderTableDao;
import com.demo.dao.XieXinDao;
import com.demo.dao.user.UserDao;
import com.demo.entity.HuiLvTable;
import com.demo.entity.LeiMuTable;
import com.demo.entity.XieXinTable;
import com.demo.entity.order.OrderTable;
import com.demo.entity.user.GuKeTable;
import com.demo.entity.user.UserInfo;
import com.demo.list.PageModel;
import com.demo.page.PageBean;
import com.demo.page.PageBiz;
import com.demo.vo.LoginInfo;
import com.opensymphony.xwork2.ActionContext;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("yewu1Action")
@Scope("prototype")
public class YeWu1Action extends BaseAction implements ServletRequestAware
{
    private static final long serialVersionUID = 1L;
    @Resource
    private OrderDao orderDao;
    @Resource
    private LeiMuDao leiMuDao;
    @Resource
    private OrderTableDao orderTableDao;
    @Resource
    private UserDao userDao;
    @Resource
    private HuiLvDao huiLvDao;
    @Resource
    private PageBiz pageBiz;
    @Resource
    private XieXinDao xieXinDao;
    public String usertype;
    private OrderTable ordertable;
    private XieXinTable xiexin;
    public String msg;
    public int pageindex;
    public String orderId;
    public String time;
    public String time1;
    public Long zhanghaoId;
    public Long caigouyuan;
    public int pageNumber;
    public Long chuli;
    public Long chulis;
    public String danhao;
    public Long dhgatezhanghao;
    private PageBean pageBean;
    private HttpServletRequest request;
    public PageBean getPageBean()
    {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean)
    {
        this.pageBean = pageBean;
    }

    public OrderTable getOrdertable()
    {
        return ordertable;
    }

    public void setOrdertable(OrderTable ordertable)
    {
        this.ordertable = ordertable;
    }
    //业务查看全部分配订单
    public String getFenPeiAll(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.selAllFenPei(pageSize, pageNumber, us.getUserId(), orderId);
    	return "fenpei";
    }
    //业务 分配订单
    public String caigou_fenpei()
    {
        try
        {
            String ch[] = request.getParameter("bulletinId").split("-");
            String sel[] = request.getParameter("seluserid").split("-");
            String str[] = new String[ch.length];
            for(int i = 0; i < ch.length; i++)
            {
                List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i]));
                if(sel.length != ch.length)
                    str[i] = i+".操作失败";
                else
                if("".equals(sel[i]) || sel[i] == null)
                    str[i] = i+".操作失败";
                else
                if(ls.size() != 0)
                {
                    ((OrderTable)ls.get(0)).setId(Long.parseLong(ch[i]));
                    ((OrderTable)ls.get(0)).setFenpei(1l);
                    ((OrderTable)ls.get(0)).setCaigouyuan(Long.parseLong(sel[i]));
                    ls.get(0).setGetordersId(1l);
                    orderDao.merge((OrderTable)ls.get(0));
                    str[i] = i+".分配成功！";
                }
            }

            ActionContext.getContext().put("strsd", str);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getFenPeiAll();
    }
    //分配问题订单
    public String wentiorder()
    {
        try
        {
            String ch[] = request.getParameter("bulletinId").split("-");
            String str[] = new String[ch.length];
            for(int i = 0; i < ch.length; i++)
            {
                List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i]));
                if(ls.size() != 0)
                {
                    ((OrderTable)ls.get(0)).setId(Long.parseLong(ch[i]));
                    ((OrderTable)ls.get(0)).setFenpei(2l);
                    orderDao.merge((OrderTable)ls.get(0));
                    str[i] = "操作成功";
                }
            }

            ActionContext.getContext().put("wenti", str);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getFenPeiAll();
    }
  //修改分配备注
    public String updateRemark(){
   	 return "upadminremark";
    }
    public OrderTable getUpdateId()
    {
        OrderTable stu = (OrderTable)orderDao.get(ordertable.getId());
        return stu;
    }
    //修改备注
    public String upAdminRemark(){
		String remark = ordertable.getRemark();
	   	ordertable = orderDao.get(ordertable.getId());
	   	ordertable.setRemark(remark);
	   	orderDao.merge(ordertable);
	   	msg = "修改成功"; 
	   	return getFenPeiAll();
    }
    //查看问题订单
    public String getWenTiOrder(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.selWenTiOrder(pageSize, pageNumber, us.getUserId(), orderId,dhgatezhanghao);
    	return "wenti";
    }
    //分配问题订单
    public String upfenpei()
    {
        try
        {
            String ch[] = request.getParameter("bulletinId").split("-");
            String sel[] = request.getParameter("seluserid").split("-");
            String str[] = new String[ch.length];
            for(int i = 0; i < ch.length; i++)
            {
                List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i]));
                if(sel.length != ch.length){
                    str[i] = i+"有账号未选择操作失败！";
                }
                else
                if("".equals(sel[i]) || sel[i] == null)
                {
                    str[i] = i+"操作失败";
                }
                else
                if(ls.size() != 0)
                {
                    ls.get(0).setId(Long.parseLong(ch[i]));        
                    ls.get(0).setCaigouyuan(Long.parseLong(sel[i]));
                    ls.get(0).setDenghuixin(0l);
                    ls.get(0).setSumaitong(0l);
                    ls.get(0).setDaifahuo(0l);
                    ls.get(0).setDaochu(0l);
                    ls.get(0).setFawan(2l);
                    ls.get(0).setFenpei(1l);
                    ls.get(0).setGetordersId(1l);
                    orderDao.merge(ls.get(0));
                    str[i] = i+".操作成功";
                }
            }
            ActionContext.getContext().put("str", str);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getWenTiOrder();
    }
    //问题订单已经完成 
    public String upWtWcOrder(){
   	  try
         {
             String[] ch = request.getParameter("bulletinId").split("-");
             String[] str = new String[ch.length];
             for(int i = 0; i < ch.length; i++)
             {
                 List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i]));
                 if(ls.size() != 0)
                 {
              	   if(ls.get(0).getWancheng() == 1){
              		   str[i]  = i+".此订单已经是完成的订单";
              	   }else{
                     ls.get(0).setId(Long.parseLong(ch[i]));
                     ls.get(0).setDaifahuo(0l);
                     ls.get(0).setWancheng(1l);
                     ls.get(0).setFenpei(1l);
                     
                     ls.get(0).setWanchengtime(new java.sql.Date(System.currentTimeMillis()));
                     
                     orderDao.merge(ls.get(0));
                     str[i] = i+".操作成功";
              	   }
                 }
             }
             ActionContext.getContext().put("str", str);
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }
         
   	 return getWenTiOrder();
    }
    //修改备注
    public String upRemark(){
    	return "upremark";
    }
    //修改备注
    public String updateWenTiRemark(){
    	String remark = ordertable.getRemark();
	   	ordertable = orderDao.get(ordertable.getId());
	   	ordertable.setRemark(remark);
	   	orderDao.merge(ordertable);
	   	msg = "修改成功"; 
	   	return getWenTiOrder();
    }
    public void setServletRequest(HttpServletRequest arg0)
    {
        request = arg0;
    }
    //业务查看所有订单那
    public String getYeAllOrder(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean=pageBiz.selYeAllOrder(pageSize, pageNumber, orderId,us.getUserId());
    	return "getallorder";
    }
    //修改订单
    public String upOrder(){
    	return "updateorder";
    }
    //修改订单
    public String updateOrder(){
    	String danhao = ordertable.getDanhao();
    	String remark = ordertable.getRemark();
	   	ordertable = orderDao.get(ordertable.getId());
	   	ordertable.setDanhao(danhao);
	   	ordertable.setRemark(remark);
	   	orderDao.merge(ordertable);
	   	msg = "修改成功"; 
	   	return getYeAllOrder();
    }
    //标注为紧急订单
    public String upjinji(){
    	ordertable = orderDao.get(ordertable.getId());
    	ordertable.setJingji(1l);
    	orderDao.merge(ordertable);
    	msg = "修改成功 ！";
    	return getYeAllOrder();
    }
    //查看纠纷订单
    public String getDispute(){
    	try {
    		LoginInfo us = (LoginInfo)getFromSession("logininfo");
        	int pageSize = 10;
        	pageBean = pageBiz.selDispute(pageSize, pageNumber, orderId, us.getUserId(),chuli,time,time1,zhanghaoId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	return "getDispute";
    }
	//查看全部退货
    public String getTuiHuoAll(){
    
    	try {
    		LoginInfo us = (LoginInfo)getFromSession("logininfo");
    		int pageSize = 10;
        	pageBean = pageBiz.selChaKanAll(pageSize, pageNumber, danhao,chulis,us.getUserId());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
    	return "tuihuoAll";
    }
    //未完成订单修改为已经完成 
    public String upWcOrder(){
   	   try
          {
   		 
              String[] ch = request.getParameter("bulletinId").split("-");
              String[] str = new String[ch.length];
              for(int i = 0; i < ch.length; i++)
              {
                  List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i]));
                  
                  if(ls.size() != 0)
                  {
                	  if (ls.get(0).getTuihuo() == 1) {
                		  ls.get(0).setId(Long.parseLong(ch[i]));
                          ls.get(0).setDaifahuo(0l);
                          ls.get(0).setWancheng(1l);
                          ls.get(0).setGetordersId(0l);
                          ls.get(0).setChuli(1l);
                          ls.get(0).setSumaitong(0l);  
                          ls.get(0).setWanchengtime(new java.sql.Date(System.currentTimeMillis()));
                          orderDao.merge(ls.get(0));
                          str[i] = i+".操作成功";  	
					}else{
						  str[i] = i+".此订单不是退货订单,操作失败";
					}
                     
                  }
              }
              ActionContext.getContext().put("str", str);
          }
          catch(Exception e)
          {
              e.printStackTrace();
          }
          
   	 return getProcessingReturns();
    }
  
    //返回给仓库退件菜单
    public String redaifangqu(){
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
                     ls.get(0).setTuihuo(2l);
                     ls.get(0).setChuli(1l);
                     ls.get(0).setDaochu(0l);
                     //ls.get(0).setDanhao(null);
                     ls.get(0).setWancheng(0l);
                     orderDao.merge(ls.get(0));
                     str[i] = i +".操作成功";
                 }
             }
             ActionContext.getContext().put("strb", str);
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }
         return getProcessingReturns();
    }
    //处理退货订单
    public String getProcessingReturns(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.selProcessingReturns(pageSize, pageNumber, danhao, us.getUserId());
    	return "tuihuo";
    }
    //业务处理订单
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
               
                      if(ls.get(0).getChuli()==null || ls.get(0).getChuli()==0l){
                    		ls.get(0).setChuli(1l);  
                      }
                      else if(ls.get(0).getChuli() == 1l){
                    	  ls.get(0).setChuli(0l);  
                      }
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
     
    	return getDispute();
    }
    //修改备注
    public String upremarks(){
    	return "upremarks";
    }
    //修改备注
    public String updateRemarks(){
    	String remark = ordertable.getRemark();
    	ordertable = orderTableDao.get(ordertable.getId());
    	ordertable.setRemark(remark);
    	orderTableDao.merge(ordertable);
    	return getProcessingReturns();
    }
}