package com.demo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="documenttable")
public class DocumentTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//编号
	private Long Number;//序号
	private String title;//内容
	private Date time;// 时间
	private Long Complete;//文档完成
	private Long GetDocuments;//得到文档
	private Long Audit;//审核
	private Long TechnicianId;//技术员编号 
	public Long getTechnicianId() {
		return TechnicianId;
	}
	public void setTechnicianId(Long technicianId) {
		TechnicianId = technicianId;
	}
	public Long getComplete() {
		return Complete;
	}
	public void setComplete(Long complete) {
		Complete = complete;
	}
	public Long getGetDocuments() {
		return GetDocuments;
	}
	public void setGetDocuments(Long getDocuments) {
		GetDocuments = getDocuments;
	}
	public Long getAudit() {
		return Audit;
	}
	public void setAudit(Long audit) {
		Audit = audit;
	}
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getNumber() {
		return Number;
	}
	public void setNumber(Long number) {
		Number = number;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
}
