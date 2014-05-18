package com.demo.biz.msg;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.biz.dhgate.DhCommonApiBiz;
import com.demo.biz.dhgate.DhMsgApiBiz;
import com.demo.biz.dhgate.DhOrderBiz;
import com.demo.biz.dhgate.DhProductApiBiz;
import com.demo.dao.DhMsgInfoDao;
import com.demo.dao.DhMsgTopicDao;
import com.demo.dao.LeiMuDao;
import com.demo.dao.ZhangHaoDao;
import com.demo.entity.DhMsgInfo;
import com.demo.entity.DhMsgTopic;
import com.demo.entity.ZhangHao;
import com.demo.entity.LeiMuTable;
import com.demo.page.PageBean;

/**
 * �ػ�վ����
 *
 */
@Service
@Transactional
public class DhMsgBiz {

	@Resource
	private DhMsgTopicDao dhMsgTopicDao;
	@Resource
	private DhMsgInfoDao dhMsgInfoDao;
	@Resource
	private DhOrderBiz DhOrderBiz;
	@Resource
	private DhProductApiBiz dhProductApiBiz;
	@Resource
	private LeiMuDao leiMuDao;
	@Resource
	private DhMsgApiBiz dhMsgApiBiz;
	@Resource
	private ZhangHaoDao zhangHaoDao;
	
	/**
	 * ��ҳ��ѯվ����
	 * @param pageSize
	 * @param page
	 * @param userId
	 * @return
	 */
	public PageBean getAllByPage(int pageSize, int page, Long userId,
			String dhAccount, Integer msgType, Integer readStatus, Integer marked) {
		return dhMsgTopicDao.getAllByPage(pageSize, page, userId,
				dhAccount, msgType, readStatus, marked);
	}
	
	public DhMsgTopic getMsgTopicByTopicId(Long topicId) {
		return dhMsgTopicDao.getByTopicId(topicId);
	}
	
	public List<DhMsgInfo> getMsgInfosByTopicId(Long topicId) {
		return dhMsgInfoDao.getAllByTopicId(topicId);
	}
	
	/**
	 * �Զ�����վ����
	 * @param dhAccount
	 */
	public void fenpeiMsg(ZhangHao dhAccount) {
		dhMsgTopicDao.fenpeiMsg(dhAccount.getAccount(), dhAccount.getBd_user_id(),
				dhAccount.getBd_user_name());
	}
	
	/**
	 * �Զ����䶩�����͵�վ����
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private void fenpeiOrderMsg(DhMsgTopic topic, ZhangHao dhAccount) {
		// ȡ��������
		JSONObject order = DhOrderBiz.getOrder(topic.getParm(), dhAccount);
		if (order == null) {
			return;
		}
		// ȡ������Ʒ����
		String rfxid = order.getJSONObject("rfxDto").getString("rfxid");
		JSONObject orderProduct = DhOrderBiz.getOrderProduct(rfxid, dhAccount);
		if (orderProduct == null || orderProduct.getString("lstProductDTO").equals("null") || 
				orderProduct.getString("lstProductDTO").equals("[]") ) {
			return;
		}
		// ȡ��Ʒ����
		String itemCode = orderProduct.getJSONArray("lstProductDTO").getJSONObject(0).getString("itemcode");
		JSONObject product = dhProductApiBiz.getProduct(itemCode, dhAccount);
		if (product == null) {
			return;
		}
		// ȡ��ƷcatePubId��Ӧ��userId��ִ�з���
		String catePubId = product.getJSONObject("product").getString("catePubId");
		String parentCatePubId = catePubId.substring(0, 3);
		LeiMuTable leiMu = leiMuDao.getByCateId(parentCatePubId);
		if (leiMu == null) {
			return;
		}
		topic.setBdUserId(leiMu.getMsgFenpeiUserId());
		topic.setBdUserName(leiMu.getMsgFenpeiUserName());
		dhMsgTopicDao.merge(topic);
	}
	
	public void delete(String topicIds) {
		String[] idArr = topicIds.split(",");
		for (String id : idArr) {
			Long topicId = Long.parseLong(id);
			// ɾ��msgInfo
			List<DhMsgInfo> msgInfoList = dhMsgInfoDao.getAllByTopicId(topicId);
			for (DhMsgInfo msgInfo : msgInfoList) {
				dhMsgInfoDao.delete(msgInfo);
			}
			// ɾ��msgTopic
			DhMsgTopic msgTopic = dhMsgTopicDao.getByTopicId(topicId);
			dhMsgTopicDao.delete(msgTopic);
		}
	}
	
	public void updateReaded(String topicIds) {
		if (topicIds == null || topicIds.equals("")) {
			return;
		}
		
		String[] topicIdArr = topicIds.split(",");
		String updateTopicIds = "";
		ZhangHao dhAccount = null;
		for (String topicId : topicIdArr) {
			DhMsgTopic msgTopic = dhMsgTopicDao.getByTopicId(Long.parseLong(topicId));
			if (msgTopic.getReadStatus().equals(0)) {
				updateTopicIds += topicId + ",";
				if (dhAccount == null) {
					dhAccount = zhangHaoDao.findUnique(msgTopic.getDhAccount(), 
							DhCommonApiBiz.ACCOUNT_TYPE);
				}
				msgTopic.setReadStatus(1);
				dhMsgTopicDao.merge(msgTopic);
			}
		}
		updateTopicIds = updateTopicIds.substring(0, updateTopicIds.length() - 1);
		dhMsgApiBiz.updateReaded(dhAccount, updateTopicIds);
	}
}
