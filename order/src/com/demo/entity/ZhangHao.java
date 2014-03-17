package com.demo.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="zhanghao")
public class ZhangHao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//账号编号
	private String name;//账号名
	private Long yewuId;//业务编号 
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
	private String dh_user_id; // 敦煌系统用户id (2014/2/22增加)
	private String dh_user_nickname; // 敦煌系统用户昵称 (2014/2/22增加)
	private Long bd_user_id; // 分配的必达用户id
	private String bd_user_name; // 分配的必达用户名称
	private Long refresh_token_timeout; // 单位: 毫秒
	private String user_id; // 第三方账号id
	private String user_nickname; // 第三方账号昵称
	private String account_type; // 账号类型: dh-敦煌账号; smt-速卖通账号 
	private String app_key; // 速卖通账号才需要
	private String app_secret; // 速卖通账号才需要 
	private Long order_update_time; // 订单数据最新同步时间
	private Long msg_update_time; // 站内信数据最新同步时间
	private Long ClipArtId;//美工编号
	public Long getClipArtId() {
		return ClipArtId;
	}
	public void setClipArtId(Long clipArtId) {
		ClipArtId = clipArtId;
	}
	public Long getRefresh_token_timeout() {
		return refresh_token_timeout;
	}
	public void setRefresh_token_timeout(Long refreshTokenTimeout) {
		refresh_token_timeout = refreshTokenTimeout;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String userId) {
		user_id = userId;
	}
	public String getUser_nickname() {
		return user_nickname;
	}
	public void setUser_nickname(String userNickname) {
		user_nickname = userNickname;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String accountType) {
		account_type = accountType;
	}
	public String getApp_key() {
		return app_key;
	}
	public void setApp_key(String appKey) {
		app_key = appKey;
	}
	public String getApp_secret() {
		return app_secret;
	}
	public void setApp_secret(String appSecret) {
		app_secret = appSecret;
	}
	public Long getBd_user_id() {
		return bd_user_id;
	}
	public void setBd_user_id(Long bdUserId) {
		bd_user_id = bdUserId;
	}
	public String getBd_user_name() {
		return bd_user_name;
	}
	public void setBd_user_name(String bdUserName) {
		bd_user_name = bdUserName;
	}
	public String getDh_user_id() {
		return dh_user_id;
	}
	public void setDh_user_id(String dhUserId) {
		dh_user_id = dhUserId;
	}
	public String getDh_user_nickname() {
		return dh_user_nickname;
	}
	public void setDh_user_nickname(String dhUserNickname) {
		dh_user_nickname = dhUserNickname;
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
	public Long getYewuId() {
		return yewuId;
	}
	public void setYewuId(Long yewuId) {
		this.yewuId = yewuId;
	}
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getOrder_update_time() {
		return order_update_time;
	}
	public void setOrder_update_time(Long order_update_time) {
		this.order_update_time = order_update_time;
	}
	public Long getMsg_update_time() {
		return msg_update_time;
	}
	public void setMsg_update_time(Long msg_update_time) {
		this.msg_update_time = msg_update_time;
	}

}
