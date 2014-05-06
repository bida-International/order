package com.demo.vo.dhgate.product;

import java.io.Serializable;

public class ProdWeightRangeVo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long baseQt;
	private String isOnlyWeight;
	private Long stepQt;
	private Double stepWeight;
	
	public Long getBaseQt() {
		return baseQt;
	}
	public void setBaseQt(Long baseQt) {
		this.baseQt = baseQt;
	}
	public String getIsOnlyWeight() {
		return isOnlyWeight;
	}
	public void setIsOnlyWeight(String isOnlyWeight) {
		this.isOnlyWeight = isOnlyWeight;
	}
	public Long getStepQt() {
		return stepQt;
	}
	public void setStepQt(Long stepQt) {
		this.stepQt = stepQt;
	}
	public Double getStepWeight() {
		return stepWeight;
	}
	public void setStepWeight(Double stepWeight) {
		this.stepWeight = stepWeight;
	}
}
