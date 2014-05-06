package com.demo.entity.tools;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 产品图片上传日志
 *
 */
@Entity
@Table(name = "prod_img_upload_log")
public class ProdImgUploadLog implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String aliImgUrl;
	private String dhImgUrl;
	private String dhImgMd5;
	private Long zhangHaoId;
	private Long uploadTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAliImgUrl() {
		return aliImgUrl;
	}
	public void setAliImgUrl(String aliImgUrl) {
		this.aliImgUrl = aliImgUrl;
	}
	public String getDhImgUrl() {
		return dhImgUrl;
	}
	public void setDhImgUrl(String dhImgUrl) {
		this.dhImgUrl = dhImgUrl;
	}
	public String getDhImgMd5() {
		return dhImgMd5;
	}
	public void setDhImgMd5(String dhImgMd5) {
		this.dhImgMd5 = dhImgMd5;
	}
	public Long getZhangHaoId() {
		return zhangHaoId;
	}
	public void setZhangHaoId(Long zhangHaoId) {
		this.zhangHaoId = zhangHaoId;
	}
	public Long getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Long uploadTime) {
		this.uploadTime = uploadTime;
	}
}
