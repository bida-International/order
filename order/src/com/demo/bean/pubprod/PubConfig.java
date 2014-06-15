package com.demo.bean.pubprod;

import java.io.Serializable;

public class PubConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	private String prodNamePrefix;
	private String prodNameSuffix;
	private String prodNameOrgin;
	private String prodNameReplaced;
	private String prodDescPrefix;
	private String prodDescSuffix;
	private String prodDescOrgin;
	private String prodDescReplaced;
	private String prodBulkPriceSet;
	public String getProdNamePrefix() {
		return prodNamePrefix;
	}
	public void setProdNamePrefix(String prodNamePrefix) {
		this.prodNamePrefix = prodNamePrefix;
	}
	public String getProdNameSuffix() {
		return prodNameSuffix;
	}
	public void setProdNameSuffix(String prodNameSuffix) {
		this.prodNameSuffix = prodNameSuffix;
	}
	public String getProdNameOrgin() {
		return prodNameOrgin;
	}
	public void setProdNameOrgin(String prodNameOrgin) {
		this.prodNameOrgin = prodNameOrgin;
	}
	public String getProdNameReplaced() {
		return prodNameReplaced;
	}
	public void setProdNameReplaced(String prodNameReplaced) {
		this.prodNameReplaced = prodNameReplaced;
	}
	public String getProdDescPrefix() {
		return prodDescPrefix;
	}
	public void setProdDescPrefix(String prodDescPrefix) {
		this.prodDescPrefix = prodDescPrefix;
	}
	public String getProdDescSuffix() {
		return prodDescSuffix;
	}
	public void setProdDescSuffix(String prodDescSuffix) {
		this.prodDescSuffix = prodDescSuffix;
	}
	public String getProdDescOrgin() {
		return prodDescOrgin;
	}
	public void setProdDescOrgin(String prodDescOrgin) {
		this.prodDescOrgin = prodDescOrgin;
	}
	public String getProdDescReplaced() {
		return prodDescReplaced;
	}
	public void setProdDescReplaced(String prodDescReplaced) {
		this.prodDescReplaced = prodDescReplaced;
	}
	public String getProdBulkPriceSet() {
		return prodBulkPriceSet;
	}
	public void setProdBulkPriceSet(String prodBulkPriceSet) {
		this.prodBulkPriceSet = prodBulkPriceSet;
	}
	
}
