package com.demo.dao;

import com.demo.entity.AliLeiMu;

public interface AliLeiMuDao extends BaseDao<AliLeiMu, Long> {

	// 取一级父类目id
	public Long getFirstParentCateId(Long cateId);
}
