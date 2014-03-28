package com.demo.action.tools;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.demo.action.BaseAction;
import com.demo.dao.tools.GatherData1ResultDao;
import com.demo.entity.tools.GatherData1Result;
import com.demo.page.PageBean;

/**
 * 采集产品链接列表
 *
 */
@Controller("tools.gatherData1ResultAction")
@Scope("prototype")
public class GatherData1ResultAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	@Resource
	private GatherData1ResultDao gatherData1ResultDao;
	
	private Long keyCreateTime;
	private Integer pageSize = 20;
	private Integer pageNum = 1;
	public List<GatherData1Result> gatherdata1result; 
	private PageBean pageBean;
	public String exporttime;//导出时间
	public String exporttime1;//导出时间
	public String execute() {
		pageBean = gatherData1ResultDao.getAllByPage(keyCreateTime, pageSize, pageNum,exporttime,exporttime1);
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
