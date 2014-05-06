package com.demo.vo.dhgate.product;

import java.io.Serializable;

public class ProdWholesaleRange implements Serializable {

	private static final long serialVersionUID = 1L;
	private Double discount;
	private Integer startQty;
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Integer getStartQty() {
		return startQty;
	}
	public void setStartQty(Integer startQty) {
		this.startQty = startQty;
	}
	
}
