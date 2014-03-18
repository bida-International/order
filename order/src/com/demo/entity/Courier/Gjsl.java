package com.demo.entity.Courier;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="gjsl")
public class Gjsl implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//编号
	private Double zhongliang;//重量
	private Double zifei;//资费
	private String kuaidi;//快递
	public String getKuaidi() {
		return kuaidi;
	}
	public void setKuaidi(String kuaidi) {
		this.kuaidi = kuaidi;
	}
	public Double getZhongliang() {
		return zhongliang;
	}
	public void setZhongliang(Double zhongliang) {
		this.zhongliang = zhongliang;
	}
	public Double getZifei() {
		return zifei;
	}
	public void setZifei(Double zifei) {
		this.zifei = zifei;
	}
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
