
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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
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
    private KuCunTable kucuntable;
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
    private List<File> uploadFile;
    private List<String> uploadFileFileName;
    private List<String> uploadFileContentType;
    private HttpServletRequest request;     


	public KuCunTable getKucuntable() {
		return kucuntable;
	}

	public void setKucuntable(KuCunTable kucuntable) {
		this.kucuntable = kucuntable;
	}

	public List<File> getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(List<File> uploadFile) {
		this.uploadFile = uploadFile;
	}

	public List<String> getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(List<String> uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public List<String> getUploadFileContentType() {
		return uploadFileContentType;
	}

	public void setUploadFileContentType(List<String> uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}
	private String caption;  
    
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
//    //修改金额
//    public String jsmoney(){
//    	System.out.println("++++修改金额++");
//    	String num = request.getParameter("num");
//    	String goods = request.getParameter("goods");
//    	String id = request.getParameter("id");
//        List<KuCunTable> kk = kuCunDao.getGoodsUserId(goods);
//        ordertable = orderDao.get(Long.parseLong(id));
//        if (kk.size() != 0) {
//        	if (Long.parseLong(num)>kk.get(0).getNum()) {
//				msg = "库存数量剩"+kk.get(0).getNum()+",数量不足、操作失败";
//			}else{
//				ordertable.setMoney(Long.parseLong(num)*kk.get(0).getUnitprice());
//				orderDao.merge(ordertable);
//			}
//		}
//    	return "updatehuokuan";
//    }
    //采购修改得到订单
    public String updatedingdans() throws Exception
    {
    	System.out.println("+++库存订单+++");
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
        String miaosu = request.getParameter("miaosu");
        ordertable = (OrderTable)orderDao.get(ordertable.getId());
        ordertable.setGongyunshang(gongyunshang);
        ordertable.setRemark(beizhu);
        ordertable.setCaigouyuan(cai);
        ordertable.setDanhao(danhao);
        ordertable.setGuowaidizhi(dizhi);
        ordertable.setCaigoutime(shijian);
        ordertable.setGuoneiwangzhanId(caigou); 
       	ordertable.setKuaidifangshiId(kuaidi);
        ordertable.setGuojia(guojia);
        ordertable.setXiugai(1l);
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ff = f.format(d);
        if (Long.parseLong(kucun) == 1) {
      
             List<KuCunTable> kk = kuCunDao.getGoodsUserId(wuping);
             if (kk.size() == 0) {
     			msg = "库存订单里面没有找到("+wuping+")此物品、操作失败";
     			return "updatehuokuan";
     		}else{
     			Long ku=0l;
     			Long nn = 0l;
     			if (kk.get(0).getNum() == null || kk.get(0).getNum() == 0l) {
					nn = 0l;
				}else{
					nn = kk.get(0).getNum();
				}
     			if (kk.get(0).getZynum()==null || kk.get(0).getZynum() == 0) {
					ku = 0l;
				}else{
					ku=kk.get(0).getZynum();
				}
     			if ((ku+(Long.parseLong(num)))>nn) {
					msg = "占用库存大于库存数量、"+(((kk.get(0).getZynum())+(Long.parseLong(num)))-(kk.get(0).getNum()));
					return "updatehuokuan";
				}
     			if (kk.get(0).getNum() < Long.parseLong(num)) {
     				kk.get(0).setId(kk.get(0).getId());
     				msg= "库存数量还差"+((Long.parseLong(num))-(kk.get(0).getNum()))+"、请在物品那一栏修改物品数量";
     			//	kk.get(0).setNum(0l);
     			//	kk.get(0).setOrderId(orderId);
     				kk.get(0).setSytime(f.parse(ff));
     				Long zy = 0l;
     				if (kk.get(0).getZynum() != null) {
						zy = kk.get(0).getZynum() + kk.get(0).getZynum();
						
					}else{
						zy = kk.get(0).getZynum();
					}
     				kk.get(0).setZynum(zy);
     				kk.get(0).setZynum(kk.get(0).getNum());
     				ordertable.setHuokuan((kk.get(0).getUnitprice())*(kk.get(0).getNum()));
     				ordertable.setNum(kk.get(0).getNum());
     				kk.get(0).setThnum(kk.get(0).getNum());
     				kuCunDao.merge(kk.get(0));
     			}else{
     				Long zy = 0l;
 					
     				if (kk.get(0).getZynum() != null && kk.get(0).getZynum() != 0) {
						zy = Long.parseLong(num) + kk.get(0).getZynum();
						
					}else{
						zy = Long.parseLong(num);
					}
     				kk.get(0).setId(kk.get(0).getId());
     			//	kk.get(0).setNum((kk.get(0).getNum()-(Long.parseLong(num))));
     			//	kk.get(0).setOrderId(orderId);
     				kk.get(0).setSytime(f.parse(ff));
     				kk.get(0).setZynum(zy);
     				ordertable.setNum(Long.parseLong(num));
     				ordertable.setHuokuan((kk.get(0).getUnitprice())*(Long.parseLong(num)));
     				kk.get(0).setThnum(Long.parseLong(num));
     				kuCunDao.merge(kk.get(0));
     			}
     			ordertable.setKucun(1l);
     			ordertable.setKucunid(kk.get(0).getId());
     			ordertable.setWuping(wuping+","+(Long.parseLong(num))+miaosu);
     		}
             
		}
        if (Long.parseLong(kucun) == 0) {
            ordertable.setHuokuan(huokuan);
            ordertable.setWuping(wuping);
		}
		orderDao.merge(ordertable);
		System.out.println("+库存+++");
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
    public KuCunTable getKuCunIds()
    {
    	KuCunTable stu = null;
        try
        {
            stu = kuCunDao.get(kucuntable.getId());
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
                    ls.get(0).setFenpei(1l);
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
    	pageBean = pageBiz.selKanTuiHuo(pageSize, pageNumber, us.getUserId(), orderId,danhao);
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
  //批量上传图片
    public String uploadInventoryOrders() throws Exception{
    	try {
    		String root = null;
        	//得到工程保存图片的路径
    		if (uploadFile != null) {
    			root = ServletActionContext.getServletContext().getRealPath("WebRoot/"+"upload/");		
			}
            String[] coding = request.getParameterValues("bianma");
            String[] num = request.getParameterValues("num");
          //  String[] totalprice = request.getParameterValues("totalprice");//总价
            String[] unitprice = request.getParameterValues("unitprice");
            LoginInfo us = (LoginInfo)getFromSession("logininfo");
            String[] wuping = request.getParameterValues("wuping");
            String[] transportproviders = request.getParameterValues("transportproviders");
            Date ds = new Date();
            SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String ffs = fs.format(ds);
            KuCunTable kk = new KuCunTable();
            File dirs = new File(root);//+(System.currentTimeMillis())   
            if(!dirs.exists()){  
                dirs.mkdirs();  
            }
            if(uploadFile == null){  
                return null;  
            }else{
            //循环上传的文件
            for(int i = 0 ; i < uploadFile.size() ; i ++){
            	List<KuCunTable> pic = kuCunDao.getThePictureName("upload"+"/"+this.getUploadFileFileName().get(i), us.getUserId());
            	String[] stu = new String[uploadFile.size()];
            	if (pic.size() == 0) {
            		List<KuCunTable> cc = kuCunDao.getGoodsUserId(wuping[i]);
            		if (cc.size() == 0 || cc.get(0).getNum() == null || cc.get(0).getNum() == 0l) {
               	 	InputStream is = new FileInputStream(uploadFile.get(i)); 
                    //得到图片保存的位置(根据root来得到图片保存的路径在tomcat下的该工程里)
                    File destFile = new File(root,this.getUploadFileFileName().get(i));
                    //把图片写入到上面设置的路径里
                    OutputStream os = new FileOutputStream(destFile);
                    byte[] buffer = new byte[1024];
                    int length  = 0;
                    while((length = is.read(buffer))>0){
                        os.write(buffer, 0, length);
                    }
               	if (cc.size() == 0 ) {
               		 kk.setCoding(coding[i]);
                        kk.setNum(Long.parseLong(num[i]));
                        kk.setTotalprice(Double.parseDouble(unitprice[i])*Double.parseDouble(num[i]));
                        kk.setUnitprice(Double.parseDouble(unitprice[i]));
                        kk.setWuping(wuping[i]);
                        kk.setUserid(us.getUserId());
                        kk.setTransportproviders(transportproviders[i]);
                        kk.setUploadFile("upload"+"/"+this.getUploadFileFileName().get(i));//+(System.currentTimeMillis())
                        kk.setTime(fs.parse(ffs));
                        stu[i] = i+"操作成功";
                        kuCunDao.merge(kk);
       			}else{
       				cc.get(i).setId(cc.get(i).getId());
       				cc.get(i).setCoding(coding[i]);
       				cc.get(i).setNum(Long.parseLong(num[i]));
       				cc.get(i).setTotalprice(Double.parseDouble(unitprice[i])*Double.parseDouble(num[i]));
       				cc.get(i).setUnitprice(Double.parseDouble(unitprice[i]));
       				cc.get(i).setUserid(us.getUserId());
       				cc.get(i).setTransportproviders(transportproviders[i]);
       				cc.get(i).setUploadFile("upload"+"/"+this.getUploadFileFileName().get(i));//+(System.currentTimeMillis())
       				cc.get(i).setWuping(wuping[i]);
       				cc.get(i).setTime(fs.parse(ffs));
       				stu[i] = i+"操作成功";
       				kuCunDao.merge(cc.get(i));
       			}
                   is.close();
                   os.close();
            	}else{
            		msg = "此物品还有库存、请库存用完之后再添加";
					return "addStock";
            	}
    			}else{
    				stu[i] = i+"图片名称已经存在";
    			}
            	 ActionContext.getContext().put("insert", stu);
            }
            }
           
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

    	return "addStock";
    }
  //显示库存订单
    public String getStockOrder(){
    	try {
    		LoginInfo us = (LoginInfo)getFromSession("logininfo");
        	int pageSize = 10;
        	pageBean = pageBiz.selStockOrder(pageSize, pageNumber, us.getUserId(),time,time1,bianma,orderId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    
    	return "stockorder";
    }
    //显示问题订单
    public String getIssuesOrders(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.selIssuesOrders(pageSize, pageNumber, orderId, us.getUserId(),time,time1);
    	return "issuesorders";
    }
    //将完成订单返回给代发 
    public String fhdaifahuo()
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
                    ls.get(0).setFenpei(1l);
                    ls.get(0).setWancheng(0l);
                    ls.get(0).setDaifahuo(0l);
                    ls.get(0).setDaochu(0l);
                    ls.get(0).setSumaitong(0l);
                    ls.get(0).setGetordersId(1l);
                    orderDao.merge(ls.get(0));
                    str[i] = i + ".操作成功！";
                } 
            }

            ActionContext.getContext().put("wenti", str);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getIssuesOrders();
    }
    //修改库存
    public String upStock(){
    	return "upstock";
    }

	//上传库存
    public String addStock(){
    	return "addStock";
    }
    //修改库存
    public String updateStock(){
    	try {
    		LoginInfo us = (LoginInfo)getFromSession("logininfo");
        	//得到工程保存图片的路径
    		String root = null;
    		if (uploadFile != null) {
    			root = ServletActionContext.getServletContext().getRealPath("WebRoot/"+"upload/");		
			}
    		   String bianma = kucuntable.getCoding();
               Long num = kucuntable.getNum();
               Double total = kucuntable.getTotalprice();
               Double unitprice = kucuntable.getUnitprice();
               String wuping = kucuntable.getWuping();
               String transportproviders = kucuntable.getTransportproviders();
               kucuntable = kuCunDao.get(kucuntable.getId());
               kucuntable.setNum(num);
               kucuntable.setCoding(bianma);
               kucuntable.setTotalprice(total);
               kucuntable.setUnitprice(unitprice);
               kucuntable.setWuping(wuping);
               kucuntable.setTransportproviders(transportproviders);
            File dirs = new File(root);//+(System.currentTimeMillis())   
            if(!dirs.exists()){  
                dirs.mkdirs();  
            }
            if(uploadFile == null){  
            	orderDao.merge(ordertable);
                return  getStockOrder();
            }else{
            //循环上传的文件
            for(int i = 0 ; i < uploadFile.size() ; i ++){
            
        		String[] stu = new String[uploadFile.size()];
           	 	InputStream is = new FileInputStream(uploadFile.get(i)); 
                //得到图片保存的位置(根据root来得到图片保存的路径在tomcat下的该工程里)
                File destFile = new File(root,this.getUploadFileFileName().get(i));
                //把图片写入到上面设置的路径里
                OutputStream os = new FileOutputStream(destFile);
                byte[] buffer = new byte[1024];
                int length  = 0;
                while((length = is.read(buffer))>0){
                    os.write(buffer, 0, length);
                }
                kucuntable.setUploadFile("upload"+"/"+this.getUploadFileFileName().get(i));
               is.close();
               os.close();
               ActionContext.getContext().put("insert", stu);
            }
          }
           
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
           kuCunDao.merge(kucuntable);
           return getStockOrder();
    }
    //下单审核
    public String singleaudit(){
    	String ch[] = request.getParameter("bulletinId").split("-");
        String str[] = new String[ch.length];
        for(int i = 0; i < ch.length; i++)
        {
            List<OrderTable> ls = orderDao.getSelId(Long.valueOf(Long.parseLong(ch[i])));
            if(ls.size() != 0){
                    ls.get(0).setId(Long.parseLong(ch[i]));
                    ls.get(0).setSingleAuditId(1l);
                    ls.get(0).setFenpei(1l);
                    ls.get(0).setGetordersId(0l);
                    orderDao.merge(ls.get(0));
                    str[i] = i +".操作成功！";
            }
        }
        ActionContext.getContext().put("strsd", str);
    	return getCaiGouOrder();
    }
    public void setServletRequest(HttpServletRequest arg0)
    {
        request = arg0;
    }

}
