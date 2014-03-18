
package com.demo.action;

import com.demo.dao.KuCunDao;
import com.demo.dao.OrderDao;
import com.demo.dao.OrderTableDao;
import com.demo.dao.ZhangHaoDao;
import com.demo.dao.Courier.YunFeiTableDao;
import com.demo.entity.KuCunTable;
import com.demo.entity.ZhangHao;
import com.demo.entity.Courier.YunFeiTable;
import com.demo.entity.order.OrderTable;
import com.demo.list.PageModel;
import com.demo.page.PageBean;
import com.demo.page.PageBiz;
import com.demo.vo.LoginInfo;
import com.opensymphony.xwork2.ActionContext;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
@Controller("caiGouAction")
@Scope("prototype")
public class CaiGouAction extends BaseAction implements ServletRequestAware
{
    private static final long serialVersionUID = 1L;
    @Resource
    private OrderDao orderDao;
    @Resource
    private PageBiz pageBiz;
    @Resource
    private YunFeiTableDao yunFeiTableDao;
    @Resource
    private KuCunDao kuCunDao;
    @Resource
    private ZhangHaoDao zhangHaoDao;
    @Resource
    private OrderTableDao orderTableDao;
    private OrderTable ordertable;
    public int pageindex;
    public String orderId;
    public String msg;
    public String dhgatezhanghao;
    public String caigoutime;
    public String caigoutime1;
    public String time;
    public String bianma;
    public String time1;
    public String chuli;
    public String danhao;
    public String fanhui;
    public String bianhao;//编号 
    public Long kucun;//库存
    public String zhanghaoIds;
    public String zhanghaoId;
    public Long leimu;
    public List<OrderTable> orders;
    public List<OrderTable> order;
 	public List<OrderTable> stu1;
 	public String wuping;
    public int pageNumber;
    private PageBean pageBean;
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

    public OrderTable getOrdertable()
    {
        return ordertable;
    }

    public void setOrdertable(OrderTable ordertable)
    {
        this.ordertable = ordertable;
    }
    //采购完成订单
    public String getDeDaoOrder()
    {
        LoginInfo us = (LoginInfo)getFromSession("logininfo");
        int pageSize = 10;
            pageBean = pageBiz.selCaiGouAdminWanChengOrder(pageSize, pageNumber, us.getUserId(), orderId, time, time1,caigoutime,caigoutime1,bianma,gongyunshang,wuping);
        return "dedaoorders";
    }
    //采购查看利润小于0
    public String lirunzero(){
    	return "lirunzero";
    }
    //采购查看利润小于0
    public PageModel<OrderTable> getLiRunZero(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	List<OrderTable> stu = orderTableDao.getCaiGouLiRun(us.getUserId(), orderId, time, time1,zhanghaoId);
    	Double tuikuan = 0d;
    	Double huokuan = 0d;
    	Double yunfei = 0d;
    	Double money = 0d;
    	Double huilv = 0d;
    	 PageModel<OrderTable> ls = new PageModel<OrderTable>();
    	 stu1 = new ArrayList();
    		for (int i = 0; i < stu.size(); i++) {
    			if(stu.get(i).getTuikuan() == null || "".equals(stu.get(i).getTuikuan())){
    				tuikuan = 0d;
    			}else{
    				tuikuan = stu.get(i).getTuikuan();
    			}
    			if(stu.get(i).getHuokuan() == null || "".equals(stu.get(i).getHuokuan())){
    				huokuan = 0d;
    			}else{
    				 huokuan = stu.get(i).getHuokuan();
    			}
    			if(stu.get(i).getYunfei()==null || "".equals(stu.get(i).getYunfei())){
    				yunfei = 0d;
    			}else{
    				yunfei = stu.get(i).getYunfei();
    			}
    			 if(stu.get(i).getHuilv() == null || "".equals(stu.get(i).getHuilv())){
    				 huilv = 0d;
    			 }else{
    				 huilv = stu.get(i).getHuilv();
    			 }
    			 if(stu.get(i).getMoney()==null || "".equals(stu.get(i).getMoney())){
    				 money = 0d;
    			 }else{
    				 money = stu.get(i).getMoney();
    			 }
    			 if(((money*huilv)-(huokuan+yunfei+tuikuan*huilv))<0){
    				 if(stu != null)
    		         {			
    					
    					 stu1.add(stu.get(i));
    		             ls.setPagelist(stu1);
    		           
    		             if(pageindex != 0)
    		                 ls.setPageindex(pageindex);
    		         }
    			 }
    		}
    		
        return ls;
    }
    //采购查看利润大于0小于30
    public String thzero(){
        return "thzero";
    }
    //采购查看利润大于0小于30
    public PageModel<OrderTable> getThZeRo(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	List<OrderTable> stu = orderTableDao.getCaiGouLiRunInterval(us.getUserId(), orderId, time, time1);
    	Double tuikuan = 0d;
    	Double huokuan = 0d;
    	Double yunfei = 0d;
    	Double money = 0d;
    	Double huilv = 0d;
    	 PageModel<OrderTable> ls = new PageModel<OrderTable>();
    	 stu1 = new ArrayList();
    		for (int i = 0; i < stu.size(); i++) {
    			if(stu.get(i).getTuikuan() == null || "".equals(stu.get(i).getTuikuan())){
    				tuikuan = 0d;
    			}else{
    				tuikuan = stu.get(i).getTuikuan();
    			}
    			if(stu.get(i).getHuokuan() == null || "".equals(stu.get(i).getHuokuan())){
    				huokuan = 0d;
    			}else{
    				 huokuan = stu.get(i).getHuokuan();
    			}
    			if(stu.get(i).getYunfei()==null || "".equals(stu.get(i).getYunfei())){
    				yunfei = 0d;
    			}else{
    				yunfei = stu.get(i).getYunfei();
    			}
    			 if(stu.get(i).getHuilv() == null || "".equals(stu.get(i).getHuilv())){
    				 huilv = 0d;
    			 }else{
    				 huilv = stu.get(i).getHuilv();
    			 }
    			 if(stu.get(i).getMoney()==null || "".equals(stu.get(i).getMoney())){
    				 money = 0d;
    			 }else{
    				 money = stu.get(i).getMoney();
    			 }
    			 if(((money*huilv)-(huokuan+yunfei+tuikuan*huilv))>0 &&((money*huilv)-(huokuan+yunfei+tuikuan*huilv)) < 30){
    				 if(stu != null)
    		         {			
    					 stu1.add(stu.get(i));
    		             ls.setPagelist(stu1);
    		             if(pageindex != 0)
    		                 ls.setPageindex(pageindex);
    		         }
    			 }
    		}
    		
        return ls;
    }
    public String updedao()
    {
        ordertable = (OrderTable)orderDao.get(ordertable.getId());
        ordertable.setFenpei(0l);
        ordertable.setGetordersId(0l);
        orderDao.merge(ordertable);
        msg = "操作成功";
        return getCaiGouOrder();
    }

    public String updateorder()
    {
        ordertable = (OrderTable)orderDao.get(ordertable.getId());
        ordertable.setFenpei(0l);
        ordertable.setWancheng(0l);
        orderDao.merge(ordertable);
        msg = "操作成功";
        return getDeDaoOrder();
    }

    public String upcaigoudingdan()
    {
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ff = f.format(d);
        request.setAttribute("date", ff);
        return "updatehuokuan";
    }

    public String upfenpeiorder()
    {
        return "updatehuokuans";
    }
    //采购修改得到订单
    public String updatedingdans()
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
        Long kucun = ordertable.getKucun();
        Long kuaidi = ordertable.getKuaidifangshiId();
        String guojia = ordertable.getGuojia();
        String num = request.getParameter("num");
        String bianhao = request.getParameter("bianhao");
        String biaojihao = request.getParameter("biaojihao");
        ordertable = (OrderTable)orderDao.get(ordertable.getId());
        ordertable.setGongyunshang(gongyunshang);
        ordertable.setRemark(beizhu);
        ordertable.setCaigouyuan(cai);
        ordertable.setDanhao(danhao);
        ordertable.setHuokuan(huokuan);
        ordertable.setGuowaidizhi(dizhi);
        ordertable.setWuping(wuping);
        if(huokuan!=null&&!"".equals(huokuan)){
        	ordertable.setCaigoutime(shijian);
        }
        ordertable.setGuoneiwangzhanId(caigou); 
       	ordertable.setKuaidifangshiId(kuaidi);
        ordertable.setKucun(kucun);
        ordertable.setGuojia(guojia);
        ordertable.setXiugai(1l);
        LoginInfo us = (LoginInfo)getFromSession("logininfo");
       // KuCunTable tt = kuCunDao.getKuCunAll(biaojihao,orderId);
        KuCunTable ss = kuCunDao.getOrderAll(orderId);
        KuCunTable aa = kuCunDao.getBiaoHao(biaojihao);
        if(bianhao == null){
        	orderDao.merge(ordertable);
            msg = "操作成功";
            return getCaiGouOrder();
        }
       if(Integer.parseInt(bianhao) == 0){
    	 if(ss == null){
	        if(aa == null){
	        	KuCunTable kk = new KuCunTable();
	        	kk.setOrderId(orderId);
	        	kk.setBiaoji(biaojihao);
	        	kk.setNum(Long.parseLong(num));
	        	kk.setUserid(us.getUserId());
	        	msg = "操作成功";
	        	kuCunDao.merge(kk);
	        	}else{
	        		msg = "编号已经存在、操作失败";
	        		return "updatehuokuan";
	        	}
    	 }else{
    		 msg = "此订单已经有编号";
    		 return "updatehuokuan";
    	 }
        }
        if(Integer.parseInt(bianhao)==1){
        	if(aa == null){
        		msg = "未找到此编号、操作失败";
        		return "updatehuokuan";
        	}else{
        	
        		if(aa.getNum()-(Long.parseLong(num))>0){
        			aa.setId(aa.getId());
        			aa.setNum(aa.getNum()-(Long.parseLong(num)));
        			kuCunDao.merge(aa);
        		}else{
        			msg = "库存"+aa.getNum()+".不足、操作失败";
        			return "updatehuokuan";
        		}
        	}
        }
        
        return getCaiGouOrder();
    }

    public String updatehuokaunAll()
    {
        String gongyunshang = ordertable.getGongyunshang();
        String beizhu = ordertable.getRemark();
        Long cai = ordertable.getCaigouyuan();
        String danhao = ordertable.getDanhao();
        Double huokuan = ordertable.getHuokuan();
        Double yunfei = ordertable.getYunfei();
        ordertable = (OrderTable)orderDao.get(ordertable.getId());
        ordertable.setDanhao(danhao);
        ordertable.setYunfei(yunfei);
        ordertable.setHuokuan(huokuan);
        ordertable.setGongyunshang(gongyunshang);
        ordertable.setRemark(beizhu);
        ordertable.setCaigouyuan(cai);
        ordertable.setXiugai(1l);
        orderDao.merge(ordertable);
        return getDeDaoOrder();
    }

    public OrderTable getUpdateIds()
    {
        OrderTable stu = null;
        try
        {
            stu = (OrderTable)orderDao.get(ordertable.getId());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return stu;
    }
    //采购得到订单
    public String getCaiGouOrder()
    {
    	try {
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
    	       
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		 return "cgdedaoorder";
    }
    //采购得到订单
   /* public PageModel<OrderTable> getCaiGouDeDaoOrder(){
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
          List<OrderTable> ls = orderDao.getDeOrder(us.getUserId(), orderId, time, time1,zhanghaoId);
          PageModel<OrderTable> stu = new PageModel<OrderTable>();
          try
          {
              if(ls != null)
              {
                  stu.setPagelist(ls);
                  if(pageindex != 0)
                      stu.setPageindex(pageindex);
              }
          }
          catch(Exception e)
          {
              e.printStackTrace();
          }
          putInSession("pageindex", pageindex);
          return stu;
    }*/
    //分配到待发货
    public String fenpei_caigou()
    {
        String id[] = request.getParameter("bulletinId").split("-");
        String str[] = new String[id.length];
        for(int i = 0; i < id.length; i++)
        {
        	List<OrderTable> ls = orderDao.getSelId(Long.parseLong(id[i]));
            if(ls.size() != 0)
                if(((OrderTable)ls.get(0)).getWuping() == null || "".equals(((OrderTable)ls.get(0)).getWuping()) || ((OrderTable)ls.get(0)).getGongyunshang() == null || "".equals(((OrderTable)ls.get(0)).getGongyunshang()))
                {
                    str[i] = i+".订单"+ ls.get(0).getOrderId()+"用信息未填";
                } else
                {
                    ((OrderTable)ls.get(0)).setId(Long.parseLong(id[i]));
                    ((OrderTable)ls.get(0)).setDaifahuo(1l);
                    ls.get(0).setFenpei(1l);
                    ls.get(0).setGetordersId(0l);
                    orderDao.merge((OrderTable)ls.get(0));
                    
                    str[i] = i+".操作成功";
                }
        }
        ActionContext.getContext().put("str", str);
        return getCaiGouOrder();
    }

   
    public String updenghuixin()
    {
        try
        {
            String str[] = request.getParameter("denghuixinId").split("-");
            for(int i = 0; i < str.length; i++)
            {
                List<OrderTable> ls = orderDao.getSelId(Long.parseLong(str[i]));
                if(ls.size() != 0)
                {
                    ((OrderTable)ls.get(0)).setId(Long.valueOf(Long.parseLong(str[i])));
                    ((OrderTable)ls.get(0)).setDenghuixin(1l);
                    orderDao.merge((OrderTable)ls.get(0));
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getCaiGouOrder();
    }

    public OrderTable getUpdateDengHuiXin()
    {
        OrderTable stu = null;
        try
        {
            stu = (OrderTable)orderDao.get(ordertable.getId());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return stu;
    }

    public String upUnOrder()
    {
        ordertable = (OrderTable)orderDao.get(ordertable.getId());
        ordertable.setFenpei(2l);
        orderDao.merge(ordertable);
        msg = "操作成功 ";
        return "denghuixin";
    }
    //采购返回速卖通待发货
    public String sumaitong()
    {
        try
        {
            String ch[] = request.getParameter("bulletinId").split("-");
            for(int i = 0; i < ch.length; i++)
            {
                List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i]));
                if(ls.size() != 0)
                {
                    ((OrderTable)ls.get(0)).setId(Long.parseLong(ch[i]));
                    ((OrderTable)ls.get(0)).setSumaitong(1l);
                    ls.get(0).setFenpei(1l);
                    ls.get(0).setGetordersId(0l);
                    orderDao.merge((OrderTable)ls.get(0));
                }
            }

            msg = "操作成功";
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getCaiGouOrder();
    }
    //待发货返回给管理员速卖通
    public String updateSuMaiTong(){
    	try
        {
            String ch[] = request.getParameter("bulletinId").split("-");
            for(int i = 0; i < ch.length; i++)
            {
                List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i]));
                if(ls.size() != 0)
                {
                    ((OrderTable)ls.get(0)).setId(Long.parseLong(ch[i]));
                    ((OrderTable)ls.get(0)).setSumaitong(1l);
                    orderDao.merge((OrderTable)ls.get(0));
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
    //采购修改货款 
    public String updatehuokuan()
    {
        Double huokuan = ordertable.getHuokuan();
        String remark = ordertable.getRemark();
        ordertable = orderDao.get(ordertable.getId());
        ordertable.setHuokuan(huokuan);
        ordertable.setRemark(remark);
        orderDao.merge(ordertable);
        msg = "操作成功";
        return huokuannull();
    }
    public PageModel<OrderTable> getDeDaoSuMaiTong()
    {
        LoginInfo us = (LoginInfo)getFromSession("logininfo");
        List<OrderTable> ls = orderDao.getSuMaiTong(us.getUserId(), orderId, time);
        PageModel<OrderTable> arr = new PageModel<OrderTable>();
        try
        {
            if(ls != null)
            {
                arr.setPagelist(ls);
                if(pageindex != 0)
                    arr.setPageindex(pageindex);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        putInSession("pageindex", Integer.valueOf(pageindex));
        return arr;
    }

    public String upsumaitong()
    {
        try
        {
            String str[] = request.getParameter("sumaitongId").split("-");
            for(int i = 0; i < str.length; i++)
            {
                List<OrderTable> ls = orderDao.getSelId(Long.valueOf(Long.parseLong(str[i])));
                if(ls.size() != 0)
                {
                    ((OrderTable)ls.get(0)).setId(Long.parseLong(str[i]));
                    ((OrderTable)ls.get(0)).setSumaitong(1l);
                    orderDao.merge((OrderTable)ls.get(0));
                }
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getCaiGouOrder();
    }

    public String upUnfinished()
    {
        ordertable = (OrderTable)orderDao.get(ordertable.getId());
        ordertable.setFenpei(2l);
        orderDao.merge(ordertable);
        msg = "操作成功";
        return "sumaitong";
    }

    public String updateSuMaitong()
    {
        return "upsumaitong";
    }

    public OrderTable getSuMaiTongAll()
    {
        OrderTable stu = null;
        try
        {
            stu = (OrderTable)orderDao.get(ordertable.getId());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return stu;
    }

    public String upSuMaiTong()
    {
        String gongyunshang = ordertable.getGongyunshang();
        String beizhu = ordertable.getRemark();
        Long cai = ordertable.getCaigouyuan();
        String danhao = ordertable.getDanhao();
        Double huokuan = ordertable.getHuokuan();
        Double yunfei = ordertable.getYunfei();
        ordertable = (OrderTable)orderDao.get(ordertable.getId());
        ordertable.setDanhao(danhao);
        ordertable.setYunfei(yunfei);
        ordertable.setHuokuan(huokuan);
        ordertable.setGongyunshang(gongyunshang);
        ordertable.setRemark(beizhu);
        ordertable.setCaigouyuan(cai);
        ordertable.setXiugai(1l);
        orderDao.merge(ordertable);
        return "sumaitong";
    }

    public String huokuannull()
    {
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.selPayEmpty(pageSize, pageNumber, us.getUserId(), orderId, caigoutime, caigoutime1);
        return "huokuannull";
    }
//    //采购查看货款为空
//    public PageModel<OrderTable> getHuoKuanNull()
//    {
//        LoginInfo us = (LoginInfo)getFromSession("logininfo");
//        List<OrderTable> ls = orderDao.getCaiGouHuoKuanNull(us.getUserId(), orderId, time, time1);
//        PageModel<OrderTable> arr = new PageModel<OrderTable>();
//        try
//        {
//            if(ls != null)
//            {
//                arr.setPagelist(ls);
//                if(pageindex != 0)
//                    arr.setPageindex(pageindex);
//            }
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//        putInSession("pageindex", pageindex);
//        return arr;
//    }

    public String uphuokuan()
    {
        return "caigouuphuokuan";
    }

    public OrderTable getSelOrderId()
    {
        return (OrderTable)orderDao.get(ordertable.getId());
    }

    public String caigouupdatehuokuan()
    {
        Double huokuan = ordertable.getHuokuan();
        ordertable = (OrderTable)orderDao.get(ordertable.getId());
        ordertable.setHuokuan(huokuan);
        orderDao.merge(ordertable);
        return huokuannull();
    }
    //采购待发货
    public String getDaiFaHuo()
    {
        int pageSize = 10;
        LoginInfo us = (LoginInfo)getFromSession("logininfo");
        pageBean = pageBiz.selCaiGouAdminDaiFaHuo(pageSize, pageNumber, us.getUserId(), orderId, time, time1,caigoutime,caigoutime1,wuping,gongyunshang);
        return "daifahuo";
    }

    public String weiwancheng()
    {
        LoginInfo us = (LoginInfo)getFromSession("logininfo");
        orders = orderDao.getCaiGouDaoChuOrder(us.getUserId());
        return "exportOrder";
    }

    public String xuanzhe()
    {
        orders = new ArrayList();
        String id[] = request.getParameter("bulletinId").split("-");
        for(int i = 0; i < id.length; i++)
        {
            List<OrderTable> os = orderDao.getSelId(Long.parseLong(id[i]));
            System.out.println(os);
            orders.add((OrderTable)os.get(0));
        }

        return "exportOrder";
    }

    public String updaifahuo()
    {
        return "updaifahuo";
    }
    //采购修改订单
    public String upcaigoudaifahuo()
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
        String guojia = ordertable.getGuojia();
        Long kuaidi = ordertable.getKuaidifangshiId();
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
                    ((OrderTable)ls.get(0)).setId(Long.valueOf(Long.parseLong(ch[i])));
                    ((OrderTable)ls.get(0)).setDenghuixin(1l);
                    ls.get(0).setDaifahuo(0l);
                    ls.get(0).setGetordersId(0l);
                    orderDao.merge((OrderTable)ls.get(0));
                }
            }

            msg = "操作成功";
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getOnOrder();
    }
    //采购完成订单返回
    public String fanhuidaifahuo()
    {
        try
        {
            String ch[] = request.getParameter("bulletinId").split("-");
            for(int i = 0; i < ch.length; i++)
            {
                List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i]));
                if(ls.size() != 0)
                {
                    ((OrderTable)ls.get(0)).setId(Long.parseLong(ch[i]));
                    ((OrderTable)ls.get(0)).setWancheng(0l);
                    ((OrderTable)ls.get(0)).setDaifahuo(0l);
                    ls.get(0).setGetordersId(1l);
                    
                    ls.get(0).setDaochu(0l);
                    orderDao.merge((OrderTable)ls.get(0));
                }
            }

            msg = "操作成功";
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getDeDaoOrder();
    }
    //把采购库存订单全部放入待放区
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
                    ls.get(0).setFenpei(1l);
                    ls.get(0).setGetordersId(2l);
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
    	return getCaiGouOrder();
    }
    //采购查看纠纷
    public String jiufen(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.selDisputeOrder(pageSize, pageNumber, us.getUserId(), orderId, caigoutime, caigoutime1, leimus);
    	return "caigoujiufen";
    }
//    //采购查看纠纷
//    public PageModel<OrderTable> getJiuFen()
//    {
//    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
//        List<OrderTable> ls = orderTableDao.getJiuFen(us.getUserId(),orderId, time, time1,leimus);
//        PageModel<OrderTable> stu = new PageModel<OrderTable>();
//        try
//        {
//            if(ls != null)
//            {
//                stu.setPagelist(ls);
//                if(pageindex != 0)
//                    stu.setPageindex(pageindex);
//            }
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//        return stu;
//    }
    //采购查看库存
    public String getKuCun(){
   	 LoginInfo us = (LoginInfo)getFromSession("logininfo");
   	 int pageSize = 10;
   	 pageBean = pageBiz.selKuCunOrder(pageSize, pageNumber,us.getUserId(), orderId);
   	 return "kucuns";
    }
    //采购修改库存
    public String upkucun(){
    	return "upnumbers";
    }
    //采购修改库存 
    public String updatekucuns(){
    	Long num = ordertable.getNum();
    	String remark = ordertable.getRemark();
    	ordertable = orderDao.get(ordertable.getId());
    	ordertable.setNum(num);
    	ordertable.setRemark(remark);
    	orderDao.merge(ordertable);
    	msg = "修改成功 ";
    	return getKuCun();
    }
    //采购查看退货
    public String getTuiHuo(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.selKanTuiHuo(pageSize, pageNumber, us.getUserId(), orderId,danhao, chuli);
    	return "tuihuo";
    }
    //修改处理情况
    public String updateAllChuLi(){
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
                     
                     if(ls.get(0).getChuli() == 1){
                     	
                     	ls.get(0).setChuli(0l);
                     }
                     else if(ls.get(0).getChuli()==0 || ls.get(0).getChuli() == null){
                     	
                     	ls.get(0).setChuli(1l);
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
         return getTuiHuo();
    }
    //采购修改账号
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
                    ls.get(i).setId(Long.parseLong(ch[i]));    
                    if(ls.get(i).getDaochu() == 1){     	
                    	ls.get(i).setDaochu(0l);
                    }
                    else if(ls.get(i).getDaochu()==0){  	
                    	ls.get(i).setDaochu(1l);
                    }
                    orderDao.merge(ls.get(i));
                    str[i] = i + ".操作成功！";
                }
            }
            ActionContext.getContext().put("strsd", str);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "caigoujiufen";
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
                  if(((OrderTable)ls.get(0)).getWuping() == null || "".equals(((OrderTable)ls.get(0)).getWuping()) || ((OrderTable)ls.get(0)).getGongyunshang() == null || "".equals(((OrderTable)ls.get(0)).getGongyunshang()) || ((OrderTable)ls.get(0)).getGuowaidizhi() == null || "".equals(((OrderTable)ls.get(0)).getGuowaidizhi()))
                  {
                      str[i] = i + ".订单"+ls.get(0).getOrderId()+"有信息没填写完整";
                  } else
                  {
                      ls.get(0).setId(Long.parseLong(ch[i]));
                      ls.get(0).setCaigouyuan(us.getUserId());
                      ls.get(0).setDaifahuo(3l);
                      ls.get(0).setFenpei(1l);
                      ls.get(0).setGetordersId(0l);
                      orderDao.merge(ls.get(0));
                      str[i] = i +".操作成功！";
                  }
          }
          ActionContext.getContext().put("strsd", str);
          return getCaiGouOrder();
    }
    //查询国家
    public YunFeiTable getCountry(){
    	
    	YunFeiTable tt = null;
    	try {
    		 OrderTable stu = (OrderTable)orderDao.get(ordertable.getId());
    	  	 tt =  yunFeiTableDao.getCorresponding(stu.getCountry());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
  	 
  	  return tt;
    }
    //查看全部代发
    public String getOnOrder(){
    	LoginInfo us =(LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.selOnbehalf(pageSize, pageNumber, orderId,us.getUserId());
    	return "daifa";
    }
    //修改运输单号
    public String ModifyShipmentNumber(){
    	
    	return "upShipment";
    }
    //修改运输单号
    public String upShipMent(){
    	String danhao = ordertable.getDanhao();
    	ordertable = orderTableDao.get(ordertable.getId());
    	ordertable.setDanhao(danhao);
    	orderTableDao.merge(ordertable);
    	return getOnOrder();
    }
    public void setServletRequest(HttpServletRequest arg0)
    {
        request = arg0;
    }

}
