package com.demo.action;

import com.demo.dao.OrderDao;
import com.demo.dao.OrderTableDao;
import com.demo.dao.user.GuKeDao;
import com.demo.dao.user.UserDao;
import com.demo.entity.order.OrderTable;
import com.demo.entity.user.GuKeTable;
import com.demo.entity.user.UserInfo;
import com.demo.page.PageBean;
import com.demo.page.PageBiz;
import com.demo.vo.LoginInfo;
import com.opensymphony.xwork2.ActionContext;
import java.io.PrintStream;
import java.sql.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
@Controller("guKeAction")
@Scope("prototype")
public class GuKeAction extends BaseAction implements ServletRequestAware
{

    private static final long serialVersionUID = 1L;
    private HttpServletRequest request;
    @Resource
    private OrderDao orderDao;
    @Resource
    private GuKeDao guKeDao;
    @Resource
    private UserDao userDao;
    @Resource
    private OrderTableDao orderTableDao;
    @Resource
    private PageBiz pageBiz;
    public String chuli;
    public String danhao;
    private OrderTable orderTable;
    private GuKeTable guKeTable;
    private UserInfo user;
    public int pageNumber;
    public String guoneidanhao;
    private PageBean pageBean;
    public int pageindex;
    public String orderId;
    public String msg;
    
    public GuKeTable getGuKeTable() {
		return guKeTable;
	}

	public void setGuKeTable(GuKeTable guKeTable) {
		this.guKeTable = guKeTable;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public OrderTable getOrderTable() {
		return orderTable;
	}

	public void setOrderTable(OrderTable orderTable) {
		this.orderTable = orderTable;
	}

	public PageBean getPageBean()
    {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean)
    {
        this.pageBean = pageBean;
    }

    public String addorder()
    {
        return "addorder";
    }
    //顾客上传订单
    public String addAllOrder()
    {
        String[] name = request.getParameterValues("name");
        String[] dingdan = request.getParameterValues("orderId");
        String[] guoneikuaidi = request.getParameterValues("guoneikuaidi");
        String[] guoneidanhao = request.getParameterValues("guoneidanhao");
        String[] wuping = request.getParameterValues("wuping");
        String[] guojiaId=request.getParameterValues("guojiaId");
        String[] guowaidizhi = request.getParameterValues("guowaidizhi");
        String[] kuaidifangshiId = request.getParameterValues("kuaidifangshiId");
        String[] remark = request.getParameterValues("remark");
        String[] str = new String[dingdan.length];
        for(int i = 0; i < dingdan.length; i++)
        {
            List<OrderTable> ls = orderDao.getAllOrderId(dingdan[i]);
            if("".equals(dingdan[i]) || dingdan[i] == null)
                str[i] = i+".订单号不能为空!";
            else
            if(ls.size() != 0)
                str[i] = i+".订单号已经存在";
            else
            if(guoneidanhao[i] == null || "".equals(guoneidanhao[i]))
                str[i] = i+".账号未选择";
            else
            if("".equals(guoneikuaidi[i]) || guoneikuaidi[i] == null)
            {
                str[i] = i + 1+".快递未选择";
            }
            else
                if("".equals(guojiaId[i]) || guojiaId[i] == null)
                {
                    str[i] = i + 1+".国家未选择";
                }
            else
            {
                OrderTable order = new OrderTable();
                order.setTime(new Date(System.currentTimeMillis()));
                order.setCaigouyuan(0L);
                order.setName(name[i]);
                order.setOrderId(dingdan[i]);
                order.setGuoneikuaidiId(Long.parseLong(guoneikuaidi[i]));
                order.setGuoneidanhao(guoneidanhao[i]);
                order.setWuping(wuping[i]);
                order.setGuojiaId(Long.parseLong(guojiaId[i]));
                order.setGuowaidizhi(guowaidizhi[i]);
                order.setKuaidifangshiId(Long.parseLong(kuaidifangshiId[i]));
                order.setZhanghaoId(15l);
                order.setGuoneiwangzhanId(6l);
                order.setRemark(remark[i]);
                LoginInfo login = (LoginInfo)getFromSession("logininfo");
                order.setDengluId(login.getUserId());
                order.setFenpei(3l);
                order.setDaifahuo(1l);
                orderDao.merge(order);
                str[i] = i+".操作成功";
            }
        }

        ActionContext.getContext().put("insert", str);
        return "addorder";
    }
    //顾客待发货
    public String getDaiFaHuo()
    {
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
        int pageSize = 10;
        pageBean = pageBiz.selGuKeDaiFaHuo(pageSize, pageNumber,us.getUserId(),orderId);
        return "daifahuo";
    }
    //顾客已经发货
    public String getYiJingFaHuo()
    {
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
        int pageSize = 10;
        pageBean = pageBiz.selYiJingRuDan(pageSize, pageNumber, orderId,us.getUserId());
        return "yijingfahuo";
    }
    //修改订单
    public String updaifahuo(){
    	return "updaifahuo";
    }
    //编号查询全部
    public OrderTable getUpdateId()
    {
        OrderTable stu = (OrderTable)orderDao.get(orderTable.getId());
        return stu;
    }
    //顾客修改待发货
    public String updatedaifahuo(){
   
    	try {
    		String orderid = orderTable.getOrderId();
        	Long guoneikuaidi = orderTable.getGuoneikuaidiId();
        	String wuping = orderTable.getWuping();
        	String guoneidanhao = orderTable.getGuoneidanhao();
        	String dizhi = orderTable.getGuowaidizhi();
        	String remark = orderTable.getRemark();
        	Long guojia = orderTable.getGuojiaId();
        	orderTable = orderTableDao.get(orderTable.getId());
        	orderTable.setOrderId(orderid);
        	orderTable.setGuoneikuaidiId(guoneikuaidi);
        	orderTable.setWuping(wuping);
        	orderTable.setGuoneidanhao(guoneidanhao);
        	orderTable.setGuowaidizhi(dizhi);
        	orderTable.setRemark(remark);
        	orderTable.setGuojiaId(guojia);
        	orderTableDao.merge(orderTable);
        	msg = "修改成功";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
    	return getChuHuo();
    }
    //返回修改已经发货页面
    public String upyijingfahuo(){
    	return "upyijingfahuo";
    }
    //修改已经发货
    public String updateyijingfahuo(){
    	String orderid = orderTable.getOrderId();
    	Long guoneikuaidi = orderTable.getGuoneikuaidiId();
    	String wuping = orderTable.getWuping();
    	String guoneidanhao = orderTable.getGuoneidanhao();
    	String dizhi = orderTable.getGuowaidizhi();
    	String remark = orderTable.getRemark();
    	orderTable = orderTableDao.get(orderTable.getId());
    	orderTable.setOrderId(orderid);
    	orderTable.setGuoneikuaidiId(guoneikuaidi);
    	orderTable.setWuping(wuping);
    	orderTable.setGuoneidanhao(guoneidanhao);
    	orderTable.setGuowaidizhi(dizhi);
    	orderTable.setRemark(remark);
    	orderTableDao.merge(orderTable);
    	msg = "修改成功";
    	return getYiJingFaHuo();
    }
    //删除待发货产品
    public String deleteorder(){
    	try {
    		orderDao.delete(orderTable);
        	msg = "删除成功";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
    	return getDaiFaHuo();
    }
    //客户返回给仓库
    public String cangkudaifangqu(){
    	String[] id = request.getParameter("bulletinId").split("-");
        for(int i = 0; i < id.length; i++)
        {
            List<OrderTable> ls = orderDao.getSelId(Long.parseLong(id[i]));
            if(ls.size() != 0)
            {
                ls.get(0).setId(Long.parseLong(id[i]));
                ls.get(0).setDaochu(1l);
                orderDao.merge(ls.get(0));
            }
        }

        msg = "操作成功";
        return getYiJingFaHuo();
    }
    //查看出货订单 
    public String getChuHuo(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
        int pageSize = 10;
        pageBean = pageBiz.selChuHuo(pageSize, pageNumber, us.getUserId(), orderId, guoneidanhao);
    	return "chuhuo";
    }
    //顾客修改个人资料
    public String upZiLiao(){
    	return "UpdateZiLiao";
    }
    //顾客修改个人资料
    public String updateZiLiao(){

    	String username = user.getUsername();
    	String pwd = user.getPwd();
    	String name = guKeTable.getName();
    	String gukeQQ = guKeTable.getGukeQQ();
    	String phone = guKeTable.getPhone();
    	guKeTable = guKeDao.get(guKeTable.getId());
    	user = userDao.get(user.getId());
    	user.setUsername(username);
    	user.setPwd(pwd);
    	guKeTable.setGukeQQ(gukeQQ);
    	guKeTable.setPhone(phone);
    	guKeTable.setName(name);
    	guKeDao.merge(guKeTable);
		userDao.merge(user);
		msg = "修改成功";
    	return "UpdateZiLiao";
    }
    //顾客查看退货
    public String getTuiHuoAll(){
    	
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.selTuiHuo(pageSize, pageNumber, us.getUserId(), orderId,danhao,chuli);
    	return "tuihuo";
    }
    //顾客查看运费详情
    public String getXqYunFei(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    
    	GuKeTable gg = guKeDao.getByUserId(us.getUserId());
    	int pageSize = 10;
    	pageBean = pageBiz.selXqYuCun(pageSize, pageNumber, gg.getId());
    	return "getxqYuCun";
    }
    //在线咨询
    public String zxzx(){
    	return "zxzx";
    }
    public void setServletRequest(HttpServletRequest arg0)
    {
        request = arg0;
    }

}
