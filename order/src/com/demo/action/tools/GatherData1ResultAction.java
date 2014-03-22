package com.demo.action.tools;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.demo.action.BaseAction;
import com.demo.dao.tools.GatherData1ResultDao;
import com.demo.page.PageBean;

/**
 * 采集产品链接列表
 *
 */
@Controller("tools.gatherData1ResultAction")
@Scope("prototype")
public class GatherData1ResultAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	@Resource
	private GatherData1ResultDao gatherData1ResultDao;
	
	private Long keyCreateTime;
	private Integer pageSize = 20;
	private Integer pageNum = 1;
	
	private PageBean pageBean;
	
	public String execute() {
		pageBean = gatherData1ResultDao.getAllByPage(keyCreateTime, pageSize, pageNum);
		return SUCCESS;
	}

	public Long getKeyCreateTime() {
		return keyCreateTime;
	}

	public void setKeyCreateTime(Long keyCreateTime) {
		this.keyCreateTime = keyCreateTime;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
}
