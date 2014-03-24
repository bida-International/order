package com.demo.dao.Impl.tools;

import javax.annotation.Resource;

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

	public PageBean getAllByPage(Long keyCreateTime, Integer pageSize, Integer page) {
		PageBean pageBean = new PageBean(pageDao);
        String hql = "from GatherData1Result where keyCreateTime=" + keyCreateTime + " order by orderNum desc";
        return pageBean.getFenYe(hql, pageSize, page);
    }
	
	public GatherData1Result findUnique(Long keyCreateTime, String link) {
		return ht.findFirst("from GatherData1Result where keyCreateTime = ? and link =?", 
				new Object[] {keyCreateTime, link});
	}
	
	public void batchDelete(Long keyCreateTime) {
		ht.bulkUpdate("delete from GatherData1Result where keyCreateTime = " + keyCreateTime);
	}
}
