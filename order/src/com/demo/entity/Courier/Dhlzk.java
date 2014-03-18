package com.demo.entity.Courier;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="dhlzk")
public class Dhlzk implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//╠Ю╨е
	private Double zk;//уш©ш
	private Double ly;//х╪см
	
	public Double getZk() {
		return zk;
	}
	public void setZk(Double zk) {
		this.zk = zk;
	}
	public Double getLy() {
		return ly;
	}
	public void setLy(Double ly) {
		this.ly = ly;
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
