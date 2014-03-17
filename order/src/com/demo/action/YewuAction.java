package com.demo.action;

import com.demo.dao.GuoJiaDao;
import com.demo.dao.LeiMuDao;
import com.demo.dao.OrderDao;
import com.demo.dao.OrderTableDao;
import com.demo.dao.Order_DetailDao;
import com.demo.entity.LeiMuTable;
import com.demo.entity.Express.YunFeiTable;
import com.demo.entity.order.OrderTable;
import com.demo.entity.order.Order_Detail;
import com.demo.list.PageModel;
import com.demo.page.PageBean;
import com.demo.page.PageBiz;
import com.demo.vo.LoginInfo;
import com.opensymphony.xwork2.ActionContext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
@Controller("yewuAction")
@Scope("prototype")
public class YewuAction extends BaseAction implements ServletRequestAware
{
	private static final long serialVersionUID = 1L;
	@Resource
    private OrderDao orderDao;
	@Resource
    private PageBiz pageBiz;
	@Resource
    private OrderTableDao orderTableDao;
	@Resource
    private GuoJiaDao guoJiaDao;
	@Resource
    private Order_DetailDao order_DetailDao;
	@Resource
    private LeiMuDao leiMuDao;
    private OrderTable ordertable;
    public List<OrderTable> orders;
    public String dhgatezhanghao;
    public int pageindex;
    public String orderId;
    public String msg;
    public String remark;
    public String gongyunshang;
    public Long selzhanghao;
    public String danhao;
    public String time;
    public String time1;
    public String guoneidanhao;
    public int pageNumber;
    private PageBean pageBean;
    private HttpServletRequest request;
	
    public OrderTable getOrdertable()
    {
        return ordertable;
    }

    public void setOrdertable(OrderTable ordertable)
    {
        this.ordertable = ordertable;
    }

    public PageBean getPageBean()
    {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean)
    {
        this.pageBean = pageBean;
    }
    //业务修改订单
    public String getorderIdAll()
    {
        return "updateorders";
    }

    public OrderTable getUpdateIds()
    {
        OrderTable stu = null;
        try
        {
            stu = orderDao.get(ordertable.getId());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return stu;
    }
    //业务修改订单
    public String updatedingdanAll()
    {
    	 Long cai = ordertable.getCaigouyuan();
         String order = ordertable.getOrderId();
         String yunshu = ordertable.getYunshu();
         Double yunfei = ordertable.getYunfei();
         String guojia = ordertable.getGuojia();
         String remark = ordertable.getRemark();
         Double huilv = ordertable.getHuilv();
         Double tuikuan = ordertable.getTuikuan();
         Long shangwang = ordertable.getShangwang();
         Long qianshou = ordertable.getQianshou();
         Long ruzhang = ordertable.getRuzhang();
         String danhao = ordertable.getDanhao();
         Long jiufen = ordertable.getJiufen();
         Double huokuan = ordertable.getHuokuan();
         Double jine = ordertable.getMoney();
         Long tuihuo = ordertable.getTuihuo();
         String bianma = ordertable.getBianma(); 
         String gongyunshang = ordertable.getGongyunshang();
         java.sql.Date time = ordertable.getJiufentime();
         ordertable = (OrderTable)orderDao.get(ordertable.getId());
         ordertable.setCaigouyuan(cai);
         ordertable.setOrderId(order);
         ordertable.setYunshu(yunshu);
         ordertable.setYunfei(yunfei);
         ordertable.setGuojia(guojia);
         ordertable.setRemark(remark);
         ordertable.setHuilv(huilv);
         ordertable.setTuikuan(tuikuan);
         ordertable.setShangwang(shangwang);
         ordertable.setQianshou(qianshou);
         ordertable.setRuzhang(ruzhang);
         ordertable.setDanhao(danhao);
         ordertable.setJiufen(jiufen);
         ordertable.setHuokuan(huokuan);
         ordertable.setTuihuo(tuihuo);
         ordertable.setMoney(jine);
         ordertable.setBianma(bianma);
         ordertable.setGongyunshang(gongyunshang);
         if(jiufen ==1 && (time == null || "".equals(time))){
        	 ordertable.setJiufentime(new java.sql.Date(System.currentTimeMillis()));
         }else if(jiufen ==1 && (time != null && !"".equals(time))){
        	    ordertable.setJiufentime(time);
         }
     
         ordertable.setXiugai(1l);
         orderDao.merge(ordertable);
         msg = "修改成功";
        return getDeDaoOrderAll();
    }
    //业务得到修改订单
    public String getDeDaoOrderAll()
    {
        int pageSize = 10;
        pageBean = pageBiz.selYeWuDeDaoOrder(pageSize, pageNumber, orderId, gongyunshang, selzhanghao, danhao);
        return "yewuallorder";
    }

    //业务查看运输单号
    public String getYunShuAll()
    {
    	 int pageSize = 10;
         pageBean = pageBiz.selAdminDanHao(pageSize, pageNumber, orderId, time, time1);
    	return "yunshunull";  
    }

    //业务查看运费为空
    public String getYunFeiNull()
    {
       int pageSize = 10;
       pageBean = pageBiz.selYeWuYunFeiNull(pageSize, pageNumber, orderId, time, time1);
        return "yunfei";
    }
    //业务修改运输单号
    public String upyworder()
    {
        return "upyworder";
    }

    public OrderTable getYeWuId()
    {
        return orderDao.get(ordertable.getId());
    }
    //业务修改运输单号
    public String updtewuorder()
    {
        String danhao = ordertable.getDanhao();
        ordertable = orderDao.get(ordertable.getId());
        ordertable.setDanhao(danhao);
        orderDao.merge(ordertable);
        msg = "操作成功";
        return getYunShuAll();
    }

    public String upywyunfei()
    {
        return "upywyunfei";
    }
    //业务修改运费
    public String updateyunfei()
    {
        Double yunfei = ordertable.getYunfei();
        ordertable = orderDao.get(ordertable.getId());
        ordertable.setYunfei(yunfei);
        orderDao.merge(ordertable);
        msg = "操作成功";
        return getYunFeiNull();
    }
    //业务查看速卖通已入单
    public String getChaKanOrder()
    {
        int pageSize = 10;
        LoginInfo us = (LoginInfo)getFromSession("logininfo");
        pageBean = pageBiz.selSuMaiTongRuDan(pageSize, pageNumber, us.getUserId(), orderId, time, time1);
        return "sumaitong";
    }
    //业务修改速卖通已经完成 订单
    public String yijingwancheng()
    {
        String id[] = request.getParameter("bulletinId").split("-");
        for(int i = 0; i < id.length; i++)
        {
            List<OrderTable> ls = orderDao.getSelId(Long.parseLong(id[i]));
            if(ls.size() != 0)
            {
                ls.get(0).setId(Long.parseLong(id[i]));
                ls.get(0).setWancheng(1l);
                ls.get(0).setSumaitong(0l);
                orderDao.merge(ls.get(0));
            }
        }

        msg = "操作成功";
        return getChaKanOrder();
    }

    public String upsumaitong()
    {
        return "upsumaitong";
    }

    public String updatesumaitong()
    {
        Double yunfei = ordertable.getYunfei();
        String danhao = ordertable.getDanhao();
        Double huokuan = ordertable.getHuokuan();
        String remark = ordertable.getRemark();
        ordertable = (OrderTable)orderDao.get(ordertable.getId());
        ordertable.setYunfei(yunfei);
        ordertable.setDanhao(danhao);
        ordertable.setHuokuan(huokuan);
        ordertable.setRemark(remark);
        msg = "修改成功";
        orderDao.merge(ordertable);
        return getChaKanOrder();
    }
    //业务已经入单返回给管理员
    public String fanhuiadmin()
    {
        ordertable = (OrderTable)orderDao.get(ordertable.getId());
        ordertable.setSumaitong(1l);
        orderDao.merge(ordertable);
        return getChaKanOrder();
    }
    //业务查看未入单订单
    public String getWeiRuDang()
    {
        int pageSize = 10;
        pageBean = pageBiz.selSuMaiTongWeiRuDan(pageSize, pageNumber, orderId, time, time1);
        return "weirudang";
    }

    public String upweirudang()
    {
        return "upweirudang";
    }
    //业务修改速卖通未录单
    public String updateweirudang()
    {
        Double yunfei = ordertable.getYunfei();
        String remark = ordertable.getRemark();
        String gongyunshang = ordertable.getGongyunshang();
        ordertable = (OrderTable)orderDao.get(ordertable.getId());
        ordertable.setYunfei(yunfei);
        ordertable.setRemark(remark);
        ordertable.setGongyunshang(gongyunshang);
        orderDao.merge(ordertable);
        return getWeiRuDang();
    }
    //业务速卖通代发
    public String getDaiFaAll()
    {
        int pageSize = 10;
        pageBean = pageBiz.selSuMaiTongDaiFa(pageSize, pageNumber, orderId, time, time1);
        return "daifa";
    }
    //业务把代发订单导入完成订单 
    public String wancheng()
    {
        String[] id = request.getParameter("bulletinId").split("-");
        for(int i = 0; i < id.length; i++)
        {
            List<OrderTable> ls = orderDao.getSelId(Long.parseLong(id[i]));
            if(ls.size() != 0)
            {
                ls.get(0).setId(Long.valueOf(Long.parseLong(id[i])));
                ls.get(0).setDaifahuo(0l);
                ls.get(0).setSumaitong(0l);
                ls.get(0).setFenpei(1l);
                ls.get(0).setWancheng(1l);
                ls.get(0).setDaochu(0l);
                ls.get(0).setGetordersId(0l);
                orderDao.merge(ls.get(0));
            }
        }

        msg = "操作成功";
        return getDaiFaAll();
    }
    //修改代发
    public String updaifa()
    {
        return "updaifa";
    }
    //修改代发
    public String updatedaifa()
    {
        String danhao = ordertable.getDanhao();
        Double yunfei = ordertable.getYunfei();
        ordertable = (OrderTable)orderDao.get(ordertable.getId());
        ordertable.setDanhao(danhao);
        ordertable.setYunfei(yunfei);
        msg = "操作成功";
        orderDao.merge(ordertable);
        return getDaiFaAll();
    }
    //业务返回代发给采购
    public String fanhuidaifa()
    {
        ordertable = orderDao.get(ordertable.getId());
        ordertable.setDenghuixin(0l);
        ordertable.setGetordersId(1l);
        orderDao.merge(ordertable);
        return getDaiFaAll();
    }
 
    
    //速卖通未入单返回给管理员
    public String adminfanhui(){
    	 ordertable = (OrderTable)orderDao.get(ordertable.getId());
         ordertable.setSumaitong(1l);
         orderDao.merge(ordertable);
          return getWeiRuDang();
    }
    //业务查看未入账订单
    public String getWeiRuZhang(){
    	int pageSize = 10;
    	pageBean = pageBiz.selAllWeiRuZhang(pageSize, pageNumber, orderId, time, time1, dhgatezhanghao, danhao);
    	return "weiruzhang";
    }
    //业务修改入账情况
    public String updateAllRuZhang(){
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
                     
                     if(ls.get(0).getRuzhang() == 1){
                     	
                     	ls.get(0).setRuzhang(0l);
                     }
                     else if(ls.get(0).getRuzhang()==0){
                     	
                    	 ls.get(0).setRuzhang(1l);
                    	 java.util.Date ds = new java.util.Date();
                         SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                         String ffs = fs.format(ds);
                    	 ls.get(0).setRuzhangtime(fs.parse(ffs));
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
         return getWeiRuZhang();
    }
    //查询17track
    public String getTrack(){
    	
    	return "test";
    }
    //查询未入账和未签收
    public List<OrderTable> getWrzWqs(){
    
    	List<OrderTable> ss = null;
    	try {
        	 ss=orderTableDao.getWrzWqs();
        	 if(ss.size()!=0){
	        	for (int i = 0; i < 40; i++) {
	    			ss.get(i).setId(ss.get(i).getId());
	    			ss.get(i).setYjcx(1l);
	    			orderTableDao.merge(ss.get(i));
	    		}
        	 }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
    	return ss;
    }
    //查询17track
    public String getUpTrack(){
    	
		List<OrderTable> tt = orderTableDao.getQbdd();   
		try {
		
	    	for (int i = 0; i < tt.size(); i++) {
	    		if(tt.size()!=0){
	    			
	    			if(tt.get(i).getRuzhang()==0 && tt.get(i).getQianshou()==0){
	    			
	    				tt.get(i).setId(tt.get(i).getId());
	    				tt.get(i).setYjcx(0l);
	    				orderTableDao.merge(tt.get(i));
	    			}
	    		}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
    	return "test";
    }
    //修改类目
    public String getModifyCategory(){
    
    	try {
    		int pageSize = 10;
        	pageBean = pageBiz.selAllCgs(pageSize, pageNumber);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
    	
    	return "ModifyCategory";
    }
	public String upCategory()
	{
	
		   try
	        {
	            String ch[] = request.getParameter("bulletinId").split("-");
	            String sel[] = request.getParameter("seluserid").split("-");
	            String str[] = new String[ch.length];
	            for(int i = 0; i < ch.length; i++)
	            {
	                List<Order_Detail> ls = order_DetailDao.getAllSelId(Long.parseLong(ch[i]));
	                List<LeiMuTable> lei = leiMuDao.getSelId(Long.parseLong(sel[i]));
	                if(sel.length != ch.length)
	                    str[i] = i+".操作失败";
	                else
	                if("".equals(sel[i]) || sel[i] == null)
	                    str[i] = i+".操作失败";
	                else
	                if(ls.size() != 0)
	                {
	                    ls.get(0).setId(Long.parseLong(ch[i]));     
	                    ls.get(0).setSfModify(1l);
	                    ls.get(0).setCat_name(lei.get(0).getLeimu());
	                    order_DetailDao.merge(ls.get(0));
	                    str[i] = i+".修改成功！";
	                }
	            }
	            ActionContext.getContext().put("strsd", str);
	        }
	        catch(Exception e)
	        {
	            e.printStackTrace();
	        }
		return getModifyCategory();
	}
    public void setServletRequest(HttpServletRequest arg0)
    {
        request = arg0;
    }
}
