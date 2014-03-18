package com.demo.entity.Courier;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="fedexiegbj")
public class FedExIeGbj implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//���
	private Double money;//�۸�
	private Double zhongliang;//����
	private String quyu;//����
	private String kuaidi;//���
	public String getKuaidi() {
		return kuaidi;
	}
	public void setKuaidi(String kuaidi) {
		this.kuaidi = kuaidi;
	}
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
	public String getQuyu() {
		return quyu;
	}
	public void setQuyu(String quyu) {
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
