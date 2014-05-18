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
	 * ��ҳ��ѯվ����
	 * @param pageSize
	 * @param pageNum
	 * @param userId
	 * @return
	 */
	public PageBean getAllByPage(int pageSize, int page, Long userId,String dhAccount, 
			Integer msgType, Integer readStatus, Integer marked);
	
	/**
	 * ��ȡδ�����(��������)վ�����б�
	 * @return
	 */
	public List<DhMsgTopic> getNotFenpeiMsgList(String dhAccount);
	
	public void fenpeiMsg(String dhAccount, Long bdUserId, String bdUserName);
	//����վ���Ÿ��ͷ�
	public void fenpeiMsgs(Long bdUserId);
	//ת������ 
	public void updateTransferMsgs(Long kefu,Long kefu1);
	
	// ��ȡһ���˺����µ�վ���ŵĻظ�ʱ��
	public Long getTop1LastReplyTime(String dhAccount);
	// ����һ���˺����µ�վ���ŵĻظ�ʱ�仺��
	public void updateTop1LastReplyTimeCache(String dhAccount, Long lastReplyTime);
}
