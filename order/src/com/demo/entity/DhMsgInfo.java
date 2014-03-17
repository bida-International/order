package com.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 敦煌网站内信详情
 *
 */
@Entity
@Table(name="dh_msg_info")
public class DhMsgInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long topicId;
	private Long infoId; // 消息Id
	private String infoContent; // 消息内容
	private String attachment;
	private String senderId; // 发送人id
	private String senderNickname; // 发送人昵称
	private String recieverId;
	private String recieverNickname;
	private Long createTime;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "topic_id")
	public Long getTopicId() {
		return topicId;
	}
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	@Column(name = "info_id")
	public Long getInfoId() {
		return infoId;
	}
	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}
	@Column(name = "info_content")
	public String getInfoContent() {
		return infoContent;
	}
	public void setInfoContent(String infoContent) {
		this.infoContent = infoContent;
	}
	@Column(name = "attatchment")
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	@Column(name = "sender_id")
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	@Column(name = "sender_nickname")
	public String getSenderNickname() {
		return senderNickname;
	}
	public void setSenderNickname(String senderNickname) {
		this.senderNickname = senderNickname;
	}
	@Column(name = "reciever_id")
	public String getRecieverId() {
		return recieverId;
	}
	public void setRecieverId(String recieverId) {
		this.recieverId = recieverId;
	}
	@Column(name = "reciever_nickname")
	public String getRecieverNickname() {
		return recieverNickname;
	}
	public void setRecieverNickname(String recieverNickname) {
		this.recieverNickname = recieverNickname;
	}
	@Column(name = "create_time")
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}
