package com.demo.vo.dhgate;

import java.io.Serializable;
import java.util.List;

import com.demo.vo.dhgate.product.ProdAttach;
import com.demo.vo.dhgate.product.ProdAttr;
import com.demo.vo.dhgate.product.ProdBase;
import com.demo.vo.dhgate.product.ProdInventory;
import com.demo.vo.dhgate.product.ProdPackage;
import com.demo.vo.dhgate.product.ProdSaleSetting;
import com.demo.vo.dhgate.product.ProdSku;
import com.demo.vo.dhgate.product.ProdSpecSelfDef;
import com.demo.vo.dhgate.product.ProdWholesaleRange;

public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	private String catePubId;
	private String imageMd5;
	private String imageUrl;
	private String productGroupId;
	private String shippingModelId;
	private String supplierId;
	private Integer vaildday;
	
	private List<ProdAttach> prodAttachList;
	private List<ProdAttr> prodAttrList;
	private ProdBase prodBase;
	private ProdInventory prodInventory;
	private ProdPackage prodPackage;
	private ProdSaleSetting prodSaleSetting;
	private List<ProdSku> prodSkuList;
	private List<ProdSpecSelfDef> prodSpecSelfDefList;
	private List<ProdWholesaleRange> prodWholesaleRangeList;
	
	public String getCatePubId() {
		return catePubId;
	}
	public void setCatePubId(String catePubId) {
		this.catePubId = catePubId;
	}
	public String getImageMd5() {
		return imageMd5;
	}
	public void setImageMd5(String imageMd5) {
		this.imageMd5 = imageMd5;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getProductGroupId() {
		return productGroupId;
	}
	public void setProductGroupId(String productGroupId) {
		this.productGroupId = productGroupId;
	}
	public String getShippingModelId() {
		return shippingModelId;
	}
	public void setShippingModelId(String shippingModelId) {
		this.shippingModelId = shippingModelId;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public Integer getVaildday() {
		return vaildday;
	}
	public void setVaildday(Integer vaildday) {
		this.vaildday = vaildday;
	}
	public List<ProdAttach> getProdAttachList() {
		return prodAttachList;
	}
	public void setProdAttachList(List<ProdAttach> prodAttachList) {
		this.prodAttachList = prodAttachList;
	}
	public List<ProdAttr> getProdAttrList() {
		return prodAttrList;
	}
	public void setProdAttrList(List<ProdAttr> prodAttrList) {
		this.prodAttrList = prodAttrList;
	}
	public ProdBase getProdBase() {
		return prodBase;
	}
	public void setProdBase(ProdBase prodBase) {
		this.prodBase = prodBase;
	}
	public ProdInventory getProdInventory() {
		return prodInventory;
	}
	public void setProdInventory(ProdInventory prodInventory) {
		this.prodInventory = prodInventory;
	}
	public ProdPackage getProdPackage() {
		return prodPackage;
	}
	public void setProdPackage(ProdPackage prodPackage) {
		this.prodPackage = prodPackage;
	}
	public ProdSaleSetting getProdSaleSetting() {
		return prodSaleSetting;
	}
	public void setProdSaleSetting(ProdSaleSetting prodSaleSetting) {
		this.prodSaleSetting = prodSaleSetting;
	}
	public List<ProdSku> getProdSkuList() {
		return prodSkuList;
	}
	public void setProdSkuList(List<ProdSku> prodSkuList) {
		this.prodSkuList = prodSkuList;
	}
	public List<ProdSpecSelfDef> getProdSpecSelfDefList() {
		return prodSpecSelfDefList;
	}
	public void setProdSpecSelfDefList(List<ProdSpecSelfDef> prodSpecSelfDefList) {
		this.prodSpecSelfDefList = prodSpecSelfDefList;
	}
	public List<ProdWholesaleRange> getProdWholesaleRangeList() {
		return prodWholesaleRangeList;
	}
	public void setProdWholesaleRangeList(
			List<ProdWholesaleRange> prodWholesaleRangeList) {
		this.prodWholesaleRangeList = prodWholesaleRangeList;
	}
	
}
