package com.demo.biz.track;

import java.io.Serializable;

public class QueryTrackResult implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer ret; // ������
	private Integer packageState; // ����״̬
	private String latestTrackInfo; // ����һ��������Ϣ
	private String allTrackInfo; // ȫ��������Ϣ
	
	/** ��ѯ״̬:(0)�޷�����,(1)������������Ϣ,(2)������������Ϣ,(3)��վ����,
	 * (4)�������,(5)��ѯ��������ʹ�û������,(6)�Է�������æ,(7)�Է�������æʹ�û������
	 */
	public Integer getRet() {
		return ret;
	}
	public void setRet(Integer ret) {
		this.ret = ret;
	}
	/** ����״̬: 0.��ѯ����;1.����;��;2.�����ȡ;3.Delivered */
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
