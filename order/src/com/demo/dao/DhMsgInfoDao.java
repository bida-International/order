package com.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.entity.DhMsgInfo;

public interface DhMsgInfoDao extends BaseDao<DhMsgInfo, Long>{

	public DhMsgInfo getByInfoId(Long infoId);

	public List<DhMsgInfo> getAllByTopicId(Long topicId);
}
