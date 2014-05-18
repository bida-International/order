package com.demo.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.entity.DhMsgTopic;
import com.demo.entity.Courier.YunFeiTable;
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
	public PageBean getAllByPage(int pageSize, int page, Long userId,String dhAccount, 
			Integer msgType, Integer readStatus, Integer marked);
	
	/**
	 * 获取未分配的(订单种类)站内信列表
	 * @return
	 */
	public List<DhMsgTopic> getNotFenpeiMsgList(String dhAccount);
	
	public void fenpeiMsg(String dhAccount, Long bdUserId, String bdUserName);
	//分配站内信给客服
	public void fenpeiMsgs(Long bdUserId);
	//转移数据 
	public void updateTransferMsgs(Long kefu,Long kefu1);
	
	// 获取一个账号最新的站内信的回复时间
	public Long getTop1LastReplyTime(String dhAccount);
	// 更新一个账号最新的站内信的回复时间缓存
	public void updateTop1LastReplyTimeCache(String dhAccount, Long lastReplyTime);
}
