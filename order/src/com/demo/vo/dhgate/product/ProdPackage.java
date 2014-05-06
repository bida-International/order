package com.demo.vo.dhgate.product;

import java.io.Serializable;
import java.util.List;

public class ProdPackage implements Serializable {

	private static final long serialVersionUID = 1L;
	private Double grossWeight;
	private Double height;
	private Double length;
	private Integer lots;
	private String measureId;
	private Integer packingQuantity;
	private Double width;
//	private List<ProdWeightRangeVo> prodWeightRangeDto;
	private ProdWeightRangeVo prodWeightRangeDto;
	public Double getGrossWeight() {
		return grossWeight;
	}
	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	public Double getLength() {
		return length;
	}
	public void setLength(Double length) {
		this.length = length;
	}
	public Integer getLots() {
		return lots;
	}
	public void setLots(Integer lots) {
		this.lots = lots;
	}
	public String getMeasureId() {
		return measureId;
	}
	public void setMeasureId(String measureId) {
		this.measureId = measureId;
	}
	public Integer getPackingQuantity() {
		return packingQuantity;
	}
	public void setPackingQuantity(Integer packingQuantity) {
		this.packingQuantity = packingQuantity;
	}
	public Double getWidth() {
		return width;
	}
	public void setWidth(Double width) {
		this.width = width;
	}
//	public List<ProdWeightRangeVo> getProdWeightRangeDto() {
//		return prodWeightRangeDto;
//	}
//	public void setProdWeightRangeDto(List<ProdWeightRangeVo> prodWeightRangeDto) {
//		this.prodWeightRangeDto = prodWeightRangeDto;
//	}
	public ProdWeightRangeVo getProdWeightRangeDto() {
		return prodWeightRangeDto;
	}
	public void setProdWeightRangeDto(ProdWeightRangeVo prodWeightRangeDto) {
		this.prodWeightRangeDto = prodWeightRangeDto;
	}

}
