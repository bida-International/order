package com.demo.entity.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="caigou")
public class CaiGou implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//编号
	private String name;//姓名
	private Long userid;//账户编号
	private Long quanxian;//权限
	private Long cgadminId;//采购管理员编号 
	
	public Long getCgadminId() {
		return cgadminId;
	}
	public void setCgadminId(Long cgadminId) {
		this.cgadminId = cgadminId;
	}
	public Long getQuanxian() {
		return quanxian;
	}
	public void setQuanxian(Long quanxian) {
		this.quanxian = quanxian;
	}
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
}
