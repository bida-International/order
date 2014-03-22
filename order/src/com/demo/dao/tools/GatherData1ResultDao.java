package com.demo.dao.tools;

import com.demo.dao.BaseDao;
import com.demo.entity.tools.GatherData1Result;
import com.demo.page.PageBean;

public interface GatherData1ResultDao extends BaseDao<GatherData1Result, Long> {

	public PageBean getAllByPage(Long keyCreateTime, Integer pageSize, Integer page) ;
	
	public void batchDelete(Long keyCreateTime);
}
