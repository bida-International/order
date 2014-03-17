package com.demo.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.DhMsgInfoDao;
import com.demo.entity.DhMsgInfo;

@Repository
public class DhMsgInfoDaoImpl extends BaseDaoImpl<DhMsgInfo, Long> implements DhMsgInfoDao{

	public DhMsgInfoDaoImpl() {
		super(DhMsgInfo.class);
	}

	public DhMsgInfo getByInfoId(Long infoId) {
		return ht.findFirst("from DhMsgInfo where infoId=?", new Object[]{infoId});
	}
	
	@SuppressWarnings("unchecked")
	public List<DhMsgInfo> getAllByTopicId(Long topicId) {
		return ht.find("from DhMsgInfo where topicId = " + topicId + " order by createTime");
	}
}
