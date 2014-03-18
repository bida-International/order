package com.demo.entity.Courier;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="hydb")
public class Hydb implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//编号
	private Double money;//价格
	private Double xuz;//续重 
	private Double xz;//限重
	private Double zhongliang;//重量
	private String guojia;//国家
	private String kuaidi;//快递
	
	public String getKuaidi() {
		return kuaidi;
	}
	public void setKuaidi(String kuaidi) {
		this.kuaidi = kuaidi;
	}
	public String getGuojia() {
		return guojia;
	}
	public void setGuojia(String guojia) {
		this.guojia = guojia;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Double getXuz() {
		return xuz;
	}
	public void setXuz(Double xuz) {
		this.xuz = xuz;
	}
	public Double getXz() {
		return xz;
	}
	public void setXz(Double xz) {
		this.xz = xz;
	}
	public Double getZhongliang() {
		return zhongliang;
	}
	public void setZhongliang(Double zhongliang) {
		this.zhongliang = zhongliang;
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
