package com.demo.entity.Courier;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="yunfei")
public class YunFeiTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//���
	private Long quyu;//����
	private String guojia;//����
	private Double money;//�˷ѽ��
	private String fenqu;//����
	private String zimu;//��ĸ
	private String Corresponding;//��Ӧ��
	public String getCorresponding() {
		return Corresponding;
	}
	public void setCorresponding(String corresponding) {
		Corresponding = corresponding;
	}
	public String getZimu() {
		return zimu;
	}
	public void setZimu(String zimu) {
		this.zimu = zimu;
	}
	public String getFenqu() {
		return fenqu;
	}
	public void setFenqu(String fenqu) {
		this.fenqu = fenqu;
	}
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Long getQuyu() {
		return quyu;
	}
	public void setQuyu(Long quyu) {
		this.quyu = quyu;
	}
}
