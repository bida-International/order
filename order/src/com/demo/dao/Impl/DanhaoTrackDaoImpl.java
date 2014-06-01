package com.demo.dao.Impl;

import org.springframework.stereotype.Repository;

import com.demo.dao.DanhaoTrackDao;
import com.demo.entity.order.DanhaoTrack;

@Repository
public class DanhaoTrackDaoImpl extends BaseDaoImpl<DanhaoTrack, Long> implements
		DanhaoTrackDao {

	public DanhaoTrackDaoImpl() {
		super(DanhaoTrack.class);
	}

	public DanhaoTrack findUniqueByDanhao(String danhao) {
		String hql = "from DanhaoTrack where danhao = ?";
		return ht.findFirst(hql, new Object[] { danhao });
	}

}
