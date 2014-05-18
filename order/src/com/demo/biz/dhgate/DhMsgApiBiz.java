package com.demo.biz.dhgate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.DhMsgInfoDao;
import com.demo.dao.DhMsgTopicDao;
import com.demo.dao.ZhangHaoDao;
import com.demo.dao.user.YeWu1Dao;
import com.demo.entity.DhMsgInfo;
import com.demo.entity.DhMsgTopic;
import com.demo.entity.ZhangHao;
import com.demo.entity.user.YeWu1;
import com.demo.utils.ApplicationUtils;
import com.demo.utils.DateUtils;
import com.demo.utils.HttpClientUtils;

/**
 * 敦煌网站内信服务
 * 
 */
@Service
@Transactional
public class DhMsgApiBiz {

	@Resource
	private DhCommonApiBiz dhCommonApiBiz;
	@Resource
	private DhMsgTopicDao dhMsgTopicDao;
	@Resource
	private DhMsgInfoDao dhMsgInfoDao;
	@Resource
	private ZhangHaoDao  zhangHaoDao;
	@Resource
	private YeWu1Dao yewu1dao;
	private final Integer pageSize = 20;
	private Integer updateCount = 0;
	private Long newLastReplyTime; // 新的最后回复时间
	private Long dbLastReplyTime; // 数据库该账号最新一条数据的最后回复时间

	/**
	 *  同步订单数据(用于自动同步)
	 * @param dhAccount
	 * @return
	 */
	public String autoFetchMsg(ZhangHao dhAccount) {
		String msgType = "";
		Integer beforeDay =1;
		
		Long msgUpdateTime = dhAccount.getMsg_update_time();
		Date curTime = new Date();
		if (msgUpdateTime != null) {
			Integer intervalDays = DateUtils.getIntervalDays(new Date(msgUpdateTime), curTime);
			if (intervalDays > 1) {
				beforeDay = intervalDays;
			}
		} else {
			beforeDay = 1; // 初始从5天前的数据开始取
		}
		
		String result = this.fetchMsg(dhAccount, msgType, beforeDay);
		if (!result.contains("错误")) {
			// 更新
			if (newLastReplyTime != null) {
				dhMsgTopicDao.updateTop1LastReplyTimeCache(dhAccount.getAccount(), newLastReplyTime);
			}
			
			// 更新同步时间
			dhAccount.setMsg_update_time(curTime.getTime());
			zhangHaoDao.merge(dhAccount);
		}
		return result;
	}
	
	/**
	 * 同步订单数据(用于页面手动同步)
	 * @param account
	 * @param msgType
	 * @param beforeDay
	 * @return
	 */
	public String fetchMsg(String account, String msgType, Integer beforeDay) {
		ZhangHao dhAccount = zhangHaoDao.findUnique(account, 
				DhCommonApiBiz.ACCOUNT_TYPE);
		return this.fetchMsg(dhAccount, msgType, beforeDay);
	}
	
	/**
	 * 同步订单数据(用于页面手动同步)
	 * @param account
	 * @param msgType
	 * @param beforeDay
	 * @return
	 */
	public String fetchMsg(ZhangHao dhAccount, String msgType, Integer beforeDay) {
		try {
			updateCount = 0;
			newLastReplyTime = null;
			dbLastReplyTime = dhMsgTopicDao.getTop1LastReplyTime(dhAccount.getAccount());
			String result = this.fetchMsg(dhAccount, msgType, beforeDay, 1);
			if (result.equals("success")) {
				return "本次成功更新 " + updateCount + " 条站内信数据";
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "发生错误：未知异常";
		}
	}

	/**
	 * 抓取一页数据
	 * 
	 * @param dhgateAccount
	 * @param msgType
	 * @param beforeDay
	 * @param pageNum
	 * @param updateCount
	 *            更新的记录数
	 * @return
	 */
	private String fetchMsg(ZhangHao dhAccount, String msgType,
			Integer beforeDay, Integer pageNum) {
		String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
				"dh.messages.get")) {
			return "发生错误：" + DhCommonApiBiz.ERR_TOKEN;
		}
		paramMap.put("appid", "");
		paramMap.put("beforeday", beforeDay.toString());
		paramMap.put("marked", "");
		paramMap.put("msgtype", msgType);
		paramMap.put("orderField", "lastreplytime"); // 按最后回复时间倒序排序
		paramMap.put("orderType", "desc");
		paramMap.put("pagenum", pageNum.toString());
		paramMap.put("pagesize", this.pageSize.toString());
		paramMap.put("recivereaded", "");
		paramMap.put("searchContent", "");
		paramMap.put("state", "");
		paramMap.put("systemuserid", "");

		JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
		if (respJson != null) {
			if (respJson.containsKey("code")) {
				if (respJson.getString("code").equals("2") || 
						respJson.getString("code").equals("40")) {
					dhCommonApiBiz.clearAccessToken(dhAccount);
					return "发生错误：" + DhCommonApiBiz.ERR_TOKEN;
				} else if (!respJson.getString("code").equals("0")) {
					return "发生错误：" + respJson.getString("message");
				}
			}
			
			JSONObject statusObj = respJson.getJSONObject("status");
			if (Integer.parseInt(statusObj.getString("code")) == 0) {
				if (respJson.getString("messageTopicList").equals("null") || 
						respJson.getString("messageTopicList").equals("[]") ) {
					return "success";
				}

				boolean stopFlag = false;
				JSONArray msgTopics = respJson.getJSONArray("messageTopicList");
				for (int i = 0; i < msgTopics.size(); i++) {
					// 检查最后回复时间，当它小于等于dbLastReplyTime时，表示接下来的数据已在库中或已更新，结束取数据
					Long lastReplyTime = msgTopics.getJSONObject(i).getLong("lastreplytime");
					if (dbLastReplyTime != null 
							&& lastReplyTime <= dbLastReplyTime) {
						stopFlag = true;
						break;
					}
					// 第一条为新的最后回复时间
					if (newLastReplyTime == null) { 
						newLastReplyTime = lastReplyTime;
					}
					
					updateCount += this.updateMsgTopic(dhAccount,
							msgTopics.getJSONObject(i));
				}
				// 当取到的topic数量与pagesize相等时，继续取下一页
				if (!stopFlag && msgTopics.size() == this.pageSize) {
					this.fetchMsg(dhAccount, msgType, beforeDay,
							pageNum + 1);
				}
				return "success";
			} else {
				return "发生错误：" + statusObj.getString("message");
			}
		}
		return "发生错误：" + DhCommonApiBiz.CONN_ERR;
	}

	/**
	 * 更新msgTopic到本地数据
	 * 
	 * @param json
	 * @return 是否有更新: 0-没有更新（不需要）；1-有更新
	 */
	private int updateMsgTopic(ZhangHao dhAccount, JSONObject json) {
		boolean needUpdate = true;
		Long topicId = json.getLong("tdMessageTopicId");
		Long lastReplyTime = json.getLong("lastreplytime");
		Integer marked = this.getTopicMarkedState(json, dhAccount);
		DhMsgTopic msgTopic = dhMsgTopicDao.getByTopicId(topicId);
		if (msgTopic == null) {
			msgTopic = new DhMsgTopic();
			msgTopic.setTopicId(topicId);
			msgTopic.setMsgType(json.getInt("msgtype"));
			msgTopic.setMcLock(json.getInt("mcLock"));
			msgTopic.setParm(json.getString("parm"));
			msgTopic.setSenderId(json.getString("senderid"));
			msgTopic.setSenderReaded(json.getInt("senderreaded"));
			msgTopic.setRecieverId(json.getString("recieverid"));
			msgTopic.setRecieverReaded(json.getInt("recivereaded"));
			msgTopic.setDhAccount(dhAccount.getAccount());
			msgTopic.setMarked(marked);
			// 昵称处理
			msgTopic.setSenderNickname(this.getNickname(msgTopic.getSenderId(),
					json.getString("senderNickname"), dhAccount));
			msgTopic.setRecieverNickname(this.getNickname(msgTopic.getRecieverId(),
					json.getString("receiverNickname"), dhAccount));
		} else if (lastReplyTime.equals(msgTopic.getLastReplyTime())) {
			if (msgTopic.getSenderReaded() != json.getInt("senderreaded") ||
					msgTopic.getRecieverReaded() != json.getInt("recivereaded")) { //读取状态发生变化时更新
				msgTopic.setSenderReaded(json.getInt("senderreaded"));
				msgTopic.setRecieverReaded(json.getInt("recivereaded"));
			} else if (!msgTopic.getMarked().equals(marked)) {
				msgTopic.setMarked(marked);
			} else {
				needUpdate = false;
			}
		}

		if (needUpdate) {
			msgTopic.setTitle(json.getString("title"));
			msgTopic.setLastReplyTime(lastReplyTime);
			msgTopic.setMsgReplyCount(json.getInt("msgreplycount"));
			// 已读状态更新
			Integer readStatus = 0;
			if (msgTopic.getSenderReaded() == 1 
					&& msgTopic.getSenderNickname() != null 
					&& msgTopic.getSenderNickname().equals(dhAccount.getUser_nickname())) {
				readStatus = 1;
			} else if (msgTopic.getRecieverReaded() == 1 
					&& msgTopic.getRecieverNickname() != null
					&& msgTopic.getRecieverNickname().equals(dhAccount.getUser_nickname())) {
				readStatus = 1;
			}
			msgTopic.setReadStatus(readStatus);

			if (msgTopic.getBdUserId() == null) {
				String ff = DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss:SSS");
		        YeWu1 yy = yewu1dao.getMsgTimes().get(0);
		        
		        msgTopic.setBdUserId(yy.getUserid());
				this.dhMsgTopicDao.merge(msgTopic);
				
				yy.setLetterTime(ff);
				yewu1dao.merge(yy);
			}
			// 更新站内信详情信息
			this.fetchTopicMsgInfo(dhAccount, msgTopic, false);
		
			return 1;
		}
		return 0;
	}

	/**
	 * 同步一条站内信数据
	 * @param topicId
	 */
	public String synchMsgTopicAndInfo(Long topicId) {
		try {
			DhMsgTopic msgTopic = dhMsgTopicDao.getByTopicId(topicId);
			if (msgTopic == null) {
				return "发生错误：数据不存在";
			}
			String account = msgTopic.getDhAccount();
			if (account == null) {
				return "发生错误：数据错误";
			}
			ZhangHao dhAccount = zhangHaoDao.findUnique(account, 
					DhCommonApiBiz.ACCOUNT_TYPE);
			if (dhAccount == null) {
				return "发生错误：敦煌账号未添加";
			}
			return this.fetchTopicMsgInfo(dhAccount, msgTopic, true);
		} catch (Exception e) {
			e.printStackTrace();
			return "发生错误：未知异常";
		}
	}
	
	/**
	 * 抓取topic的详情信息
	 * @param msgTopic	
	 * @param updateMsgTopic 是否更新主题数据
	 */
	private String fetchTopicMsgInfo(ZhangHao dhAccount, DhMsgTopic msgTopic, 
			boolean updateMsgTopic) {
		String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
				"dh.message.detail.get")) {
			return "发生错误：" + DhCommonApiBiz.ERR_TOKEN;
		}
		paramMap.put("appid", (String) ApplicationUtils.get("dhgateAppKey"));
		paramMap.put("messageTopicId", msgTopic.getTopicId().toString());

		JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
		if (respJson != null) {
			if (respJson.containsKey("code")) {
				if (respJson.getString("code").equals("2") || 
						respJson.getString("code").equals("40")) {
					dhCommonApiBiz.clearAccessToken(dhAccount);
					return "发生错误：" + DhCommonApiBiz.ERR_TOKEN;
				} else if (!respJson.getString("code").equals("0")) {
					return "发生错误:" + respJson.getString("message");
				}
			}
		
			JSONObject statusObj = respJson.getJSONObject("status");
			if (Integer.parseInt(statusObj.getString("code")) == 0) {
				JSONArray msgInfos = respJson.getJSONObject("message")
						.getJSONArray("messageInfo");
				for (int i = 0; i < msgInfos.size(); i++) {
					this.updateMsgInfo(msgInfos.getJSONObject(i), msgTopic);
				}
				
				// 更新主题数据
				if (updateMsgTopic) {
					JSONObject msgTopicJson = respJson.getJSONObject("message")
							.getJSONObject("messageTopic");
					msgTopic.setTitle(msgTopicJson.getString("title"));
					msgTopic.setLastReplyTime(msgTopicJson.getLong("lastreplytime"));
					msgTopic.setMsgReplyCount(msgTopicJson.getInt("msgreplycount"));
					msgTopic.setSenderReaded(msgTopicJson.getInt("senderreaded"));
					msgTopic.setRecieverReaded(msgTopicJson.getInt("recivereaded"));
					dhMsgTopicDao.merge(msgTopic);
				}
				return "success";
			}  else {
				return "发生错误：" + statusObj.getString("message");
			}
		}
		return "发生错误：" + DhCommonApiBiz.CONN_ERR;
	}
	
	/**
	 * 更新消息详情
	 * @param json
	 * @param msgTopic
	 */
	private void updateMsgInfo(JSONObject json, DhMsgTopic msgTopic) {
		Long infoId = json.getLong("tdMessageInfoId");
		DhMsgInfo msgInfo = dhMsgInfoDao.getByInfoId(infoId);
		if (msgInfo == null) {
			msgInfo = new DhMsgInfo();
			msgInfo.setTopicId(msgTopic.getTopicId());
			msgInfo.setInfoId(infoId);
			msgInfo.setInfoContent(json.getString("content"));
			msgInfo.setSenderId(json.getString("senderid"));
			msgInfo.setRecieverId(json.getString("recieverid"));
			msgInfo.setCreateTime(json.getLong("createtime"));
			if (!json.getString("attatchment").equals("null")) {
				msgInfo.setAttachment(json.getString("attatchment"));
			}
			// 昵称处理
			msgInfo.setSenderNickname(this.getNickname(msgInfo.getSenderId(),
					json.getString("senderNickname"), msgTopic));
			msgInfo.setRecieverNickname(this.getNickname(msgInfo.getRecieverId(),
					json.getString("receiverNickname"), msgTopic));
			dhMsgInfoDao.merge(msgInfo);
		}
	}
	
	/**
	 * 取发送人或接收人的昵称(更新站内信Topic时调用)
	 * 
	 * 当前api返回的昵称为空, 需要另做处理
	 * @param dhUserId
	 * @param dhUserNickname
	 * @param dhgateAccount
	 * @return
	 */
	private String getNickname(String dhUserId, String dhUserNickname,
			ZhangHao dhAccount) {
		if (!dhUserNickname.equals("null")) {
			return dhUserNickname;
		}
		
		if (dhUserId.equals(dhAccount.getUser_id())) {
			return dhAccount.getUser_nickname();
		} else {
			// TODO 调用api获取取用户昵称信息, 因为官方还未提供该功能api，暂时返回null
			return null;
		}
	}

	/**
	 * 取发送人或接收人的昵称(更新站内信详情时调用)
	 * @param dhUserId
	 * @param dhUserNickname
	 * @param msgTopic
	 * @return
	 */
	private String getNickname(String dhUserId, String dhUserNickname,
			DhMsgTopic msgTopic) {
		if (!dhUserNickname.equals("null")) {
			return dhUserNickname;
		}
		if (dhUserId.equals(msgTopic.getSenderId())) {
			return msgTopic.getSenderNickname();
		} else if (dhUserId.equals(msgTopic.getRecieverId())) {
			return msgTopic.getRecieverNickname();
		}
		return null;
	}
	
	/**
	 * 取站内信主题标记状态
	 * @param topicJson
	 * @param dhUserId
	 * @return
	 */
	private Integer getTopicMarkedState(JSONObject topicJson, ZhangHao dhAccount) {
		if (topicJson.getString("recieverid").equals(dhAccount.getUser_id()) &&
				topicJson.getInt("reciveMarked") == 1) {
			return 1;
		} else if (topicJson.getString("senderid").equals(dhAccount.getUser_id()) &&
				topicJson.getInt("senderMarked") == 1) {
			return 1;
		} 
		return 0;
	}
	
	/**
	 * 回复站内信
	 * @param topicId
	 * @param content
	 * @return
	 */
	public String sendReply(Long topicId, String content) {
		try {
			DhMsgTopic msgTopic = dhMsgTopicDao.getByTopicId(topicId);
			if (msgTopic == null) {
				return "发生错误：数据不存在";
			}
			String account = msgTopic.getDhAccount();
			if (account == null) {
				return "发生错误：数据错误";
			}
			ZhangHao dhAccount = zhangHaoDao.findUnique(account, 
					DhCommonApiBiz.ACCOUNT_TYPE);
			if (dhAccount == null) {
				return "发生错误：敦煌账号未添加";
			}
			
			String senderId = null;
			String recieverId = null;
			if (msgTopic.getSenderId().equals(dhAccount.getUser_id())) {
				senderId = msgTopic.getSenderId();
				recieverId = msgTopic.getRecieverId();
			} else if (msgTopic.getRecieverId().equals(dhAccount.getUser_id())) {
				senderId = msgTopic.getRecieverId();
				recieverId = msgTopic.getSenderId();
			}
			if (senderId == null) {
				return "发生错误：数据错误";
			}
			
			String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
			Map<String, String> paramMap = new HashMap<String, String>();
			if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
					"dh.message.info.reply")) {
				return "发生错误：" + DhCommonApiBiz.ERR_TOKEN;
			}
			paramMap.put("attatchmenturl", "");
			paramMap.put("content", content);
			paramMap.put("senderid", senderId);
			paramMap.put("tdMessageTemplatesId", "");
			paramMap.put("tdMessageTopicId", topicId.toString());
			paramMap.put("toSysuserbaseid", recieverId);
	
			JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
			if (respJson != null) {
				if (respJson.containsKey("code")) {
					if (respJson.getString("code").equals("2") || 
						respJson.getString("code").equals("40")) {
						dhCommonApiBiz.clearAccessToken(dhAccount);
						return "发生错误：" + DhCommonApiBiz.ERR_TOKEN;
					} else if (!respJson.getString("code").equals("0")) {
						return "发生错误：" + respJson.getString("message");
					}
				}
				
				JSONObject statusObj = respJson.getJSONObject("status");
				if (Integer.parseInt(statusObj.getString("code")) == 0) {
					return "success";
				} else {
					return "发生错误：" + statusObj.getString("message");
				}
			} else {
				return "发生错误：" + DhCommonApiBiz.CONN_ERR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "发生错误：未知异常";
		}
	}
	
	/**
	 * 更新已读状态
	 * @param msgTopic	
	 * @param updateMsgTopic 是否更新主题数据
	 */
	public String updateReaded(ZhangHao dhAccount, String msgTopicIds) {
		String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
				"dh.message.readed.update")) {
			return "发生错误：" + DhCommonApiBiz.ERR_TOKEN;
		}
		paramMap.put("messageTopicIdList", msgTopicIds);

		JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
		if (respJson != null) {
			System.out.println(respJson);
			if (respJson.containsKey("code")) {
				if (respJson.getString("code").equals("2") || 
						respJson.getString("code").equals("40")) {
					dhCommonApiBiz.clearAccessToken(dhAccount);
					return "发生错误：" + DhCommonApiBiz.ERR_TOKEN;
				} else if (!respJson.getString("code").equals("0")) {
					return "发生错误:" + respJson.getString("message");
				}
			}
		
			JSONObject statusObj = respJson.getJSONObject("status");
			if (Integer.parseInt(statusObj.getString("code")) == 0) {
				return "success";
			}  else {
				return "发生错误：" + statusObj.getString("message");
			}
		}
		return "发生错误：" + DhCommonApiBiz.CONN_ERR;
	}
}
