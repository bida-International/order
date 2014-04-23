package com.demo.entity.tools;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ��Ʒ״̬��
 *
 */
@Entity
@Table(name="product_status")
public class ProductStatus implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String dhId; // �ػͲ�Ʒ����
	private String dhUrl; // �ػͲ�Ʒҳ���ַ
	private Integer dhStatus; // �ػͲ�Ʒ״̬ 0.�¼� 1.�ϼ� 2.��ȡʧ��
	private String dhImgUrl; // �ػͲ�ƷͼƬ��ַ
	private String aliUrl; // ����ͨ��Ʒҳ���ַ
	private Integer aliStatus; // ����ͨ��Ʒ״̬: 0.�¼� 1.�ϼ� 2.��ȡʧ��
	private String aliImgUrl; // �ػͲ�ƷͼƬ��ַ
	private Long checkTime; // ���ʱ��
	private Long zhangHaoId; // �����˺�id
	private String zhangHaoAccount; // �����˺�����
	private Long createTime; // ��Ʒ���ʱ��
	private Integer statusFlag; // ״̬��ʶ(����״̬�Ƿ�ͬ��):  0.״̬��ͬ��, 1.״̬ͬ�� 2.״̬��ȡʧ��
	private Long synchTime; // �Զ�ͬ��ʱ��
	private Integer synchResult; // �Զ�ͬ�����: 0.ʧ�� 1.�ɹ�
	private String remark; // ��ע��Ϣ
	
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
