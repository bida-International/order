package com.demo.biz.track;

public class QueryTrackResult {

	private Integer ret; // ������
	private Integer packageState; // ����״̬
	private String latestTrackInfo; // ����һ��������Ϣ
	private String allTrackInfo; // ȫ��������Ϣ
	
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
