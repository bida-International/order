package com.demo.action;

import com.demo.dao.GuoJiaDao;
import com.demo.dao.HuiLvDao;
import com.demo.dao.IncomeDao;
import com.demo.dao.LeiMuDao;
import com.demo.dao.OrderDao;
import com.demo.dao.OrderTableDao;
import com.demo.dao.SpendingDao;
import com.demo.dao.XieXinDao;
import com.demo.dao.ZhangHaoDao;
import com.demo.dao.Courier.DhlZkDao;
import com.demo.dao.Courier.DhlfqDao;
import com.demo.dao.Courier.DhlgbjDao;
import com.demo.dao.Courier.EmsZfDao;
import com.demo.dao.Courier.FedExIeDao;
import com.demo.dao.Courier.FedExIeGbjDao;
import com.demo.dao.Courier.FedExIpDao;
import com.demo.dao.Courier.FedExIpGbjDao;
import com.demo.dao.Courier.FedexDao;
import com.demo.dao.Courier.GjslDao;
import com.demo.dao.Courier.HkdbDao;
import com.demo.dao.Courier.HydbDao;
import com.demo.dao.Courier.KyfqDao;
import com.demo.dao.Courier.KyfqGbjDao;
import com.demo.dao.Courier.SALdbDao;
import com.demo.dao.user.UserDao;
import com.demo.entity.HuiLvTable;
import com.demo.entity.IncomeTable;
import com.demo.entity.LeiMuTable;
import com.demo.entity.SpendingTable;
import com.demo.entity.XieXinTable;
import com.demo.entity.ZhangHao;
import com.demo.entity.Courier.DhlFq;
import com.demo.entity.Courier.DhlGbj;
import com.demo.entity.Courier.Dhlzk;
import com.demo.entity.Courier.EmsZf;
import com.demo.entity.Courier.FedExIe;
import com.demo.entity.Courier.FedExIeGbj;
import com.demo.entity.Courier.FedExIp;
import com.demo.entity.Courier.FedExIpgbj;
import com.demo.entity.Courier.Fedex;
import com.demo.entity.Courier.Gjsl;
import com.demo.entity.Courier.Hkdb;
import com.demo.entity.Courier.Hydb;
import com.demo.entity.Courier.Kygbj;
import com.demo.entity.Courier.SALdb;
import com.demo.entity.Courier.YunFeiTable;
import com.demo.entity.Courier.kyfq;
import com.demo.entity.order.OrderTable;
import com.demo.entity.user.GuKeTable;
import com.demo.entity.user.UserInfo;
import com.demo.list.PageModel;
import com.demo.page.PageBean;
import com.demo.page.PageBiz;
import com.demo.vo.LoginInfo;
import com.opensymphony.xwork2.ActionContext;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.soap.Text;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.aspectj.weaver.ast.Test;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("theAccountAction")
@Scope("prototype")
public class TheAccountAction extends BaseAction implements ServletRequestAware
{
    private static final long serialVersionUID = 1L;
	@Resource
	private SpendingDao spendingdao;
	@Resource
	private IncomeDao incomedao;
	@Resource
	private PageBiz pageBiz;
    private PageBean pageBean;
	private SpendingTable spendingtable;//支出
	private IncomeTable incometable;//收入
	public String msg;
	public int pageNumber;
	public String time;
	public String time1;
    private HttpServletRequest request;
    public PageBean getPageBean() {
		return pageBean;
	}
	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
	public SpendingTable getSpendingtable() {
		return spendingtable;
	}
	public void setSpendingtable(SpendingTable spendingtable) {
		this.spendingtable = spendingtable;
	}
	public IncomeTable getIncometable() {
		return incometable;
	}
	public void setIncometable(IncomeTable incometable) {
		this.incometable = incometable;
	}
	//内帐
    public String inTheAccount(){
    	return "theaccount";
    }
    //添加收入
    public String insert(){
    	try {
		  	java.util.Date ds = new java.util.Date();
	        SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String ffs = fs.format(ds);
	        incometable.setTime(ffs);
	        msg = "操作成功";
	        incomedao.merge(incometable);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	return "theaccount";
    }
  //添加支出
    public String save(){
    	try {
		  	java.util.Date ds = new java.util.Date();
	        SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String ffs = fs.format(ds);
	        spendingtable.setTime(ffs);
	        msg = "操作成功";
	        spendingdao.merge(spendingtable);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	return "theaccount";
    }
    //查看内帐
    public String getCheckAccountWithin(){
    	try {
    	   	int pageSize = 10;
        	pageBean = pageBiz.getSpending(pageSize, pageNumber, time, time1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	return "spendings";
    }
    //查看收入
    public String income(){
    	int pageSize = 10;
    	pageBean = pageBiz.getInCome(pageSize, pageNumber, time, time1);
    	return "income";
    }
    //修改支出
    public String updatespend(){
    	return "updatespendings";
    }
    //编号查询支出
    public SpendingTable getSpending(){
    	SpendingTable stu =spendingdao.get(spendingtable.getId());
    	return stu;
    }
    //修改支出
    public String updateSpendings(){
    	String paymentproject = spendingtable.getPaymentproject();
    	Double money = spendingtable.getMoney();
    	String time = spendingtable.getTime();
    	String remark = spendingtable.getRemark();
    	spendingtable = spendingdao.get(spendingtable.getId());
    	spendingtable.setPaymentproject(paymentproject);
    	spendingtable.setMoney(money);
    	spendingtable.setTime(time);
    	spendingtable.setRemark(remark);
    	msg= "操作成功";
    	spendingdao.merge(spendingtable);
    	return getCheckAccountWithin();
    }
    //修改收入
    public String upincome(){
    	return "updateincome";
    }
    //查询收入
    public IncomeTable getInComeTable(){
    	IncomeTable ss = incomedao.get(incometable.getId());
    	return ss;
    }
    //修改收入
    public String updateInCome(){
    	String withdrawaldate = incometable.getWithdrawaldate();
    	Double money = incometable.getMoney();
    	Double huilv = incometable.getHuilv();
    	String remark = incometable.getRemark();
    	incometable = incomedao.get(incometable.getId());
    	incometable.setWithdrawaldate(withdrawaldate);
    	incometable.setMoney(money);
    	incometable.setHuilv(huilv);
    	incometable.setRemark(remark);
    	msg= "操作成功";
    	incomedao.merge(incometable);
    	return  income();
    }
    public void setServletRequest(HttpServletRequest arg0)
    {
        request = arg0;
    }


}