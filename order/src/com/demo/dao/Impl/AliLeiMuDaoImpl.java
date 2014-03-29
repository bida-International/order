package com.demo.dao.Impl;

import org.springframework.stereotype.Repository;

import com.demo.dao.AliLeiMuDao;
import com.demo.entity.AliLeiMu;

@Repository
public class AliLeiMuDaoImpl extends BaseDaoImpl<AliLeiMu, Long> implements AliLeiMuDao {

	public AliLeiMuDaoImpl() {
		super(AliLeiMu.class);
	}

	// 取一级父类目id
	public Long getFirstParentCateId(Long cateId) {
		AliLeiMu aliLeiMu = ht.findFirst("from AliLeiMu where id=?", new Object[] {cateId});
		if (aliLeiMu == null) {
			return null;
		}
		
		if (aliLeiMu.getParentId() != 0) {
			return this.getFirstParentCateId(aliLeiMu.getParentId());
		} else {
			return aliLeiMu.getId();
		}
	}
}
