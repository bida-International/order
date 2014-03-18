package com.demo.entity.Courier;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="kygbj")
public class Kygbj implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//���
	private Double zifei;//�ʷ�
	private Double zl;//���� 
	private Long zu;//�ڼ���
	private String kuaidi;//���
	public String getKuaidi() {
		return kuaidi;
	}
	public void setKuaidi(String kuaidi) {
		this.kuaidi = kuaidi;
	}
	public Double getZifei() {
		return zifei;
	}
	public void setZifei(Double zifei) {
		this.zifei = zifei;
	}
	public Double getZl() {
		return zl;
	}
	public void setZl(Double zl) {
		this.zl = zl;
	}
	public Long getZu() {
		return zu;
	}
	public void setZu(Long zu) {
		this.zu = zu;
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
