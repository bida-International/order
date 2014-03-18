package com.demo.action.order;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.demo.action.BaseAction;
import com.demo.biz.aliexpress.AliCommonApiBiz;
import com.demo.biz.aliexpress.AliOrderApiBiz;
import com.demo.entity.ZhangHao;
import com.demo.utils.DateUtils;
import com.demo.utils.Struts2Utils;
import com.demo.vo.LoginInfo;

/**
 * 速卖通订单数据同步
 *
 */
@Controller("order.aliApiAction")
@Scope("prototype")
public class AliApiAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private AliCommonApiBiz aliCommonApiBiz;
	@Resource
	private AliOrderApiBiz aliOrderApiBiz;
	
	private List<ZhangHao> accounts;
	
	private String account;
	private Integer beforeDay;
	
	public String execute() {
		Long queryUserId = null;
		if ((Integer) this.getFromSession("role") != 1) { // 非管理员用户
			queryUserId = ((LoginInfo) this.getFromSession("logininfo")).getUserId();
		}
		accounts = aliCommonApiBiz.getAliAccounts(queryUserId);
		for (ZhangHao zhangHao : accounts) {
			if (zhangHao.getApp_key() == null || zhangHao.getApp_secret() == null) {
				accounts.remove(zhangHao);
			}
		}
		return SUCCESS;
	}
	
	public String update() {
		Date endDate = new Date();
		Date startDate = DateUtils.getAfterDaysDate(endDate, -beforeDay);
		String result = aliOrderApiBiz.fetchOrders(account, startDate, endDate);
    	boolean success = true;
    	if (result.contains("发生错误")) {
    		success = false;
    	}
    	
    	Struts2Utils.renderJson(result, success);
    	return null;
	}
	
	public List<ZhangHao> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<ZhangHao> accounts) {
		this.accounts = accounts;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getBeforeDay() {
		return beforeDay;
	}

	public void setBeforeDay(Integer beforeDay) {
		this.beforeDay = beforeDay;
	}
}
