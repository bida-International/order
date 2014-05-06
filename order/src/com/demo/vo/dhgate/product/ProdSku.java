package com.demo.vo.dhgate.product;

import java.io.Serializable;
import java.util.List;

public class ProdSku implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer inventory;
	private Double retailPrice;
	private Integer saleStatus;
	private String skuCode;
	private List<ProdSkuAttrval> prodSkuAttrvalList;
	
	public Integer getInventory() {
		return inventory;
	}
	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}
	public Double getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}
	public Integer getSaleStatus() {
		return saleStatus;
	}
	public void setSaleStatus(Integer saleStatus) {
		this.saleStatus = saleStatus;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public List<ProdSkuAttrval> getProdSkuAttrvalList() {
		return prodSkuAttrvalList;
	}
	public void setProdSkuAttrvalList(List<ProdSkuAttrval> prodSkuAttrvalList) {
		this.prodSkuAttrvalList = prodSkuAttrvalList;
	}
}
