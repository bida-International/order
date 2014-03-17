//package com.demo.action.msg;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Controller;
//
//import com.demo.action.BaseAction;
//import com.demo.biz.dhgate.DhCommonApiBiz;
//import com.demo.biz.msg.DhMsgBiz;
//import com.demo.entity.ZhangHao;
//import com.demo.page.PageBean;
//import com.demo.vo.LoginInfo;
//
///**
// * 查询站内信页面
// *
// */
//@Controller("msg.dhgateMsgAction")
//@Scope("prototype")
//public class DhgateMsgAction extends BaseAction {
//
//	private static final long serialVersionUID = 1L;
//
//	@Resource
//	private DhCommonApiBiz dhCommonApiBiz;
//	@Resource
//	private DhMsgBiz dhMsgBiz;
//	
//	private PageBean pageBean;
//	private List<ZhangHao> dhAccounts;
//	
//	private Integer pageSize = 12;
//	private Integer pageNumber = 1;
//	private String dhAccount = "0";
//	private Integer msgType = 0;
//	private Integer readStatus = -1;
//	
//	public String execute() {
//		Long queryUserId = null;
//		if ((Integer) this.getFromSession("role") != 1) { // 非管理员用户
//			queryUserId = ((LoginInfo) this.getFromSession("logininfo")).getUserId();
//		}
//		dhAccounts = dhCommonApiBiz.getDhAccounts(queryUserId);
//		
//		String queryDhAccount = null;
//		Integer queryMsgType = null;
//		Integer queryReadStatus = null;
//		if (!dhAccount.equals("0"))
//			queryDhAccount = dhAccount;
//		if (msgType != 0) {
//			queryMsgType = msgType;
//		}
//		if (readStatus != -1) {
//			queryReadStatus = readStatus;
//		}
//		
//		pageBean = dhMsgBiz.getAllByPage(pageSize, pageNumber, queryUserId,
//				queryDhAccount, queryMsgType, queryReadStatus);
//		return SUCCESS;
//	}
//	
//	public PageBean getPageBean() {
//		return pageBean;
//	}
//
//	public void setPageBean(PageBean pageBean) {
//		this.pageBean = pageBean;
//	}
//
//	public Integer getPageSize() {
//		return pageSize;
//	}
//
//	public void setPageSize(Integer pageSize) {
//		this.pageSize = pageSize;
//	}
//
//	public Integer getPageNumber() {
//		return pageNumber;
//	}
//
//	public void setPageNumber(Integer pageNumber) {
//		this.pageNumber = pageNumber;
//	}
//
//	public List<ZhangHao> getDhAccounts() {
//		return dhAccounts;
//	}
//
//	public void setDhAccounts(List<ZhangHao> dhAccounts) {
//		this.dhAccounts = dhAccounts;
//	}
//
//	public String getDhAccount() {
//		return dhAccount;
//	}
//
//	public void setDhAccount(String dhAccount) {
//		this.dhAccount = dhAccount;
//	}
//
//	public Integer getMsgType() {
//		return msgType;
//	}
//
//	public void setMsgType(Integer msgType) {
//		this.msgType = msgType;
//	}
//
//	public Integer getReadStatus() {
//		return readStatus;
//	}
//
//	public void setReadStatus(Integer readStatus) {
//		this.readStatus = readStatus;
//	}
//	
//	
//}
