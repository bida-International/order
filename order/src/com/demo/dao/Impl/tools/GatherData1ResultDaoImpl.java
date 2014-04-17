package com.demo.dao.Impl.tools;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.demo.dao.PageDao;
import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.dao.tools.GatherData1ResultDao;
import com.demo.entity.tools.GatherData1Result;
import com.demo.page.PageBean;

@Repository
public class GatherData1ResultDaoImpl extends BaseDaoImpl<GatherData1Result, Long> implements
		GatherData1ResultDao {
	
	public GatherData1ResultDaoImpl() {
		super(GatherData1Result.class);
	}
	
	@Resource
	private PageDao pageDao;

	public PageBean getAllByPage(Long keyCreateTime, Integer pageSize, Integer page,String exporttime,String exporttime1) {
		PageBean pageBean = new PageBean(pageDao);
		String hql = null;
		if (exporttime != null && !"".equals(exporttime) && (exporttime1 != null && !"".equals(exporttime1))) {
				hql = "from GatherData1Result where keyCreateTime=" + keyCreateTime + " and (convert(varchar(10),exporttime,120) between '"+exporttime+"'and '"+exporttime1+"') order by orderNum desc";	
		}else if(exporttime == null || "".equals(exporttime) && (exporttime1 == null || "".equals(exporttime1))){
				hql = "from GatherData1Result where keyCreateTime=" + keyCreateTime + " order by orderNum desc";	
		}
        return pageBean.getFenYe(hql, pageSize, page);
    }
	
	public void batchDelete(Long keyCreateTime) {
		ht.bulkUpdate("delete from GatherData1Result where keyCreateTime = " + keyCreateTime);
	}
	//修改链接状态
	public void updateLinkState(Long keyCreateTime, String currenttime){
		ht.bulkUpdate("update GatherData1Result set sfexport = 1 , exporttime = '"+currenttime+"' where id in(from GatherData1Result where keyCreateTime = " + keyCreateTime+")");
	}
	//查询全部链接
	public List<GatherData1Result> getAllLink(Long keyCreateTime) {			 
		List<GatherData1Result> gather = ht.find("from GatherData1Result where keyCreateTime=" + keyCreateTime + " order by orderNum desc");
		return gather;
	}
	public GatherData1Result findUnique(Long keyCreateTime, String link) {
		return ht.findFirst("from GatherData1Result where keyCreateTime = ? and link =?", 
				new Object[] {keyCreateTime, link});
	}

}
