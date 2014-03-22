package com.demo.dao.tools;

import com.demo.dao.BaseDao;
import com.demo.entity.tools.GatherData1Key;
import com.demo.page.PageBean;

public interface GatherData1KeyDao extends BaseDao<GatherData1Key, Long> {

	public GatherData1Key getByCreateTime(Long createTime);
	
	public PageBean getAllByPage(Integer pageSize, Integer page);
}
