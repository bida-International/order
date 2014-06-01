package com.demo.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.DhMsgTemplateDao;
import com.demo.entity.DhMsgTemplate;

@Repository
public class DhMsgTemplateDaoImpl extends BaseDaoImpl<DhMsgTemplate, Long> implements
		DhMsgTemplateDao {

	public DhMsgTemplateDaoImpl() {
		super(DhMsgTemplate.class);
	}

	public DhMsgTemplate findUniqueByType(Integer type) {
		String hql = "from DhMsgTemplate where type = ?";
		return ht.findFirst(hql, new Object[] { type });
	}
	
	/**
	 * 获取可用的模板列表
	 */
	@SuppressWarnings("unchecked")
	public List<DhMsgTemplate> getEnableTempateList() {
		String hql = "from DhMsgTemplate where status = 1";
		return ht.find(hql);
	}
}
