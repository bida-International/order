package com.demo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="kucun")
public class KuCunTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//编号
	private String biaoji;//标记号
	private Long num;//数量
	private Long userid;//采购员编号
	private String orderId;//订单号 
	private String wuping;//物品
	private String uploadFile;//图片
	private String coding;//编码 
	private Double totalprice;//总价
	private Double unitprice;//单价 
	private Date time;//上传时间
	private Date sytime;//使用时间
	private String transportproviders;//供运商
	public String getTransportproviders() {
		return transportproviders;
	}
	public void setTransportproviders(String transportproviders) {
		this.transportproviders = transportproviders;
	}
	public Date getSytime() {
		return sytime;
	}
	public void setSytime(Date sytime) {
		this.sytime = sytime;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getWuping() {
		return wuping;
	}
	public void setWuping(String wuping) {
		this.wuping = wuping;
	}

	public String getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(String uploadFile) {
		this.uploadFile = uploadFile;
	}
	public String getCoding() {
		return coding;
	}
	public void setCoding(String coding) {
		this.coding = coding;
	}

	public Double getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(Double totalprice) {
		this.totalprice = totalprice;
	}
	public Double getUnitprice() {
		return unitprice;
	}
	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBiaoji() {
		return biaoji;
	}
	public void setBiaoji(String biaoji) {
		this.biaoji = biaoji;
	}
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
}
