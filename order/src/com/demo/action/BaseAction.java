
package com.demo.action;

import com.demo.bean.MyHibernateTemplate;
import com.demo.dao.*;
import com.demo.dao.Express.YunFeiTableDao;
import com.demo.dao.Express.YunFeieDao;
import com.demo.dao.user.CaiGouAdminDao;
import com.demo.dao.user.CaiGouDao;
import com.demo.dao.user.CaiWuDao;
import com.demo.dao.user.CangKuYuanDao;
import com.demo.dao.user.ClipArtDao;
import com.demo.dao.user.GuKeDao;
import com.demo.dao.user.UserDao;
import com.demo.dao.user.YeWu1Dao;
import com.demo.dao.user.YeWuDao;
import com.demo.entity.*;
import com.demo.entity.Express.YunFeiTable;
import com.demo.entity.order.DhgateAccounts;
import com.demo.entity.order.OrderTable;
import com.demo.entity.order.Order_Detail;
import com.demo.entity.user.ClipArt;
import com.demo.entity.user.GuKeTable;
import com.demo.entity.user.UserInfo;
import com.demo.entity.user.YeWu1;
import com.demo.vo.LoginInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

public abstract class BaseAction extends ActionSupport implements ServletRequestAware
{
    private static final long serialVersionUID = 1L;
    protected Log log;
    @Resource
    private MenuDao menuDao;
    @Resource
    private YuCunDao yuCunDao;
    @Resource
    private XieXinDao xieXinDao;
    @Resource
    private YunFeiTableDao yunFeiTableDao;
    @Resource
    private CangKuYuanDao cangKuYuanDao;
    @Resource
    private CaiWuDao caiWuDao;
    @Resource
    private ClipArtDao clipArtDao;
    @Resource
    private LeiMuDao leiMuDao;
    @Resource
    private GuoJiaDao guoJiaDao;
    @Resource
    private GuKeDao guKeDao;
    @Resource
    public MyHibernateTemplate ht;
    @Resource
    private UserDao userDao;
    @Resource
    private GuoNeiKuaiDiDao guoNeiKuaiDiDao;
    @Resource
    private OrderDao orderDao;
    @Resource
    private OrderTableDao orderTableDao;
    @Resource
    private YeWuDao yeWuDao;
    @Resource
    private YeWu1Dao yeWu1Dao;
    @Resource
    private CaiGouDao caiGouDao;
    @Resource
    private CaiGouAdminDao caiGouAdminDao;
    @Resource
    private ZhangHaoDao zhangHaoDao;
    @Resource
    private GuoNeiWangZhanDao guoNeiWangZhanDao;
    @Resource
    private KuaiDiFangShiDao kuaiDiFangShiDao;
    public Long zhanghaoId;
    private OrderTable ordertable;
    public String tit;
    public Long id;
    public String sumaitong;
    public String msg;
    private HttpServletRequest request;
    public BaseAction()
    {
        log = LogFactory.getLog(getClass());
    }

    public OrderTable getOrdertable()
    {
        return ordertable;
    }

    public void setOrdertable(OrderTable ordertable)
    {
        this.ordertable = ordertable;
    }

    public void putInSession(String key, Serializable obj)
    {
        ActionContext.getContext().getSession().put(key, obj);
    }

    public Map loaditems(String classname, String values, String key)
    {
        String sql = (new StringBuilder("select a.")).append(values).append(",a.").append(key).append(" from ").append(classname).append(" a").toString();
        Map map = new TreeMap();
        List ls = ht.find(sql);
        Object obj[];
        for(Iterator iterator = ls.iterator(); iterator.hasNext(); map.put(obj[1].toString(), obj[0].toString()))
            obj = (Object[])iterator.next();

        return map;
    }

    public Object loadObj(String classname, String values, String key)
    {
        String sql = (new StringBuilder("select a.")).append(values).append(" from ").append(classname).append(" a").toString();
        Map map = new TreeMap();
        List ls = ht.find(sql);
        Object obj[];
        for(Iterator iterator = ls.iterator(); iterator.hasNext(); map.put(obj[1].toString(), obj[0].toString()))
            obj = (Object[])iterator.next();

        return map;
    }

    public void removeFromSession(String key)
    {
        ActionContext.getContext().getSession().remove(key);
    }

    public Object getFromSession(String key)
    {
        return ActionContext.getContext().getSession().get(key);
    }

    public List<MyMenu> getMenu(Integer role)
    {
        return menuDao.getByMenu(role);
    }

    public List<ZhangHao> getZhangHao()
    {
        return zhangHaoDao.getAllZhangHao();
    }
    //得到全部采购
    public List getCaiGousd(Long usertype)
    {
    	
    	List c= null;
    	try {
    		
    		if(usertype == null || usertype == 0){
	       		
	       		c = caiGouDao.getCaiGou();
	       	}
    		else if(usertype == 1){
       		 c = caiGouDao.getCaiGou();
	       	}
    		
    		else if(usertype == 2){
	      		 	c = caiGouAdminDao.getCaiGouAdminAll();
	       	}	       	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	return c;
    }

    public String getUserId(Long userid)
    {
        UserInfo mc = (UserInfo)userDao.get(userid);
        return mc.getName();
    }
    //顾客编号查询返回姓名
    public Double getName(Long userid)
    {
        YunCun mc = (YunCun)yuCunDao.get(userid);
        return mc.getMoney();
    }
    public String getZhangHaoId(String zhanghaoId)
    {
        ZhangHao zhang = zhangHaoDao.getZhangHaoId(Long.parseLong(zhanghaoId));
        return zhang.getName();
    }

    public String getTitle()
    {
        if(tit == null)
        {
            return "欢迎页面";
        } else
        {
            MyMenu mm = menuDao.get(Long.parseLong(tit));
            return mm.getName();
        }
    }
    //管理员查看总金额
    public List<OrderTable> getMoney(String orderId, String time, String time1, String zhanghao,String danhao,String sumaitong,String bianma,String leimu)
    {
    	 List<OrderTable> ss = null;
    	try {
        	ss = orderDao.getAllMoney(orderId, time, time1, zhanghao,danhao,sumaitong,bianma,leimu);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
        return ss;
    }
    //管理员查看总运费
    public  List<OrderTable> getYunFei(String orderId, String time, String time1, String zhanghao,String danhao,String sumaitong,String bianma,String leimu)
    {
    	
        return  orderDao.getAllYunFei(orderId, time, time1, zhanghao,danhao,sumaitong,bianma,leimu);
    }
    //管理员查看总货款
    public  List<OrderTable> getHuoKuan(String orderId, String time, String time1, String zhanghao,String danhao,String sumaitong,String bianma,String leimu)
    {
    	
        return orderDao.getAllHuoKuan(orderId, time, time1, zhanghao,danhao,sumaitong,bianma,leimu);
    }
    //管理员查看总退款
    public  List<OrderTable> getTuiKuan(String orderId, String time, String time1, String zhanghao,String danhao,String sumaitong,String bianma,String leimu)
    {

        return orderDao.getAllTuiKuan(orderId, time, time1, zhanghao,danhao,sumaitong,bianma,leimu);
    }
    //管理员查看总利润
    public List<OrderTable> getZongLiRuns(String orderId, String time, String time1, String zhanghao,String danhao,String sumaitong,String bianma,String leimu)
    {
    	
        return orderDao.getZongLiRun(orderId, time, time1, zhanghao,danhao,sumaitong,bianma,leimu);
    }
    //管理员查看纠纷个数
    public List<OrderTable> getJiuFen(String orderId, String time, String time1, String zhanghao,String danhao,String sumaitong,String bianma,String leimu)
    {
        return orderDao.getJiuFenNum(orderId, time, time1, zhanghao,danhao,sumaitong,bianma,leimu);
    }
    //管理员查看运费为空
    public List<OrderTable> getYunFeiNullNum(String orderId, String time, String time1, String zhanghao,String danhao,String sumaitong,String bianma,String leimu)
    {
        return orderDao.getYunFeiNullNum(orderId, time, time1, zhanghao,danhao,sumaitong,bianma,leimu);
    }
    //管理员查看货款为空
    public List<OrderTable> getHuoKuanNullNum(String orderId, String time, String time1, String zhanghao,String danhao,String sumaitong,String bianma,String leimu)
    {
        return orderDao.getHuoKuanNullNum(orderId, time, time1, zhanghao,danhao,sumaitong,bianma,leimu);
    }
    //管理员查看纠纷总额
    public  List<OrderTable> getCaiGouAllJiuFenMoney(String orderId, String time, String time1, String zhanghao,String danhao,String sumaitong,String bianma,String leimu)
    {
        return orderDao.getCaiGouAllJiuFenMoney(orderId, time, time1, zhanghao,danhao,sumaitong,bianma,leimu);
    }
    //总金额
    public List<OrderTable> getCaiGouAllMoney(Long caigouyuan, String orderId, String time, String time1)
    {
    	 List<OrderTable> stu = null;
        try
        {
            stu = orderDao.getCaiGouAllMoney(caigouyuan, orderId, time, time1);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return stu;
    }
    //总运费
    public  List<OrderTable> getCaiGouAllYunFei(Long caigouyuan, String orderId, String time, String time1)
    {
        return orderDao.getCaiGouAllYunFei(caigouyuan, orderId, time, time1);
    }
    //采购退款个数
    public  List<OrderTable> getCaiGouAllTuiKuan(Long caigouyuan, String orderId, String time, String time1)
    {
        return orderDao.getCaiGouAllTuiKuan(caigouyuan, orderId, time, time1);
    }
    //总采购货款    
    public  List<OrderTable> getCaiGouAllHuoKuan(Long caigouyuan, String orderId, String time, String time1)
    {
        return orderDao.getCaiGouAllHuoKuan(caigouyuan, orderId, time, time1);
    }
    //管理员查看纠纷个数
    public  List<OrderTable> getCaiGouAllJiuFen(Long caigouyuan, String orderId, String time, String time1)
    {
        return orderDao.getCaiGouAllJiuFen(caigouyuan, orderId, time, time1);
    }
    //采购总金额
    public  List<OrderTable> getCaiGouAdminAllMoney(String orderId, String time, String time1)
    {
        LoginInfo us = (LoginInfo)getFromSession("logininfo");
        return orderDao.getCaiGouAdminAllMoney(us.getUserId(), orderId, time, time1);
    }

    public  List<OrderTable> getCaiGouAdminAllYunFei(String orderId, String time, String time1)
    {
        LoginInfo us = (LoginInfo)getFromSession("logininfo");
        return orderDao.getCaiGouAdminAllYunFei(us.getUserId(), orderId, time, time1);
    }
    //采购查看纠纷总金额
    public  List<OrderTable> getJiuFenAllMoney(String orderId, String time, String time1)
    {
        LoginInfo us = (LoginInfo)getFromSession("logininfo");
        return orderTableDao.getJiuFenMoney(us.getUserId(), orderId, time, time1);
    }
    public  List<OrderTable> getCaiGouAdminAllHuoKuan(String orderId, String time, String time1)
    {
        LoginInfo us = (LoginInfo)getFromSession("logininfo");
        return orderDao.getCaiGouAdminAllHuoKuan(us.getUserId(), orderId, time, time1);
    }

    public  List<OrderTable> getCaiGouAdminAllTuiKuan(String orderId, String time, String time1)
    {
        LoginInfo us = (LoginInfo)getFromSession("logininfo");
        return orderDao.getCaiGouAdminAllTuiKuan(us.getUserId(), orderId, time, time1);
    }
    //采购和采购管理员查看纠纷订单 
    public  List<OrderTable> getCaiGouAdminAllJiuFen(String orderId, String time, String time1)
    {
        LoginInfo us = (LoginInfo)getFromSession("logininfo");
        return orderDao.getCaiGouAdminAllJiuFen(us.getUserId(), orderId, time, time1);
    }
  //管理员查看纠纷总额 
    public  List<OrderTable> getCaiGouJiuFenMoney(Long userid, String orderId, String time, String time1)
    {
        return orderDao.getCaiGouJiuFenMoney(userid, orderId, time, time1);
    }
    //管理员查看 退款总金额
    public  List<OrderTable> getCaiGouTuiKuan(Long userid, String orderId, String time, String time1)
    {
        return orderDao.getCaiGouTuiKuan(userid, orderId, time, time1);
    }

    public  List<GuoNeiWangZhan> getGouNeiWangZhan()
    {
        return guoNeiWangZhanDao.getGouNeiWangZhan();
    }

    public String getGuoNeiWangZhan(String wangzhanid)
    {
        GuoNeiWangZhan wangzhan = guoNeiWangZhanDao.get(Long.parseLong(wangzhanid));
        return wangzhan.getName();
    }

    public List<KuaiDiFangShi> getKuaiDiFangShiAll()
    {
        return kuaiDiFangShiDao.getSelKuaiDiFangShi();
    }

    public String getKuaiDiFangShi(String kuaidifangshi)
    {
        KuaiDiFangShi kuaidi = kuaiDiFangShiDao.get(Long.parseLong(kuaidifangshi));
        return kuaidi.getName();
    }

    public String getGuoNeiKuaiDiFangShi(String guoneikuaidi)
    {
        GuoNeiKuaiDi guonei = guoNeiKuaiDiDao.get(Long.parseLong(guoneikuaidi));
        return guonei.getName();
    }
    
    public String getKuaiDi(){
    	String ss = orderTableDao.getSelTwoTble();
    	OrderTable se = null;
    	if(ss != null){
    	
    	}
    	return null;
    }
    public List<GuoNeiKuaiDi> getGuoNeiKuaiDiAll()
    {
        return guoNeiKuaiDiDao.getGuoNeiKuaiDi();
    }
    //查询全部顾客
    public List<GuKeTable> getGuKeAll(){
    	return guKeDao.getGuKeAll();
    }
    //查询全部国家
    public List<YunFeiTable> getAllGuoJia(){
    	return guoJiaDao.getAllGuoJia();
    }
    //编号查询全部国家
    public String getAllYunFei(String id){
    	 YunFeiTable guojia =  guoJiaDao.get(Long.parseLong(id));
    	 return guojia.getGuojia(); 
    }
    //查询全部类目
    public List<LeiMuTable> getAllLeiMu(){
    	return leiMuDao.getAllLeiMu();
    }
    //采购员编号查询类目
    public String getSelLeiMu(Long userid){
    	LeiMuTable stu = leiMuDao.getLeiMuUser(userid);
    	return stu.getLeimu();
    }
    //用userid查询全部
    public GuKeTable getSelGuKe(){
    	
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	GuKeTable stu = guKeDao.getByUserId(us.getUserId());
    	 return stu;
    }
    //查看未入账总额
    public List<OrderTable> getRuZhang(String orderId,String time,String time1,String zhanghao,String danhao,String sumaitong,String bianma,String leimu){
    	return orderDao.getWeiRuZhang(orderId, time, time1, zhanghao, danhao,sumaitong,bianma,leimu);
    }
    //查看未读信息个数
    public List<XieXinTable> getXieXin(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	return xieXinDao.getAllWeiDu(us.getUserId());
    }
    //查询全部收件人
    public List getShouJianRen(Long usertype){
    	
    	List stu = null;
    	if(usertype == 1){
    		 stu = yeWuDao.getYeWuAll();
    	}if(usertype == 2){
    		stu = caiGouDao.getCaiGou();
    	}if(usertype == 3){
    		stu = cangKuYuanDao.getCangKuYuanAll();
    	}if(usertype == 4){
			stu = caiWuDao.getCaiWu();
		}if(usertype == 5){
			stu = guKeDao.getGuKeAll();
		}if(usertype == 6){
			stu = caiGouAdminDao.getCaiGouAdminAll();
		}
    	return stu;
    }
    //用国家编号查询国家
    public String getGuoJiaId(Long id){
    	
    	YunFeiTable ss = guoJiaDao.getGuoJiaAll(id); 
    	return ss.getGuojia();
    }
    //查看未读信
    public List<UserInfo> getWeiDu(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo"); 	
    	return userDao.getWeiDuXin(us.getUserId());
    }
    //剩余运费
     
    //运费为空 
    public List<OrderTable> getYunFeiAll(Long userid,String orderId,String time,String time1){
    	List<OrderTable> stu = orderTableDao.getYunFeiAll(userid, orderId, time, time1);
    	return stu;
    	
    }
    //货款为空 
    public List<OrderTable> getHuoKuanAll(Long userid,String orderId,String time,String time1){
    	List<OrderTable> stu = orderTableDao.getHuoKuanAll(userid, orderId, time, time1);
    	return stu;
    }
    //编号查询全部
    public String getSelName(Long id){
    	GuKeTable ss = guKeDao.get(id);
    	return ss.getName();
    }
    //上传到采购
    public Long getTime(java.util.Date time,java.util.Date caigoutime){
    	
    	java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
		java.util.Date beginDate= time;    

		java.util.Date endDate= caigoutime; 
		Long day=(endDate.getTime()-beginDate.getTime())/(60*60*1000);  
		return day;
    }
   
    //采购到入账
    public Long getRuzhangTime(java.util.Date time,java.util.Date caigoutime){
    	
    	java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
		java.util.Date beginDate= time;    

		java.util.Date endDate= caigoutime; 
		Long day=(endDate.getTime()-beginDate.getTime())/(60*60*1000);  
		return day;
    }
    
    //预存运费
    public Double getYunFeiYuCun(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo"); 	
    	List<YunCun> yy = null;
    	try {
    		yy = yuCunDao.getUserLastNum(us.getUserId());
        	if(yy.size() == 0 || yy.get(0).getMoney() == null){
        		return 0d;
        	}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
    	return yy.get(0).getMoney();
    }
    //全部订单平均采购速度
  public Long getCaiGouTimed(String orderId,String time,String time1,String dhgatezhanghao,String danhao,String sumaitong,String bianma,String leimu){
	
   	Long xs= 0l;
   	 try {
   		List<OrderTable> all = orderTableDao.getAllOrder(orderId, time, time1, dhgatezhanghao,danhao,sumaitong,bianma,leimu); 
    	for (int i = 0; i < all.size(); i++) {	
	    		Date beginDate=all.get(i).getTime();    
	    		Date endDate= all.get(i).getCaigoutime(); 
	    		xs=xs+((endDate.getTime()-beginDate.getTime())/(60*60*1000));
		}
    	if(all.size() !=0){
    		xs = xs/(all.size());
    	}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	 return xs;
  }
    //采购平均采购时间
    public Long getCgTime(String orderId,String time,String time1,String caigoutime,String caigoutime1,String bianma){
    	Long xs= 0l;
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	 try {
    		List<OrderTable> all = orderTableDao.getAllWanChengOrder(us.getUserId(), orderId, time, time1, caigoutime, caigoutime1, bianma); 
     	for (int i = 0; i < all.size(); i++) {	
 	    		Date beginDate=all.get(i).getTime();    
 	    		Date endDate= all.get(i).getCaigoutime(); 
 	    		xs=xs+((endDate.getTime()-beginDate.getTime())/(60*60*1000));
 		}
     	if(all.size() !=0){
     		xs = xs/(all.size());
     	}
 	} catch (Exception e) {
 		// TODO: handle exception
 		e.printStackTrace();
 	}
 		return xs;
    }
    
    //查询全部业务
    public List<YeWu1> getYeWu1All(){
    	return yeWu1Dao.getYeWuAll();
    }
    //查询全部业务
    public List<ClipArt> getAssignedAccount(){
    	return clipArtDao.getAssignedAccount();
    }
    //查看运费表字母
    public List<YunFeiTable> getYunTable(){
    	return guoJiaDao.getAllGuoJia();
    }
    //查看全部订单
    public List<OrderTable> getAllOrders(){
    	List<OrderTable> stu =orderTableDao.getAllOrder(); 
    	
    	 if(stu.size() != 0)
         {
         	  for(int i = 0; i < stu.size(); i++)
               {
	         		 stu.get(i).setId(stu.get(i).getId());
	         		 stu.get(i).setYjsc(1l);
	                 orderTableDao.merge(stu.get(i));
               }

         }
    	return stu;
    }
    //订单号查询编码
    public Order_Detail getOrderIdAll(String orderId){
    	return  orderTableDao.getOrderIdAll(orderId);
    }
    //编号查询全部
    public String getDhgateId(Long dhgateid){
    		DhgateAccounts  dd=orderTableDao.getDhgateId(dhgateid);
         return dd.getAccount();
    }
    //国家查询
    public String getCorresponding(String country){
    
    	YunFeiTable tt = yunFeiTableDao.getCorresponding(country);
    
    	return tt.getGuojia();
    }
    //查询全部类目
    public String getAllCgs(String cgs){
    	List<LeiMuTable> ll = null;
    	try {
        	 ll = leiMuDao.getAllName(cgs);
        System.out.println("6666666666"+ll.size());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
    	if(ll.size() != 0){
    	
    		return ll.get(0).getLeimu();
    	}else{
    	
    		putInSession("weikong", "11");
    		return null;
    	}
    }

    public void setServletRequest(HttpServletRequest arg0)
    {
        request = arg0;
    }
    //编码查询
    public String getCoding(String coding){
    	System.out.println("++++++"+coding);
    	OrderTable cod = orderTableDao.getCoding(coding);
    	if (cod != null) {
    		return cod.getBianma();
		}else{
			return null;
		}
    	
    }
    // 2014-2-25新增
	public String getFormatTime(Long timestamp) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp));
	}
}
