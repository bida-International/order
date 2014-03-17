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
@Table(name="order")
public class Order implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Long id;//±àºÅ
    private Long order_type;
    private CHAR ordersn;
    private String orderId;
    private Double amount;
    private Double yunfei;
    private String shippingtype;
    private Double total;
    private Long paydate;
    private CHAR country;
    private CHAR province;
    private CHAR city;
    private String address1;
    private String address2;
    private CHAR postcode;
    private CHAR telephone;
    private CHAR cellphone;
    private CHAR receiver;
    private Long status;
    private Date time;
	@Id
    @GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrder_type() {
		return order_type;
	}
	public void setOrder_type(Long orderType) {
		order_type = orderType;
	}
	public CHAR getOrdersn() {
		return ordersn;
	}
	public void setOrdersn(CHAR ordersn) {
		this.ordersn = ordersn;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getYunfei() {
		return yunfei;
	}
	public void setYunfei(Double yunfei) {
		this.yunfei = yunfei;
	}
	public String getShippingtype() {
		return shippingtype;
	}
	public void setShippingtype(String shippingtype) {
		this.shippingtype = shippingtype;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Long getPaydate() {
		return paydate;
	}
	public void setPaydate(Long paydate) {
		this.paydate = paydate;
	}
	public CHAR getCountry() {
		return country;
	}
	public void setCountry(CHAR country) {
		this.country = country;
	}
	public CHAR getProvince() {
		return province;
	}
	public void setProvince(CHAR province) {
		this.province = province;
	}
	public CHAR getCity() {
		return city;
	}
	public void setCity(CHAR city) {
		this.city = city;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public CHAR getPostcode() {
		return postcode;
	}
	public void setPostcode(CHAR postcode) {
		this.postcode = postcode;
	}
	public CHAR getTelephone() {
		return telephone;
	}
	public void setTelephone(CHAR telephone) {
		this.telephone = telephone;
	}
	public CHAR getCellphone() {
		return cellphone;
	}
	public void setCellphone(CHAR cellphone) {
		this.cellphone = cellphone;
	}
	public CHAR getReceiver() {
		return receiver;
	}
	public void setReceiver(CHAR receiver) {
		this.receiver = receiver;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
 
}
