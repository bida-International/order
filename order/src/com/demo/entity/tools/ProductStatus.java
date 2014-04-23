package com.demo.entity.tools;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 产品状态表
 *
 */
@Entity
@Table(name="product_status")
public class ProductStatus implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String dhId; // 敦煌产品编码
	private String dhUrl; // 敦煌产品页面地址
	private Integer dhStatus; // 敦煌产品状态 0.下架 1.上架 2.获取失败
	private String dhImgUrl; // 敦煌产品图片地址
	private String aliUrl; // 速卖通产品页面地址
	private Integer aliStatus; // 速卖通产品状态: 0.下架 1.上架 2.获取失败
	private String aliImgUrl; // 敦煌产品图片地址
	private Long checkTime; // 检测时间
	private Long zhangHaoId; // 所属账号id
	private String zhangHaoAccount; // 所属账号名称
	private Long createTime; // 产品添加时间
	private Integer statusFlag; // 状态标识(两边状态是否同步):  0.状态不同步, 1.状态同步 2.状态获取失败
	private Long synchTime; // 自动同步时间
	private Integer synchResult; // 自动同步结果: 0.失败 1.成功
	private String remark; // 备注信息
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "dh_id")
	public String getDhId() {
		return dhId;
	}
	public void setDhId(String dhId) {
		this.dhId = dhId;
	}
	@Column(name = "dh_url", length = 500)
	public String getDhUrl() {
		return dhUrl;
	}
	public void setDhUrl(String dhUrl) {
		this.dhUrl = dhUrl;
	}
	@Column(name = "dh_status")
	public Integer getDhStatus() {
		return dhStatus;
	}
	public void setDhStatus(Integer dhStatus) {
		this.dhStatus = dhStatus;
	}
	@Column(name = "dh_img_url", length = 500)
	public String getDhImgUrl() {
		return dhImgUrl;
	}
	public void setDhImgUrl(String dhImgUrl) {
		this.dhImgUrl = dhImgUrl;
	}
	@Column(name = "ali_url", length = 500)
	public String getAliUrl() {
		return aliUrl;
	}
	public void setAliUrl(String aliUrl) {
		this.aliUrl = aliUrl;
	}
	@Column(name = "ali_status")
	public Integer getAliStatus() {
		return aliStatus;
	}
	public void setAliStatus(Integer aliStatus) {
		this.aliStatus = aliStatus;
	}
	@Column(name = "ali_img_url", length = 500)
	public String getAliImgUrl() {
		return aliImgUrl;
	}
	public void setAliImgUrl(String aliImgUrl) {
		this.aliImgUrl = aliImgUrl;
	}
	@Column(name = "check_time")
	public Long getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Long checkTime) {
		this.checkTime = checkTime;
	}
	@Column(name = "zhanghao_id")
	public Long getZhangHaoId() {
		return zhangHaoId;
	}
	public void setZhangHaoId(Long zhangHaoId) {
		this.zhangHaoId = zhangHaoId;
	}
	@Column(name = "zhanghao_account")
	public String getZhangHaoAccount() {
		return zhangHaoAccount;
	}
	public void setZhangHaoAccount(String zhangHaoAccount) {
		this.zhangHaoAccount = zhangHaoAccount;
	}

	@Column(name = "create_time")
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	@Column(name = "status_flag")
	public Integer getStatusFlag() {
		return statusFlag;
	}
	public void setStatusFlag(Integer statusFlag) {
		this.statusFlag = statusFlag;
	}
	@Column(name = "synch_time")
	public Long getSynchTime() {
		return synchTime;
	}
	public void setSynchTime(Long synchTime) {
		this.synchTime = synchTime;
	}
	@Column(name = "synch_result")
	public Integer getSynchResult() {
		return synchResult;
	}
	public void setSynchResult(Integer synchResult) {
		this.synchResult = synchResult;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
