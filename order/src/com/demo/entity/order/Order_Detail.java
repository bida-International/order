/*jadclipse*/// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   OrderTable.java

package com.demo.entity.order;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.struts2.util.SubsetIteratorFilter.Decider;

import oracle.sql.CHAR;
import sun.security.util.BigInt;
@Entity
@Table(name="order_detail")
public class Order_Detail implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Long id;//编号
    private String order_sn;
    private String orderId;
    private String itemcode;
    private String itemname;
    private Long quantity;
    private Double targetprice;
    private String shippingtype;
    private Double shipcost;
    private String cat_name;
    private String skuCode;
    private Date time;
    private Long sfModify;//是否被修改类目0.未修改1.已经修改
	public Long getSfModify() {
		return sfModify;
	}
	public void setSfModify(Long sfModify) {
		this.sfModify = sfModify;
	}
	@Id
    @GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getOrder_sn() {
		return order_sn;
	}
	public void setOrder_sn(String orderSn) {
		order_sn = orderSn;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getItemcode() {
		return itemcode;
	}
	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Double getTargetprice() {
		return targetprice;
	}
	public void setTargetprice(Double targetprice) {
		this.targetprice = targetprice;
	}
	public String getShippingtype() {
		return shippingtype;
	}
	public void setShippingtype(String shippingtype) {
		this.shippingtype = shippingtype;
	}
	public Double getShipcost() {
		return shipcost;
	}
	public void setShipcost(Double shipcost) {
		this.shipcost = shipcost;
	}
	public String getCat_name() {
		return cat_name;
	}
	public void setCat_name(String catName) {
		cat_name = catName;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	} 
}
