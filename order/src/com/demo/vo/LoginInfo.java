package com.demo.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.demo.entity.user.CaiGou;
import com.demo.entity.user.CaiGouAdmin;
import com.demo.entity.user.ClipArt;

/**
 * 当前用户登录的信息
 * @author Administrator
 *
 */
public class LoginInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//帐号
	private String account;
	//姓名
	private String name;
	//帐户编号
	private Long userId;
	//角色:1.管理员,2.学员,3.教员,4.采购管理员
	private Integer role;
	//业务助理编号
	private Long YeWuId;
	//采购编号
	private Long CaiGouId;
	//采购管理员
	private Long CaiGouAdminId;
    private Long gukeId;//顾客编号
    private Long CangKuYuanId;//仓库员编号
    private Long caiwuId;//财务编号
    //业务编号
    private Long businessId;//业务编号
	//登录时间
	private Timestamp loginTime;
	private List<CaiGou> caigou;//采购
	private List<CaiGouAdmin> caiGouAdmin;//采购管理员
	private Long clipArtId;//美工
	private Long TechnicianId;//美工
	//登录IP
	private String ip;
	private String pwd;//密码
	private Long id;//用户编号
	private Long yewu1Id;//业务编号
	private Long psa;//采购助理
	
	public Long getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	public Long getPsa() {
		return psa;
	}
	public void setPsa(Long psa) {
		this.psa = psa;
	}
	public Long getTechnicianId() {
		return TechnicianId;
	}
	public void setTechnicianId(Long technicianId) {
		TechnicianId = technicianId;
	}
	public Long getClipArtId() {
		return clipArtId;
	}
	public void setClipArtId(Long clipArtId) {
		this.clipArtId = clipArtId;
	}
	public Long getYewu1Id() {
		return yewu1Id;
	}
	public void setYewu1Id(Long yewu1Id) {
		this.yewu1Id = yewu1Id;
	}
	public Long getCaiwuId() {
		return caiwuId;
	}
	public void setCaiwuId(Long caiwuId) {
		this.caiwuId = caiwuId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<CaiGouAdmin> getCaiGouAdmin() {
		return caiGouAdmin;
	}
	public void setCaiGouAdmin(List<CaiGouAdmin> caiGouAdmin) {
		this.caiGouAdmin = caiGouAdmin;
	}
	public List<CaiGou> getCaigou() {
		return caigou;
	}
	public void setCaigou(List<CaiGou> caigou) {
		this.caigou = caigou;
	}
	public Long getGukeId() {
		return gukeId;
	}
	public void setGukeId(Long gukeId) {
		this.gukeId = gukeId;
	}
	public Long getCangKuYuanId() {
		return CangKuYuanId;
	}
	public void setCangKuYuanId(Long cangKuYuanId) {
		CangKuYuanId = cangKuYuanId;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public Long getYeWuId() {
		return YeWuId;
	}
	public void setYeWuId(Long yeWuId) {
		YeWuId = yeWuId;
	}
	public Long getCaiGouId() {
		return CaiGouId;
	}
	public void setCaiGouId(Long caiGouId) {
		CaiGouId = caiGouId;
	}
	public Long getCaiGouAdminId() {
		return CaiGouAdminId;
	}
	public void setCaiGouAdminId(Long caiGouAdminId) {
		CaiGouAdminId = caiGouAdminId;
	}
	public Timestamp getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
