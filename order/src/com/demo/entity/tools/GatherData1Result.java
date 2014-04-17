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
	private String link; // ��Ʒ����
	private Integer orderNum; // ������
	private Long sfexport;//�Ƿ񵼳� 0��NULL û�е���, 1.����
	private String exporttime;//����ʱ��
	
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
