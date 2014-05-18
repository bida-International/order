package com.demo.entity.tools;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prod_publish_log")
public class ProdPublishLog implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	private String aliUrl;
	private Integer lineNum;
	private Long zhangHaoId;
	private String zhangHaoAccount;
	private String sessionId;
	private Integer result;
	private String msg;
	private Long createTime;


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/** ����ͨ��Ʒ��ַ */
	@Column(name = "ali_url")
	public String getAliUrl() {
		return aliUrl;
	}
	public void setAliUrl(String aliUrl) {
		this.aliUrl = aliUrl;
	}
	/** ���ڵ����ļ����к� */
	@Column(name = "line_num")
	public Integer getLineNum() {
		return lineNum;
	}
	public void setLineNum(Integer lineNum) {
		this.lineNum = lineNum;
	}
	@Column(name = "zhang_hao_id")
	public Long getZhangHaoId() {
		return zhangHaoId;
	}
	public void setZhangHaoId(Long zhangHaoId) {
		this.zhangHaoId = zhangHaoId;
	}
	@Column(name = "zhang_hao_account")
	public String getZhangHaoAccount() {
		return zhangHaoAccount;
	}
	public void setZhangHaoAccount(String zhangHaoAccount) {
		this.zhangHaoAccount = zhangHaoAccount;
	}
	@Column(name = "session_id")
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	/** �������: 0.ʧ��; 1.�ɹ� */
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	/** ʧ��������Ϣ */
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/** ����ʱ�� */
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

}