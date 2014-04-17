package com.demo.action;

import com.demo.dao.KuCunDao;
import com.demo.dao.OrderDao;
import com.demo.dao.OrderTableDao;
import com.demo.dao.ZhangHaoDao;
import com.demo.dao.Courier.YunFeiTableDao;
import com.demo.dao.user.UserDao;
import com.demo.entity.KuCunTable;
import com.demo.entity.ZhangHao;
import com.demo.entity.Courier.YunFeiTable;
import com.demo.entity.order.OrderTable;
import com.demo.entity.user.UserInfo;
import com.demo.list.PageData;
import com.demo.list.PageModel;
import com.demo.page.PageBean;
import com.demo.page.PageBiz;
import com.demo.vo.LoginInfo;
import com.opensymphony.xwork2.ActionContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
@Controller("caiGouAdminAction")
@Scope("prototype")
public class CaiGouAdminAction extends BaseAction implements ServletRequestAware
{
    private static final long serialVersionUID = 1L;
    @Resource
    private PageBiz pageBiz;
    @Resource
    private OrderDao orderDao;
    @Resource
    private UserDao userDao;
    @Resource
    private YunFeiTableDao yunFeiTableDao;
    @Resource
    private OrderTableDao orderTableDao;
    @Resource
    private KuCunDao kuCunDao;
    @Resource
    private ZhangHaoDao zhangHaoDao;
    public Long leimu;
    private OrderTable ordertable;
    public String zhanghaoIds;
    public String orderId;
    public String time;
    public String msg;
    public String wuping;
    public PageData pd;
    public int pageindex;
    public String wancheng;
    public String time1;
    public String caigoutime;
    public String caigoutime1;
    public int pageNumber;
    private PageBean pageBean;
    public List<OrderTable> orders;
    public String zhanghaoId;
    private int page;
    public String key;
    public String bianma;
    private Long cid;
    public String bianhao;
    private ZhangHao myzhangHao;
    public Long leimus;
    public Long category;
    public String gongyunshang;

    private HttpServletRequest request;
    
    public ZhangHao getMyzhangHao() {
		return myzhangHao;
	}

	public void setMyzhangHao(ZhangHao myzhangHao) {
		this.myzhangHao = myzhangHao;
	}

	public PageBean getPageBean()
    {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean)
    {
        this.pageBean = pageBean;
    }

    public Long getCid()
    {
        return cid;
    }

    public void setCid(Long cid)
    {
        this.cid = cid;
    }

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public OrderTable getOrdertable()
    {
        return ordertable;
    }

    public void setOrdertable(OrderTable ordertable)
    {
        this.ordertable = ordertable;
    }

    public String getAdminDeDaoOrder()
    {
       int pageSize = 10;
       pageBean = pageBiz.selAdminDeDaoOrder(pageSize, pageNumber, orderId);
        return "cgadmindedaoorder";
    }


    public String updedaos()
    {
        LoginInfo us = (LoginInfo)getFromSession("logininfo");
        String ch[] = request.getParameter("bulletinId").split("-");
        String str[] = new String[ch.length];
        for(int i = 0; i < ch.length; i++)
        {
            List<OrderTable> ls = orderDao.getSelId(Long.valueOf(Long.parseLong(ch[i])));
            if(ls.size() != 0)
                if(((OrderTable)ls.get(0)).getWuping() == null || "".equals(((OrderTable)ls.get(0)).getWuping()) || ((OrderTable)ls.get(0)).getGongyunshang() == null || "".equals(((OrderTable)ls.get(0)).getGongyunshang()))
                {
                    str[i] = i + ".订单"+ls.get(0).getOrderId()+"有信息没填写完整";
                } else
                {
                    ls.get(0).setId(Long.parseLong(ch[i]));
                    ls.get(0).setCaigouyuan(us.getUserId());
                    ls.get(0).setDaifahuo(1l);
                    ls.get(0).setFenpei(1l);
                    ls.get(0).setGetordersId(0l);
                    orderDao.merge(ls.get(0));
                    str[i] = i +".操作成功！";
                }
        }
        ActionContext.getContext().put("str", str);
        return getCaiGouAdminDeDaoOrder();
    }

    public String upcgadminorder()
    {
        return "cgadminorder";
    }
    //采购管理员修改订单
    public String cgadminorder() throws Exception
    {
    	String orderId = ordertable.getOrderId();
        String gongyunshang = ordertable.getGongyunshang();
        String beizhu = ordertable.getRemark();
        Long cai = ordertable.getCaigouyuan();
        String danhao = ordertable.getDanhao();
        Double huokuan = ordertable.getHuokuan();
        String dizhi = ordertable.getGuowaidizhi();
        String wuping = ordertable.getWuping();
        Date shijian = ordertable.getCaigoutime();
        Long caigou = ordertable.getGuoneiwangzhanId();    
        String kucun = request.getParameter("kucun");
        Long kuaidi = ordertable.getKuaidifangshiId();
        String guojia = ordertable.getGuojia();
        String num = request.getParameter("num");
        ordertable = (OrderTable)orderDao.get(ordertable.getId());
        ordertable.setGongyunshang(gongyunshang);
        ordertable.setRemark(beizhu);
        ordertable.setCaigouyuan(cai);
        ordertable.setDanhao(danhao);
        ordertable.setHuokuan(huokuan);
        ordertable.setGuowaidizhi(dizhi);
        ordertable.setWuping(wuping);
        ordertable.setCaigoutime(shijian);
        ordertable.setGuoneiwangzhanId(caigou); 
       	ordertable.setKuaidifangshiId(kuaidi);
        ordertable.setGuojia(guojia);
        ordertable.setXiugai(1l);
        if (Long.parseLong(kucun) == 1) {
        	 LoginInfo us = (LoginInfo)getFromSession("logininfo");
             List<KuCunTable> kk = kuCunDao.getGoodsUserId(wuping, us.getUserId());
             if (kk.size() == 0) {
     			msg = "库存订单里面没有找到("+wuping+")此物品、操作失败";
     			return getCaiGouAdminDeDaoOrder();
     		}else{
     			if (kk.get(0).getNum() < Long.parseLong(num)) {
     				kk.get(0).setId(kk.get(0).getId());
     				msg= "库存数量还差"+((Long.parseLong(num))-(kk.get(0).getNum()));
     				kk.get(0).setNum(0l);
     				kuCunDao.merge(kk.get(0));
     			}else{
     				kk.get(0).setId(kk.get(0).getId());
     				kk.get(0).setNum((kk.get(0).getNum()-(Long.parseLong(num))));
     				kuCunDao.merge(kk.get(0));
     			}
     		}
		}
        orderDao.merge(ordertable);
		return getCaiGouAdminDeDaoOrder();
       
    }

    public String fanhui()
    {
        ordertable = (OrderTable)orderDao.get(ordertable.getId());
        ordertable.setFenpei(0l);
        ordertable.setWancheng(0l);
        orderDao.merge(ordertable);
        msg = "操作成功";
        return getWanChengOrder();
    }

    public String fanhuiorder()
    {
        ordertable = (OrderTable)orderDao.get(ordertable.getId());
        ordertable.setFenpei(0l);
        ordertable.setWancheng(0l);
        ordertable.setGetordersId(0l);
        orderDao.merge(ordertable);
        msg = "操作成功";
        return getCaiGouAdminDeDaoOrder();
    }
    //把库存订单全部放入待放区
    public String daifangqu(){
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
                    ls.get(0).setGetordersId(2l);
                    ls.get(0).setFenpei(1l);
                    orderDao.merge(ls.get(0));
                    str[i] = i +".操作成功";
                }
            }
            ActionContext.getContext().put("strsd", str);
           }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return getCaiGouAdminDeDaoOrder();
    }
    //采购管理员返回到待发货
    public String upsumaitong(){
    	 try
         {
             String ch[] = request.getParameter("bulletinId").split("-");
             for(int i = 0; i < ch.length; i++)
             {
                 List<OrderTable> ls = orderDao.getSelId(Long.valueOf(Long.parseLong(ch[i])));
                 if(ls.size() != 0)
                 {
                     ls.get(0).setId(Long.parseLong(ch[i]));
                     ls.get(0).setSumaitong(1l);
                     orderDao.merge(ls.get(0));
                 }
             }

             msg = "操作成功";
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }
         return getDaiFaHuo();
    }
    public String upcgadminwenchengorder()
    {
        return "upcgadminorder";
    }

    public OrderTable getCgUpOrderId()
    {
        OrderTable stu = (OrderTable)orderDao.get(ordertable.getId());
        return stu;
    }
   
    public String getcgadminorder()
    {
        return "cguporder";
    }
    //采购管理员完成订单
    public String getWanChengOrder()
    {
    	try {
    	
    		 LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	        int pageSize = 10;
    	        pageBean = pageBiz.selCaiGouAdminWanChengOrder(pageSize, pageNumber, us.getUserId(), orderId, time, time1,caigoutime,caigoutime1,bianma,gongyunshang,wuping);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
       
        return "cguporder";
    }

    public String caigouadminuporder()
    {
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        String ff = f.format(d);
        request.setAttribute("date", ff);
        return "cgadminorder";
    }

    public String cgupdateorder()
    {
        Long cai = ordertable.getCaigouyuan();
        String danhao = ordertable.getDanhao();
        Double huokuan = ordertable.getHuokuan();
        Double yunfei = ordertable.getYunfei();
        String gongyunshang = ordertable.getGongyunshang();
        ordertable = (OrderTable)orderDao.get(ordertable.getId());
        ordertable.setDanhao(danhao);
        ordertable.setYunfei(yunfei);
        ordertable.setHuokuan(huokuan);
        ordertable.setCaigouyuan(cai);
        ordertable.setGongyunshang(gongyunshang);
        ordertable.setXiugai(1l);
        orderDao.merge(ordertable);
        return getWanChengOrder();
    }

    public String addproduct()
    {
        return "addcporder";
    }

    public String addorder()
    {
        String dingdan[] = request.getParameterValues("dingdan");
        String jine[] = request.getParameterValues("jine");
        String yunshu[] = request.getParameterValues("yunshu");
        String zhanghao[] = request.getParameterValues("zhanghaoId");
        String beizhu[] = request.getParameterValues("remark");
        String str[] = new String[dingdan.length];
        for(int i = 0; i < dingdan.length; i++)
        {
            List<OrderTable> ls = orderDao.getAllOrderId(dingdan[i]);
            if("".equals(dingdan[i]) || dingdan[i] == null)
                str[i] = i+".订单不能为空";
            else
            if(ls.size() != 0)
                str[i] = i+".订单已经存在";
            else
            if("".equals(jine[i]) || jine[i] == null)
                str[i] = i+"金额不能为空";
            else
            if("0".equals(zhanghao[i]) || Integer.parseInt(zhanghao[i]) == 0)
            {
                str[i] = i+"账号为选择";
            } else
            {
                OrderTable order = new OrderTable();
                order.setTime(new java.sql.Date(System.currentTimeMillis()));
                order.setCaigouyuan(0L);
                order.setOrderId(dingdan[i]);
                order.setMoney(Double.parseDouble(jine[i]));
                order.setYunshu(yunshu[i]);
                order.setZhanghaoId(Long.parseLong(zhanghao[i]));
                order.setRemark(beizhu[i]);
                LoginInfo login = (LoginInfo)getFromSession("logininfo");
                order.setDengluId(login.getUserId());
                orderDao.merge(order);
                str[i] = i+".操作成功";
            }
        }

        ActionContext.getContext().put("insert", str);
        return "addcporder";
    }

    public String ceshi()
    {
        return getCaiGouAdminDeDaoOrder();
    }

    public String oldOrder()
    {
        LoginInfo us = (LoginInfo)getFromSession("logininfo");
        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        UserInfo user = userDao.getByUserNameOnPwd(username, pwd);
        try
        {
            if(user != null)
            {
                List<OrderTable> order = orderDao.getOldDaoRu(user.getId());
                for(int i = 0; i < order.size(); i++)
                {
                    order.get(i).setId(((OrderTable)order.get(i)).getId());
                    order.get(i).setCaigouyuan(us.getUserId());
                    orderDao.merge(order.get(i));
                    msg = "操作成功";
                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getWanChengOrder();
    }

    public String oldUnFinishedOrder()
    {
        LoginInfo us = (LoginInfo)getFromSession("logininfo");
        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        UserInfo user = userDao.getByUserNameOnPwd(username, pwd);
        System.out.println((new StringBuilder("user")).append(user).toString());
        try
        {
            if(user != null)
            {
                List<OrderTable> order = orderDao.getOldUnDaoRu(user.getId());
                for(int i = 0; i < order.size(); i++)
                {
                     order.get(i).setId(order.get(i).getId());
                    order.get(i).setCaigouyuan(us.getUserId());
                    orderDao.merge(order.get(i));
                    msg = "操作成功";
                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getCaiGouAdminDeDaoOrder();
    }
    //采购管理员得到订单
    public String getCaiGouAdminDeDaoOrder()
    {
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
 	   List<OrderTable> aa = orderTableDao.getXiuGaiOrder(us.getUserId());
 	   if(aa != null){
 		   for(int i = 0; i < aa.size(); i++)
             {
 			    aa.get(i).setId(aa.get(i).getId());
 			    aa.get(i).setJingji(1l);
                 orderTableDao.merge(aa.get(i));
             }
 	   }
 	   int pageSize = 10;
 	   pageBean = pageBiz.selDeDaoOrder(pageSize, pageNumber, us.getUserId(), orderId, time, time1, zhanghaoId,category);
        return "caigouadmindedaoorder";
    }
    //采购管理员待发货
    public String getDaiFaHuo()
    {
        int pageSize = 10;
        LoginInfo us = (LoginInfo)getFromSession("logininfo");
        pageBean = pageBiz.selCaiGouAdminDaiFaHuo(pageSize, pageNumber, us.getUserId(), orderId, time, time1,caigoutime,caigoutime1,wuping,gongyunshang);
        return "caigouadmindaifahuo";
    }

    public String adminweiwancheng()
    {
        try
        {
            LoginInfo us = (LoginInfo)getFromSession("logininfo");
            orders = orderTableDao.getCaiGouAdminDeDaoOrder1(us.getUserId(), orderId, time);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "exportOrder";
    }

    public String xuanzhe()
    {
        orders = new ArrayList();
        String id[] = request.getParameter("bulletinId").split("-");
        for(int i = 0; i < id.length; i++)
        {
            List<OrderTable> os = orderDao.getSelId(Long.parseLong(id[i]));
            orders.add(os.get(0));
        }

        return "exportOrder";
    }

    public String upadmindaifahuo()
    {
        return "cgadminupdaifahuo";
    }
    //采购管理员修改待发货
    public String cgadminupdaifahuo()
    {
        String gongyunshang = ordertable.getGongyunshang();
        String beizhu = ordertable.getRemark();
        Long cai = ordertable.getCaigouyuan();
        String danhao = ordertable.getDanhao();
        Double huokuan = ordertable.getHuokuan();
        String dizhi = ordertable.getGuowaidizhi();
        String wuping = ordertable.getWuping();
        Date shijian = ordertable.getCaigoutime();
        Long caigou = ordertable.getGuoneiwangzhanId();
        Long kuaidi = ordertable.getKuaidifangshiId();
        String guojia = ordertable.getGuojia();
        ordertable = (OrderTable)orderDao.get(ordertable.getId());
        
        ordertable.setGongyunshang(gongyunshang);
        ordertable.setRemark(beizhu);
        ordertable.setCaigouyuan(cai);
        ordertable.setDanhao(danhao);
        ordertable.setHuokuan(huokuan);
        ordertable.setGuowaidizhi(dizhi);
        ordertable.setGuojia(guojia);
        ordertable.setWuping(wuping);
        ordertable.setCaigoutime(shijian);
        ordertable.setGuoneiwangzhanId(caigou);
        ordertable.setKuaidifangshiId(kuaidi);
        ordertable.setXiugai(1l);
        orderDao.merge(ordertable);
        return getDaiFaHuo();
    }
    //采购管理员待发货返回速卖通给管理员
    public String adminsumaitong()
    {
        try
        {
            String ch[] = request.getParameter("bulletinId").split("-");
            for(int i = 0; i < ch.length; i++)
            {
                List<OrderTable> ls = orderDao.getSelId(Long.valueOf(Long.parseLong(ch[i])));
                if(ls.size() != 0)
                {
                    ls.get(0).setId(Long.parseLong(ch[i]));
                    ls.get(0).setSumaitong(1l);
                    ls.get(0).setFenpei(1l);
                    ls.get(0).setGetordersId(0l);
                    orderDao.merge(ls.get(0));
                }
            }

            msg = "操作成功";
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getCaiGouAdminDeDaoOrder();
    }
    //传给业务代发
    public String daifa()
    {
        try
        {
            String ch[] = request.getParameter("bulletinId").split("-");
            for(int i = 0; i < ch.length; i++)
            {
                List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i]));
                if(ls.size() != 0)
                {
                    ls.get(0).setId(Long.parseLong(ch[i]));
                    ls.get(0).setDenghuixin(1l);
                    ls.get(0).setFenpei(1l);
                    ls.get(0).setGetordersId(0l);
                    orderDao.merge(ls.get(0));
                }
            }

            msg = "操作成功";
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getCaiGouAdminDeDaoOrder();
    }
    //完成订单返回
    public String fanhuidaifahuo()
    {
        try
        {
            String ch[] = request.getParameter("bulletinId").split("-");
            for(int i = 0; i < ch.length; i++)
            {
                List<OrderTable> ls = orderDao.getSelId(Long.valueOf(Long.parseLong(ch[i])));
                if(ls.size() != 0)
                {
                    ls.get(0).setId(Long.parseLong(ch[i]));
                    ls.get(0).setWancheng(0l);
                    ls.get(0).setDaifahuo(0l);
                    ls.get(0).setDaochu(0l);
                    ls.get(0).setGetordersId(1l);
                    orderDao.merge(ls.get(0));
                }
            }

            msg = "操作成功 ";
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getWanChengOrder();
    }
    //查看问题订单
    public String getWenTiOrder()
    {
        int pageSize = 10;
        pageBean = pageBiz.selAllWenTiOrder(pageSize, pageNumber, orderId);
        return "wentiorder";
    }
    //采购管理员得到问题订单
    public String cgadminwenti(){
    	 LoginInfo us = (LoginInfo)getFromSession("logininfo");
         String ch[] = request.getParameter("bulletinId").split("-");
         for(int i = 0; i < ch.length; i++)
         {
             List<OrderTable> ls = orderDao.getSelId(Long.valueOf(Long.parseLong(ch[i])));
             if(ls.size() != 0)
             {
                 ls.get(0).setId(Long.parseLong(ch[i]));
                 ls.get(0).setCaigouyuan(us.getUserId());
                 ls.get(0).setWancheng(0l);
                 ls.get(0).setFenpei(1l);
                 orderDao.merge(ls.get(0));
             }
         }
         msg = "操作成功 ";
    	return getWenTiOrder();
    }
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
                if(sel.length != ch.length)
                    str[i] = i+"有账号未选择操作失败！";
                else
                if("".equals(sel[i]) || sel[i] == null)
                    str[i] = i+"操作失败";
                else
                if(ls.size() != 0)
                {
                    ls.get(0).setId(Long.parseLong(ch[i]));
                    ls.get(0).setFenpei(1l);
                    ls.get(0).setCaigouyuan(Long.parseLong(sel[i]));
                    ls.get(0).setDenghuixin(0l);
                    ls.get(0).setSumaitong(0l);
                    ls.get(0).setDaifahuo(0l);
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
    //采购管理员查看纠纷
    public String jiufen(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.selDisputeOrder(pageSize, pageNumber, us.getUserId(), orderId, caigoutime, caigoutime1, leimus);
    	return "caigouadminjiufen";
    }
    //采购管理员查看利润小于0的订单
    public String liRunAdmin(){
    
    	return "lirunadminzero";
    }
    //查看库存订单
    public String getKuCun(){
   	 LoginInfo us = (LoginInfo)getFromSession("logininfo");
   	 int pageSize = 10;
   	 pageBean = pageBiz.selKuCunOrder(pageSize, pageNumber,us.getUserId(), orderId);
   	 return "kucun";
    }
    //采购管理员修改库存
    public String upkucun(){
    	return "upnumber";
    }
    //采购管理员修改库存 
    public String updatekucun(){
    	Long num = ordertable.getNum();
    	String remark = ordertable.getRemark();
    	ordertable = orderDao.get(ordertable.getId());
    	ordertable.setNum(num);
    	ordertable.setRemark(remark);
    	orderDao.merge(ordertable);
    	msg = "修改成功 ";
    	return getKuCun();
    }
    //修改账号
    public String updatezhanghao(){
    	return "upzhanghao";
    }
    //修改账号
    public String myupzhanghao(){
    	 try
         {
    		 	Long zhanghao = ordertable.getZhanghaoId();
    	    	ordertable = orderDao.get(ordertable.getId());
    	    	ordertable.setZhanghaoId(zhanghao);
    	    	orderDao.merge(ordertable);
    	    	msg = "修改成功 ";
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }
    	return getDaiFaHuo();
    }
    //导出全部纠纷订单
    public String jforder(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	orders=orderTableDao.getJfOrder(us.getUserId());
    	if(orders.size()!=0){
    		  if(orders.size() != 0)
    	        {
	        	  for(int i = 0; i < orders.size(); i++)
	              {
	                  orders.get(i).setId(orders.get(i).getId());
	                  orders.get(i).setCgjf(1l);
	                  orderTableDao.merge(orders.get(i));
	              }
    	        }
    	}
    	return "cgadminexportOrder";
    }
    //采购管理员修改导出状态
    public String updateAllDaoChu()
    {
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
                    if(ls.get(0).getDaochu() == 1){     	
                    	ls.get(0).setDaochu(0l);
                    }
                    else if(ls.get(0).getDaochu()==0){  	
                    	ls.get(0).setDaochu(1l);
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
        return "caigouadminjiufen";
    }
    //给财务付款订单
    public String getPayment(){
    	  LoginInfo us = (LoginInfo)getFromSession("logininfo");
          String ch[] = request.getParameter("bulletinId").split("-");
          String str[] = new String[ch.length];
          for(int i = 0; i < ch.length; i++)
          {
              List<OrderTable> ls = orderDao.getSelId(Long.valueOf(Long.parseLong(ch[i])));
              if(ls.size() != 0)
            
                      ls.get(0).setId(Long.parseLong(ch[i]));
                      ls.get(0).setCaigouyuan(us.getUserId());
                      ls.get(0).setDaifahuo(3l);
                      ls.get(0).setFenpei(1l);
                      ls.get(0).setGetordersId(0l);
                      orderDao.merge(ls.get(0));
                      str[i] = i +".操作成功！";
                
          }
          return getCaiGouAdminDeDaoOrder();
    }
    //查询国家
    public YunFeiTable getCountry(){
    	YunFeiTable tt = null;
    	try {
    		 OrderTable stu = (OrderTable)orderDao.get(ordertable.getId());
    	  	 tt =  yunFeiTableDao.getCorresponding(stu.getCountry());
    	  	  System.out.println("++tt++"+tt.getGuojia());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
  	 
  	  return tt;
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
          
   	 return getCaiGouAdminDeDaoOrder();
    }
    public void setServletRequest(HttpServletRequest arg0)
    {
        request = arg0;
    }
}
