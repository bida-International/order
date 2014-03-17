package com.demo.entity.order;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="dhgateAccounts")
public class DhgateAccounts implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//±àºÅ
	private String user_access_token;
	private String ea_code;
	private String account;
	private String pass;
	private String access_token;
	private Long expires_in;
	private String refresh_token;
	private String scope;
	private Long status;
	private Long update_time;
	private Date create_time;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUser_access_token() {
		return user_access_token;
	}
	public void setUser_access_token(String userAccessToken) {
		user_access_token = userAccessToken;
	}
	public String getEa_code() {
		return ea_code;
	}
	public void setEa_code(String eaCode) {
		ea_code = eaCode;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String accessToken) {
		access_token = accessToken;
	}
	public Long getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(Long expiresIn) {
		expires_in = expiresIn;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refreshToken) {
		refresh_token = refreshToken;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Long updateTime) {
		update_time = updateTime;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}
}
