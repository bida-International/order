package com.demo.action.tools;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.demo.action.BaseAction;
import com.demo.dao.tools.GatherData1KeyDao;
import com.demo.dao.tools.GatherData1ResultDao;
import com.demo.entity.tools.GatherData1Key;
import com.demo.page.PageBean;

/**
 * ²É¼¯¼ÇÂ¼
 *
 */
@Controller("tools.gatherData1KeyAction")
@Scope("prototype")
public class GatherData1KeyAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private GatherData1KeyDao gatherData1KeyDao;
	@Resource
	private GatherData1ResultDao gatherData1ResultDao;
	
	private Integer pageSize = 20;
	private Integer pageNum = 1;
	private Long keyCreateTime;
	
	private PageBean pageBean;
	
	public String execute() {
		pageBean = gatherData1KeyDao.getAllByPage(pageSize, pageNum);
		return SUCCESS;
	}
	
	public String delete() {
		gatherData1ResultDao.batchDelete(keyCreateTime);
		GatherData1Key gatherKey = gatherData1KeyDao.get(id);
		gatherData1KeyDao.delete(gatherKey);
		return execute();
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

	public Long getKeyCreateTime() {
		return keyCreateTime;
	}

	public void setKeyCreateTime(Long keyCreateTime) {
		this.keyCreateTime = keyCreateTime;
	}
	
	
}
