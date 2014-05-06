package com.demo.vo.dhgate.product;

import java.io.Serializable;

public class ProdSkuAttrval implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer attrId;
	private Integer attrValId;
	private Integer sizeSpecType;
	
	public Integer getAttrId() {
		return attrId;
	}
	public void setAttrId(Integer attrId) {
		this.attrId = attrId;
	}
	public Integer getAttrValId() {
		return attrValId;
	}
	public void setAttrValId(Integer attrValId) {
		this.attrValId = attrValId;
	}
	public Integer getSizeSpecType() {
		return sizeSpecType;
	}
	public void setSizeSpecType(Integer sizeSpecType) {
		this.sizeSpecType = sizeSpecType;
	}
	
}
