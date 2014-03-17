package com.demo.biz.track;

public class QueryTrackResult {

	private Integer ret; // 请求结果
	private Integer packageState; // 包裹状态
	private String latestTrackInfo; // 最新一条物流信息
	private String allTrackInfo; // 全部物流信息
	
	public Integer getRet() {
		return ret;
	}
	public void setRet(Integer ret) {
		this.ret = ret;
	}
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
