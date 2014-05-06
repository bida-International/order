package com.demo.vo.dhgate.product;

import java.io.Serializable;

public class ProdSpecSelfDef implements Serializable {

	private static final long serialVersionUID = 1L;

	private String attrValName;
	private String picUrl;
	
	public String getAttrValName() {
		return attrValName;
	}
	public void setAttrValName(String attrValName) {
		this.attrValName = attrValName;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
}
