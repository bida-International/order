package com.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ����ͨ��Ŀ��
 *
 */
@Entity
@Table(name="ali_lei_mu")
public class AliLeiMu implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String zhName; // ��������
	private String enName; // Ӣ������
	private Integer level; 
	private Integer isLeaf; // �Ƿ�Ҷ�ӽڵ�
	private Long parentId; // ���ڵ�Id
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "zh_name")
	public String getZhName() {
		return zhName;
	}
	public void setZhName(String zhName) {
		this.zhName = zhName;
	}
	@Column(name = "en_name")
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	@Column(name = "is_leaf")
	public Integer getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}
	@Column(name = "parent_id")
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
