package com.demo.entity.tools;

import java.io.Serializable;
import java.util.Date;

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
	private Long sfexport;//是否导出 0或NULL 没有导出, 1.导出
	private String exporttime;//导出时间
	
	public String getExporttime() {
		return exporttime;
	}
	public void setExporttime(String exporttime) {
		this.exporttime = exporttime;
	}
	public Long getSfexport() {
		return sfexport;
	}
	public void setSfexport(Long sfexport) {
		this.sfexport = sfexport;
	}
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
	@Column(length = 500)
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
