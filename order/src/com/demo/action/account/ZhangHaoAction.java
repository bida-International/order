package com.demo.action.account;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.demo.action.BaseAction;
import com.demo.biz.aliexpress.AliCommonApiBiz;
import com.demo.biz.aliexpress.AliOrderApiBiz;
import com.demo.biz.dhgate.DhCommonApiBiz;
import com.demo.biz.dhgate.DhMsgApiBiz;
import com.demo.biz.dhgate.DhOrderApiBiz;
import com.demo.dao.ZhangHaoDao;
import com.demo.entity.ZhangHao;
import com.demo.page.PageBean;
import com.demo.utils.HttpClientUtils;
import com.demo.utils.Struts2Utils;

/**
 * 卖家账号管理
 *
 */
@Controller("account.zhangHaoAction")
@Scope("prototype")
public class ZhangHaoAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private ZhangHaoDao zhangHaoDao;
	@Resource
	private DhOrderApiBiz dhOrderApiBiz;
	@Resource
	private DhMsgApiBiz dhMsgApiBiz;
	@Resource
	private AliOrderApiBiz aliOrderApiBiz;

	private PageBean pageBean;
	
	private String accountType = "all";
	private Integer pageSize = 12;
	private Integer pageNumber = 1;

	private Long id;
	private String account;
	private String appKey;
	private String appSecret;
	
	private ZhangHao editZhangHao;
	
	public String execute() {
		return list();
	}
	
	public String list() {
		String queryAccountType = null;
		if (!accountType.equals("all")) {
			queryAccountType = accountType;
		}
		
		setPageBean(zhangHaoDao.getAllByPage(pageSize, pageNumber, queryAccountType));
		return "list";
	}
	
	public String edit() {
		if (id != null) {
			editZhangHao = zhangHaoDao.get(id);
		}
		return "edit";
	}
	
	public String save() {
		ZhangHao zhangHao = null;
		ZhangHao checkZhangHao = zhangHaoDao.findUnique(account, accountType);
		if (id != null) {
			zhangHao = zhangHaoDao.get(id);
			if (checkZhangHao!= null && !checkZhangHao.getId().equals(id)) {
				Struts2Utils.renderJson("发生错误：该账号已经存在！", false);
				return null;
			}
		} else {
			zhangHao = new ZhangHao();
			if (checkZhangHao != null) {
				Struts2Utils.renderJson("发生错误：该账号已经存在！", false);
				return null;
			}
		}

		zhangHao.setAccount(account);
		zhangHao.setAccount_type(accountType);
		zhangHao.setName(account);
		zhangHao.setApp_key(appKey);
		zhangHao.setApp_secret(appSecret);
		zhangHaoDao.merge(zhangHao);
		Struts2Utils.renderJson("操作成功！", true);
		return null;
	}
	
	public String delete() {
		ZhangHao zhangHao = zhangHaoDao.get(id);
		if (zhangHao != null) {
			zhangHaoDao.delete(zhangHao);
		}
		Struts2Utils.renderJson("操作成功", true);
		return null;
	}

	// 同步一次账号的订单数据
	public String synchOrder() {
		ZhangHao zhangHao = zhangHaoDao.get(id);
		String result = null;
		if (zhangHao != null) {
			if (zhangHao.getAccount_type().equals(DhCommonApiBiz.ACCOUNT_TYPE)) { // 敦煌账号
				result = dhOrderApiBiz.autoFetchOrders(zhangHao);
			} else if (zhangHao.getAccount_type().equals(AliCommonApiBiz.ACCOUNT_TYPE)) { // 速卖通账号
				result = aliOrderApiBiz.autoFetchOrders(zhangHao);
			}
		} else {
			result = "发生错误：账号不存在";
		}
		Struts2Utils.renderJson(result, true);
		return null;
	}

	// 同步一次账号的站内信数据
	public String synchMsg() {
		ZhangHao zhangHao = zhangHaoDao.get(id);
		String result = null;
		if (zhangHao != null) {
			if (zhangHao.getAccount_type().equals(DhCommonApiBiz.ACCOUNT_TYPE)) { // 敦煌账号
				result = dhMsgApiBiz.autoFetchMsg(zhangHao);
			} else {
				result = "发生错误：该账号不是敦煌账号";
			}
		} else {
			result = "发生错误：账号不存在";
		}
		Struts2Utils.renderJson(result, true);
		return null;
	}
	
	public String showApiReqCount() {
		String result = "统计日期：" + HttpClientUtils.lastReqCountUpdateDate + "\\r\\n"
				+ "敦煌API请求次数：" + HttpClientUtils.dhApiReqCount  + "\\r\\n"
				+ "速卖通API请求次数：" + HttpClientUtils.aliApiReqCount;
		
		Struts2Utils.renderJson(result, true);
		return null;
	}
	
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public ZhangHao getEditZhangHao() {
		return editZhangHao;
	}

	public void setEditZhangHao(ZhangHao editZhangHao) {
		this.editZhangHao = editZhangHao;
	}
}
