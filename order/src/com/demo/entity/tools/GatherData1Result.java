package com.demo.entity.tools;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="gather_data1_result")
public class GatherData1Result implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long keyCreateTime;
	private String link; // 产品链接
	private Integer orderNum; // 订单数
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "key_create_time")
	public Long getKeyCreateTime() {
		return keyCreateTime;
	}
	public void setKeyCreateTime(Long keyCreateTime) {
		this.keyCreateTime = keyCreateTime;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	@Column(name = "order_num")
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
}
