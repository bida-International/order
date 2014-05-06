package com.demo.vo.dhgate.product;

import java.io.Serializable;

public class ProdAttach implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String fileUrl;
	private String imageMd5;
	
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getImageMd5() {
		return imageMd5;
	}
	public void setImageMd5(String imageMd5) {
		this.imageMd5 = imageMd5;
	}
	
}
