package com.demo.dao.tools;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.tools.GatherData1Result;
import com.demo.page.PageBean;

public interface GatherData1ResultDao extends BaseDao<GatherData1Result, Long> {

	public PageBean getAllByPage(Long keyCreateTime, Integer pageSize, Integer page,String exporttime,String exporttime1) ;
	
	public void batchDelete(Long keyCreateTime);
	public GatherData1Result findUnique(Long keyCreateTime, String link);

	//查询全部链接
	public List<GatherData1Result> getAllLink();
}
