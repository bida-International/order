package com.demo.entity.order;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 物流单号查询记录
 *
 */
@Entity
@Table(name="danhao_track")
public class DanhaoTrack implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String danhao; // 物流单号
	private Integer packageState; // 包裹状态
	private String latestTrackInfo; // 最新一条物流信息
	private String allTrackInfo; // 全部物流信息
	private Date queryTime;
	private String orderId; // 订单ID
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDanhao() {
		return danhao;
	}
	public void setDanhao(String danhao) {
		this.danhao = danhao;
	}
	/** 包裹状态: 0.查询不到;1.运输途中;2.到达待取;3.Delivered */
	public Integer getPackageState() {
		return packageState;
	}
	public void setPackageState(Integer packageState) {
		this.packageState = packageState;
	}
	/** 最新一条物流信息 */
	public String getLatestTrackInfo() {
		return latestTrackInfo;
	}
	public void setLatestTrackInfo(String latestTrackInfo) {
		this.latestTrackInfo = latestTrackInfo;
	}
	/** 全部物流信息 */
	public String getAllTrackInfo() {
		return allTrackInfo;
	}
	public void setAllTrackInfo(String allTrackInfo) {
		this.allTrackInfo = allTrackInfo;
	}
	public Date getQueryTime() {
		return queryTime;
	}
	public void setQueryTime(Date queryTime) {
		this.queryTime = queryTime;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
