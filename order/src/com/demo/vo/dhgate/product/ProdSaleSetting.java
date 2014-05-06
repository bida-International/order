package com.demo.vo.dhgate.product;

import java.io.Serializable;

public class ProdSaleSetting implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer leadingTime;
	private Integer maxSaleQty;
	private Integer priceConfigType;
	
	public Integer getLeadingTime() {
		return leadingTime;
	}
	public void setLeadingTime(Integer leadingTime) {
		this.leadingTime = leadingTime;
	}
	public Integer getMaxSaleQty() {
		return maxSaleQty;
	}
	public void setMaxSaleQty(Integer maxSaleQty) {
		this.maxSaleQty = maxSaleQty;
	}
	public Integer getPriceConfigType() {
		return priceConfigType;
	}
	public void setPriceConfigType(Integer priceConfigType) {
		this.priceConfigType = priceConfigType;
	}
	
}
