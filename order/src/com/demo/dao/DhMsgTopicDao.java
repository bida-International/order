package com.demo.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.entity.DhMsgTopic;
import com.demo.entity.Express.YunFeiTable;
import com.demo.page.PageBean;

public interface DhMsgTopicDao   extends BaseDao<DhMsgTopic,Long>{

	public DhMsgTopic getByTopicId(Long topicId);
	
	/**
	 * 分页查询站内信
	 * @param pageSize
	 * @param pageNum
	 * @param userId
	 * @return
	 */
	public PageBean getAllByPage(int pageSize, int page, Long userId,String dhAccount, Integer msgType, Integer readStatus);
	
	/**
	 * 获取未分配的(订单种类)站内信列表
	 * @return
	 */
	public List<DhMsgTopic> getNotFenpeiMsgList(String dhAccount);
	
	public void fenpeiMsg(String dhAccount, Long bdUserId, String bdUserName);
}
