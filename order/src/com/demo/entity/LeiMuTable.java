package com.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="leimu")
public class LeiMuTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//编号
	private String leimu;//类目
	private Long userid;//采购员编号
	private String cateId; // 敦煌网的类目Id
	private Long msgFenpeiUserId; // 站内信分配的（业务）用户id 
	private String msgFenpeiUserName; // 站内信分配的（业务）用户名称
	private String ali_leimu;//速卖通类目
	private String aliCateId; // 速卖通类目
	
	public String getAli_leimu() {
		return ali_leimu;
	}
	public void setAli_leimu(String aliLeimu) {
		ali_leimu = aliLeimu;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLeimu() {
		return leimu;
	}
	public void setLeimu(String leimu) {
		this.leimu = leimu;
	}
	@Column(name = "cate_id")
	public String getCateId() {
		return cateId;
	}
	public void setCateId(String cateId) {
		this.cateId = cateId;
	}
	@Column(name = "msg_fenpei_user_id")
	public Long getMsgFenpeiUserId() {
		return msgFenpeiUserId;
	}
	public void setMsgFenpeiUserId(Long msgFenpeiUserId) {
		this.msgFenpeiUserId = msgFenpeiUserId;
	}
	@Column(name = "msg_fenpei_user_name")
	public String getMsgFenpeiUserName() {
		return msgFenpeiUserName;
	}
	public void setMsgFenpeiUserName(String msgFenpeiUserName) {
		this.msgFenpeiUserName = msgFenpeiUserName;
	}
	@Column(name = "ali_cate_id")
	public String getAliCateId() {
		return aliCateId;
	}
	public void setAliCateId(String aliCateId) {
		this.aliCateId = aliCateId;
	}
	
}
