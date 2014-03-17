package com.demo.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="xiexin")
public class XieXinTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//编号
	private Long userid;//收件人
	private String title;//标题
	private String neirong;//正文
	private String time;//发送时间
	private Long chakan;//是否查看 1.已经查看，null。未查看
	private String fajianren;//发件人
	public String getFajianren() {
		return fajianren;
	}
	public void setFajianren(String fajianren) {
		this.fajianren = fajianren;
	}
	public Long getChakan() {
		return chakan;
	}
	public void setChakan(Long chakan) {
		this.chakan = chakan;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNeirong() {
		return neirong;
	}
	public void setNeirong(String neirong) {
		this.neirong = neirong;
	}
	
}
