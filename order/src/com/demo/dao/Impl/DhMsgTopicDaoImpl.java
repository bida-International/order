package com.demo.dao.Impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.demo.dao.DhMsgTopicDao;
import com.demo.dao.PageDao;
import com.demo.entity.DhMsgTopic;
import com.demo.page.PageBean;
import com.demo.utils.ApiCacheUtils;

@Repository
public class DhMsgTopicDaoImpl extends BaseDaoImpl<DhMsgTopic, Long> implements DhMsgTopicDao{

	@Resource
	private PageDao pageDao;
	
	public DhMsgTopicDaoImpl() {
		super(DhMsgTopic.class);
	}
	
	public DhMsgTopic getByTopicId(Long topicId) {
		return ht.findFirst("from DhMsgTopic where topicId=?", new Object[]{topicId});
	}
	
	/**
	 * 分页查询站内信
	 * @param pageSize
	 * @param pageNum
	 * @param userId
	 * @return
	 */
	public PageBean getAllByPage(int pageSize, int page, Long userId,
			String dhAccount, Integer msgType, Integer readStatus, Integer marked) {
		PageBean pageBean = new PageBean(pageDao);
        String queryPart = "";
        if (userId != null) {
        	queryPart += "bdUserId = " + userId + " ";
        }
        if (dhAccount != null) {
        	if (!queryPart.equals("")) {
        		queryPart += "and ";
        	}
        	queryPart += " dhAccount = '" + dhAccount + "' ";
        }
        if (msgType != null) {
        	if (!queryPart.equals("")) {
        		queryPart += "and ";
        	}
        	queryPart += " msgType = '" + msgType + "' ";
        }
        if (readStatus != null) {
        	if (!queryPart.equals("")) {
        		queryPart += "and ";
        	}
        	queryPart += " readStatus = '" + readStatus + "' ";
        }
        if (marked != null) {
        	if (!queryPart.equals("")) {
        		queryPart += "and ";
        	}
        	queryPart += " marked = '" + marked + "' ";
        }
        if (!queryPart.equals("")) {
        	queryPart = "where " + queryPart;
        }
        String hql = "from DhMsgTopic " + queryPart + " order by lastReplyTime desc";
        return pageBean.getFenYe(hql, pageSize, page);
    }
	
	/**
	 * 分配站内信
	 */
	public void fenpeiMsg(String dhAccount, Long bdUserId, String bdUserName) {
		ht.bulkUpdate("update DhMsgTopic set bdUserId = ?, bdUserName = ? where bdUserId is null and dhAccount = ?", 
				 new Object[]{bdUserId, bdUserName, dhAccount});
	}
	/**
	 * 获取未分配的(订单种类)站内信列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DhMsgTopic> getNotFenpeiMsgList(String dhAccount) {
		return ht.find("from DhMsgTopic where bdUserId is null and (msgType = 2 or msgType = 4) and dhAccount = ?", new Object[]{dhAccount});
	}
	//分配站内信给客服
	public void fenpeiMsgs(Long bdUserId){
		
		ht.bulkUpdate("update DhMsgTopic set bdUserId = ? where bdUserId = ?",new Object[]{bdUserId});
	}
	//转移数据 
	public void updateTransferMsgs(Long kefu,Long kefu1){
		ht.bulkUpdate("update DhMsgTopic set bdUserId = ? where bdUserId = ?",new Object[]{kefu1,kefu});
	}
	
	/**
	 *  获取一个账号最新的站内信的回复时间
	 */
	@SuppressWarnings("unchecked")
	public Long getTop1LastReplyTime(String dhAccount) {
		String cacheKey = "Top1MsgTopicLastReplyTime_" + dhAccount;
		Object obj = ApiCacheUtils.getInstance().getCache(cacheKey);
		if (obj != null) {
			return (Long) obj;
		}
		
		final String hql = "from DhMsgTopic where dhAccount='" + dhAccount + "' order by lastReplyTime desc";
		List<DhMsgTopic> msgTopics = ht.executeFind(new HibernateCallback(){
				public Object doInHibernate(Session s)
				throws HibernateException, SQLException {			
				return s.createQuery(hql).setFirstResult(0).setMaxResults(1).list();  
			}
		});
		Long lastReplyTime = null;
		if (msgTopics != null && !msgTopics.isEmpty()) {
			lastReplyTime = msgTopics.get(0).getLastReplyTime();
			ApiCacheUtils.getInstance().addCache(cacheKey, lastReplyTime);
		}
		return lastReplyTime;
	}
	
	/**
	 *  更新一个账号最新的站内信的回复时间缓存
	 */
	public void updateTop1LastReplyTimeCache(String dhAccount, Long lastReplyTime) {
		String cacheKey = "Top1MsgTopicLastReplyTime_" + dhAccount;
		ApiCacheUtils.getInstance().addCache(cacheKey, lastReplyTime);
	}
}
