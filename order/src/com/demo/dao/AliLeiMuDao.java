package com.demo.dao;

import com.demo.entity.AliLeiMu;

public interface AliLeiMuDao extends BaseDao<AliLeiMu, Long> {

	// ȡһ������Ŀid
	public Long getFirstParentCateId(Long cateId);
}
