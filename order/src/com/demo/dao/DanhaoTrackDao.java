package com.demo.dao;

import com.demo.entity.order.DanhaoTrack;

public interface DanhaoTrackDao extends BaseDao<DanhaoTrack, Long> {

	public DanhaoTrack findUniqueByDanhao(String danhao);
	
}
