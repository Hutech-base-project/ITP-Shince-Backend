package com.itp_shince.dto.reponse;

public class ProductImgReponse {
	private String proImgId;
	private String proImgPath;
	
	public ProductImgReponse(String proImgId, String proImgPath) {
		super();
		this.proImgId = proImgId;
		this.proImgPath = proImgPath;
	}

	public String getProImgId() {
		return proImgId;
	}

	public void setProImgId(String proImgId) {
		this.proImgId = proImgId;
	}

	public String getProImgPath() {
		return proImgPath;
	}

	public void setProImgPath(String proImgPath) {
		this.proImgPath = proImgPath;
	}
	
	
}
