package com.demo.dao;

import java.util.List;

import com.demo.entity.DhMsgTemplate;

public interface DhMsgTemplateDao extends BaseDao<DhMsgTemplate, Long> {

	public DhMsgTemplate findUniqueByType(Integer type);
	
	public List<DhMsgTemplate> getEnableTempateList();
}
