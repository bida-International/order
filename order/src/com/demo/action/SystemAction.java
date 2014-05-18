package com.demo.action;

import com.demo.biz.SystemBiz;
import com.demo.dao.*;
import com.demo.dao.user.BusinessDao;
import com.demo.dao.user.CaiGouAdminDao;
import com.demo.dao.user.CaiGouDao;
import com.demo.dao.user.CaiWuDao;
import com.demo.dao.user.CangKuYuanDao;
import com.demo.dao.user.ClipArtDao;
import com.demo.dao.user.GuKeDao;
import com.demo.dao.user.PuchasingAssistantDao;
import com.demo.dao.user.TechnicianDao;
import com.demo.dao.user.UserDao;
import com.demo.dao.user.YeWu1Dao;
import com.demo.dao.user.YeWuDao;
import com.demo.entity.*;
import com.demo.entity.user.BusinessTable;
import com.demo.entity.user.CaiGou;
import com.demo.entity.user.CaiGouAdmin;
import com.demo.entity.user.CaiWuTable;
import com.demo.entity.user.CangKuYuan;
import com.demo.entity.user.ClipArt;
import com.demo.entity.user.GuKeTable;
import com.demo.entity.user.Technician;
import com.demo.entity.user.UserInfo;
import com.demo.entity.user.YeWu;
import com.demo.entity.user.YeWu1;
import com.demo.entity.user.PurchasingAssistant;
import com.demo.utils.AppException;
import com.demo.vo.LoginInfo;
import com.opensymphony.xwork2.ActionContext;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
@Controller("systemAction")
@Scope("prototype")
public class SystemAction extends BaseAction  implements ServletRequestAware
{
    private static final long serialVersionUID = 1L;
    @Resource
    private SystemBiz systemBiz;
    @Resource
    private YeWuDao yeWuDao;
    @Resource
    private YeWu1Dao yeWu1Dao;
    @Resource
    private CaiGouDao caiGouDao;
    @Resource
    private CaiGouAdminDao caiGouAdminDao;
    @Resource
    private CangKuYuanDao cangKuYuanDao;
    @Resource
    private GuKeDao guKeDao;
 	@Resource
    private UserDao userDao;
 	@Resource
    private CaiWuDao caiWuDao;
	@Resource
    private ClipArtDao clipArtDao;
	@Resource
    private TechnicianDao technicianDao;
	@Resource
    private PuchasingAssistantDao puchasingassistantdao;
	@Resource
    private BusinessDao businessDao;
    public String username;
    public String pwd;
    public String msg;
    public String phone;
    public String QQ;
    public String name;
    private UserInfo user;

    private HttpServletRequest request;
    public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public String login()
    {
    	try {
			String ip = ServletActionContext.getRequest().getRemoteAddr();
	        UserInfo user = systemBiz.checkUserLogin(username, pwd, ip);
	        LoginInfo info = new LoginInfo();
	        info.setAccount(user.getUsername());
	        info.setPwd(user.getPwd());
	        info.setIp(ip);
	        info.setLoginTime(new Timestamp(System.currentTimeMillis()));
	        info.setName(user.getName());
	        info.setUserId(user.getId());
	        CaiGou t = caiGouDao.getByUserId(info.getUserId());
	        YeWu s = yeWuDao.getByUserId(info.getUserId());
	        CaiGouAdmin ss = caiGouAdminDao.getByUserId(info.getUserId());
	        CangKuYuan cc = cangKuYuanDao.getByUserId(info.getUserId());
	        GuKeTable gg = guKeDao.getByUserId(info.getUserId());
	        CaiWuTable tt = caiWuDao.getByUserId(info.getUserId());
	        YeWu1 yy = yeWu1Dao.getByUserId(info.getUserId());
	        ClipArt hh = clipArtDao.getByUserId(info.getUserId());
	        Technician js = technicianDao.getByUserId(info.getUserId());
	        BusinessTable bu = businessDao.getByUserId(info.getUserId());
	        com.demo.entity.user.PurchasingAssistant pp =  puchasingassistantdao.getByUserId(info.getUserId());
	        if(Boolean.TRUE.equals(user.getAdmin())){
	            info.setRole(Integer.valueOf(1));
	             
	        }
	        else
	        if(t != null)
	        {
	            info.setRole(Integer.valueOf(3));
	            info.setCaiGouId(t.getId());
	        } else
	        if(s != null)
	        {
	            info.setRole(Integer.valueOf(2));
	            info.setYeWuId(s.getId());
	        } else
	        if(ss != null)
	        {
	            info.setRole(Integer.valueOf(4));
	            info.setCaiGouAdminId(ss.getId());
	        } else
	        if(cc != null)
	        {
	            info.setRole(Integer.valueOf(5));
	            info.setCangKuYuanId(cc.getId());
	        } else
	        if(gg != null)
	        {
	            info.setRole(Integer.valueOf(6));
	            info.setGukeId(gg.getId());
	        }
	        else if(tt != null){
	        	info.setRole(7);
	        	info.setCaiwuId(tt.getId());
	        }
	        else if(yy != null){
	        	info.setRole(8);
	        	info.setYewu1Id(yy.getId());
	        }
	        else if(hh != null){
	        	info.setRole(9);
	        	info.setClipArtId(hh.getId());
	        }
	        else if(js != null){
	        	info.setRole(10);
	        	info.setTechnicianId(js.getId());
	        }
	        else if(pp != null){
	        	info.setRole(11);
	        	info.setTechnicianId(pp.getId());
	        }
	        else if(bu != null){
	        	info.setRole(12);
	        	info.setTechnicianId(bu.getId());
	        }
	        else
	        {
	            throw new AppException("账号未创建");
	        }
	        putInSession("role", info.getRole());
	        putInSession("logininfo", info);
	        if(user != null){
	            return "main";
	        }
	        else{
	            return "login";
	        }
		} catch (Exception e) {
			// TODO: handle exception
	        msg = e.getMessage();
	        log.debug("异常 ", e);
	        return "login";
		}        

    }

    public String sessions()
    {
        try
        {
            ActionContext.getContext().getSession().clear();
            ServletActionContext.getRequest().getSession().invalidate();
        }
        catch(Exception e)
        {
            e.fillInStackTrace();
        }
        return "login";
    }
    //用户注册
    public String registered(){
    	return "registered";
    }
    //用户注册
    public String zuche(){
    	
    	 UserInfo us = userDao.getByAccount(user.getUsername());
         if(us != null){
             msg = "已经存在";
         }else{
	    	 user.setAdmin(Boolean.valueOf(false));
	         user.setName(user.getUsername());
	         java.util.Date date = new java.util.Date();
	         SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	         user.setTime(sim.format(date));
	         user.setQuanxian(Long.valueOf(3L));
	         user = (UserInfo)userDao.merge(user);
	         GuKeTable ss = new GuKeTable();
	         ss.setName(name);
	         ss.setPhone(phone);
	         ss.setGukeQQ(QQ);
	         ss.setUserid(user.getId());
	         guKeDao.merge(ss);
	         msg = "操作成功";
         }
         return "registered";
    }
    public void setServletRequest(HttpServletRequest arg0)
    {
        request = arg0;
    }

   
}
