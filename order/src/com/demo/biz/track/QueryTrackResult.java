package com.demo.biz.track;

import java.io.Serializable;

public class QueryTrackResult implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer ret; // 请求结果
	private Integer packageState; // 包裹状态
	private String latestTrackInfo; // 最新一条物流信息
	private String allTrackInfo; // 全部物流信息
	
	/** 查询状态:(0)无法跟踪,(1)正常并查有信息,(2)正常但尚无信息,(3)网站错误,
	 * (4)处理错误,(5)查询发生错误使用缓存对象,(6)对方服务器忙,(7)对方服务器忙使用缓存对象
	 */
	public Integer getRet() {
		return ret;
	}
	public void setRet(Integer ret) {
		this.ret = ret;
	}
	/** 包裹状态: 0.查询不到;1.运输途中;2.到达待取;3.Delivered */
	public Integer getPackageState() {
		return packageState;
	}
	public void setPackageState(Integer packageState) {
		this.packageState = packageState;
	}
	public String getLatestTrackInfo() {
		return latestTrackInfo;
	}
	public void setLatestTrackInfo(String latestTrackInfo) {
		this.latestTrackInfo = latestTrackInfo;
	}
	public String getAllTrackInfo() {
		return allTrackInfo;
	}
	public void setAllTrackInfo(String allTrackInfo) {
		this.allTrackInfo = allTrackInfo;
	}
	
}
