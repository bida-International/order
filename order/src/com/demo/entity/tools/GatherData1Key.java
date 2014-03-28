package com.demo.entity.tools;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="gather_data1_key")
public class GatherData1Key implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String gatherKey; // 采集地址
	private String queryParam; // 采集条件
	private Long createTime;
	private Long updateTime; 
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "gather_key", length=500)
	public String getGatherKey() {
		return gatherKey;
	}
	public void setGatherKey(String gatherKey) {
		this.gatherKey = gatherKey;
	}
	@Column(name = "query_param")
	public String getQueryParam() {
		return queryParam;
	}
	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}
	@Column(name = "update_time")
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "create_time")
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}
