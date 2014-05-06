package com.demo.vo.dhgate.product;

import java.io.Serializable;
import java.util.List;

public class ProdAttr implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long attrId;
	private Integer isbrand;
	private List<ProdAttrVal> prodAttrValList;
	
	public Long getAttrId() {
		return attrId;
	}
	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}
	public Integer getIsbrand() {
		return isbrand;
	}
	public void setIsbrand(Integer isbrand) {
		this.isbrand = isbrand;
	}
	public List<ProdAttrVal> getProdAttrValList() {
		return prodAttrValList;
	}
	public void setProdAttrValList(List<ProdAttrVal> prodAttrValList) {
		this.prodAttrValList = prodAttrValList;
	}
	
}
