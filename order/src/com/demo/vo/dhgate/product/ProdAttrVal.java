package com.demo.vo.dhgate.product;

import java.io.Serializable;

public class ProdAttrVal implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long attrId;
	private String attrName;
	private Long attrValId;
	private String lineAttrvalName;
	private String lineAttrvalNameCn;
	private String picUrl;
	
	public Long getAttrId() {
		return attrId;
	}
	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public Long getAttrValId() {
		return attrValId;
	}
	public void setAttrValId(Long attrValId) {
		this.attrValId = attrValId;
	}
	public String getLineAttrvalName() {
		return lineAttrvalName;
	}
	public void setLineAttrvalName(String lineAttrvalName) {
		this.lineAttrvalName = lineAttrvalName;
	}
	public String getLineAttrvalNameCn() {
		return lineAttrvalNameCn;
	}
	public void setLineAttrvalNameCn(String lineAttrvalNameCn) {
		this.lineAttrvalNameCn = lineAttrvalNameCn;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
}
