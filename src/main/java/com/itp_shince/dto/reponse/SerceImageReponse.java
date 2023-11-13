package com.itp_shince.dto.reponse;

public class SerceImageReponse {
	private String serImgId;
	private String serImgPath;
	
	public SerceImageReponse(String serImgId, String serImgPath) {
		super();
		this.serImgId = serImgId;
		this.serImgPath = serImgPath;
	}

	public String getSerImgId() {
		return serImgId;
	}

	public void setSerImgId(String serImgId) {
		this.serImgId = serImgId;
	}

	public String getSerImgPath() {
		return serImgPath;
	}

	public void setSerImgPath(String serImgPath) {
		this.serImgPath = serImgPath;
	}
}
