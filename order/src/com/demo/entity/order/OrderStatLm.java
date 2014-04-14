package com.demo.entity.order;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ��Ŀ����ͳ������
 *
 */
@Entity
@Table(name="order_stat_lm")
public class OrderStatLm implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long leimuId; // ͳ�Ƶ���Ŀid
	private String leimuName; // ͳ�Ƶ��˺�����
	private Integer statType; // ͳ������: 1.��, 2.��, 3.��
	private Integer statYear; // ͳ�Ƶ��� (�����ϴ�ʱ��)
	private Integer statWeek; // ͳ�Ƶ��� (�����ϴ�ʱ��) statType��Ϊ2ʱ, ��null
	private Integer statMonth; // ͳ�Ƶ��� (�����ϴ�ʱ��) statType��Ϊ3ʱ, ��null
	private Date statDate; // ͳ�Ƶ����� (�����ϴ�ʱ��), statType��Ϊ1ʱ,��null
	private Date statBeginDate; // ͳ�ƿ�ʼ���� (�����ϴ�ʱ��)
	private Date statEndDate; // ͳ�ƽ������� (�����ϴ�ʱ��)
	private Double totalMoney; // �����ܽ��
	private Integer orderAmount; // ��������
	private Integer jiufenAmount; // ���׶�����
	private Double jiufenRate; // ������
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "leimu_id")
	public Long getLeimuId() {
		return leimuId;
	}
	public void setLeimuId(Long leimuId) {
		this.leimuId = leimuId;
	}
	@Column(name = "leimu_name")
	public String getLeimuName() {
		return leimuName;
	}
	public void setLeimuName(String leimuName) {
		this.leimuName = leimuName;
	}
	@Column(name = "stat_type")
	public Integer getStatType() {
		return statType;
	}
	public void setStatType(Integer statType) {
		this.statType = statType;
	}
	@Column(name = "stat_year")
	public Integer getStatYear() {
		return statYear;
	}
	public void setStatYear(Integer statYear) {
		this.statYear = statYear;
	}
	@Column(name = "stat_week")
	public Integer getStatWeek() {
		return statWeek;
	}
	public void setStatWeek(Integer statWeek) {
		this.statWeek = statWeek;
	}
	@Column(name = "stat_month")
	public Integer getStatMonth() {
		return statMonth;
	}
	public void setStatMonth(Integer statMonth) {
		this.statMonth = statMonth;
	}
	@Column(name = "stat_date")
	public Date getStatDate() {
		return statDate;
	}
	public void setStatDate(Date statDate) {
		this.statDate = statDate;
	}
	@Column(name = "stat_begin_date")
	public Date getStatBeginDate() {
		return statBeginDate;
	}
	public void setStatBeginDate(Date statBeginDate) {
		this.statBeginDate = statBeginDate;
	}
	@Column(name = "stat_end_date")
	public Date getStatEndDate() {
		return statEndDate;
	}
	public void setStatEndDate(Date statEndDate) {
		this.statEndDate = statEndDate;
	}
	@Column(name = "total_money")
	public Double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}
	@Column(name = "order_amount")
	public Integer getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}
	@Column(name = "jiufen_amount")
	public Integer getJiufenAmount() {
		return jiufenAmount;
	}
	public void setJiufenAmount(Integer jiufenAmount) {
		this.jiufenAmount = jiufenAmount;
	}
	@Column(name = "jiufen_rate")
	public Double getJiufenRate() {
		return jiufenRate;
	}
	public void setJiufenRate(Double jiufenRate) {
		this.jiufenRate = jiufenRate;
	}
}
