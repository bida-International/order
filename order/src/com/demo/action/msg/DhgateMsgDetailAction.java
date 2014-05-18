package com.demo.action.msg;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.demo.action.BaseAction;
import com.demo.biz.dhgate.DhMsgApiBiz;
import com.demo.biz.msg.DhMsgBiz;
import com.demo.entity.DhMsgInfo;
import com.demo.entity.DhMsgTopic;
import com.demo.utils.Struts2Utils;

@Controller("msg.dhgateMsgDetailAction")
@Scope("prototype")
public class DhgateMsgDetailAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private DhMsgBiz dhMsgBiz;
	@Resource
	private DhMsgApiBiz dhMsgApiBiz;
	
	private Long topicId;
	private String replyContent;
	
	private DhMsgTopic msgTopic;
	private List<DhMsgInfo> msgInfos;
	
	private String referer;
	
	public String execute() {
		msgTopic = dhMsgBiz.getMsgTopicByTopicId(topicId);
		msgInfos = dhMsgBiz.getMsgInfosByTopicId(topicId);
		
		if (msgTopic.getReadStatus() == 0) {
			dhMsgBiz.updateReaded(topicId.toString());
		}
		
		referer = Struts2Utils.getRequest().getHeader("referer");
		return SUCCESS;
	}
	
	public String synchData() {
		boolean success = true;
		String result = dhMsgApiBiz.synchMsgTopicAndInfo(topicId);
		if (result.contains("发生错误")) {
    		success = false;
    	}
		
		Struts2Utils.renderJson(result, success);
		return null;
	}

	public String reply() {
		boolean success = true;
		String result = dhMsgApiBiz.sendReply(topicId, replyContent);
		if (result.contains("发生错误")) {
    		success = false;
    	}
		
		Struts2Utils.renderJson(result, success);
		return null;
	}
	
	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public DhMsgTopic getMsgTopic() {
		return msgTopic;
	}

	public void setMsgTopic(DhMsgTopic msgTopic) {
		this.msgTopic = msgTopic;
	}

	public List<DhMsgInfo> getMsgInfos() {
		return msgInfos;
	}

	public void setMsgInfos(List<DhMsgInfo> msgInfos) {
		this.msgInfos = msgInfos;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}
}
