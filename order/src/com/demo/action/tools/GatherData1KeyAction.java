package com.demo.action.tools;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.demo.action.BaseAction;
import com.demo.dao.tools.GatherData1KeyDao;
import com.demo.dao.tools.GatherData1ResultDao;
import com.demo.entity.tools.GatherData1Key;
import com.demo.entity.tools.GatherData1Result;
import com.demo.page.PageBean;

/**
 * 采集记录
 *
 */
@Controller("tools.gatherData1KeyAction")
@Scope("prototype")
public class GatherData1KeyAction extends BaseAction  implements ServletRequestAware{

	private static final long serialVersionUID = 1L;

	@Resource
	private GatherData1KeyDao gatherData1KeyDao;
	@Resource
	private GatherData1ResultDao gatherData1ResultDao;
	
	private Integer pageSize = 20;
	private Integer pageNum = 1;
	private Long keyCreateTime;
	public List<GatherData1Result> gatherdata1result; 
	private PageBean pageBean;
	private HttpServletRequest request;
	public void setServletRequest(HttpServletRequest arg0)
    {
        request = arg0;
    }
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
	//查询全部链接
	public String getAllLink() throws Exception{
		
        gatherdata1result = gatherData1ResultDao.getAllLink();
        java.util.Date ds = new java.util.Date();
        SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ffs = fs.format(ds);
		if (gatherdata1result.size() != 0) {		
		  for(int i = 0; i < gatherdata1result.size(); i++)
          {
			  gatherdata1result.get(i).setId(gatherdata1result.get(i).getId());
			  gatherdata1result.get(i).setExporttime(fs.parse(ffs));
			  gatherdata1result.get(i).setSfexport(1l);
			  gatherData1ResultDao.merge(gatherdata1result.get(i));
          }
		}
		return "export";
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
