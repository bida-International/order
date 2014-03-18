package com.demo.entity.Courier;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="emszf")
public class EmsZf implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//编号
	private Double money;//价格
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
	public Double getXuzhong() {
		return xuzhong;
	}
	public void setXuzhong(Double xuzhong) {
		this.xuzhong = xuzhong;
	}
	private Double xuzhong;//续重
	private Long quyu;//区域
	
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Double getZhongliang() {
		return zhongliang;
	}
	public void setZhongliang(Double zhongliang) {
		this.zhongliang = zhongliang;
	}
	public Long getQuyu() {
		return quyu;
	}
	public void setQuyu(Long quyu) {
		this.quyu = quyu;
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
