package com.itp_shince.dto.request;


import java.util.Date;

import com.itp_shince.dto.AbstractDTO;
import com.itp_shince.model.Serce;

public class SerceImageRequest extends AbstractDTO<SerceImageRequest>{
	private String serImgId;
	private Serce serce;
	private String serImgPath;
	private Boolean isDelete;
	
	public SerceImageRequest(String serImgId, Serce serce, String serImgPath, Date createdAt, Boolean isDelete) {
		super();
		this.serImgId = serImgId;
		this.serce = serce;
		this.serImgPath = serImgPath;
		super.setCreatedAt(createdAt);
		this.isDelete = isDelete;
	}

	public String getSerImgId() {
		return serImgId;
	}

	public void setSerImgId(String serImgId) {
		this.serImgId = serImgId;
	}

	public Serce getSerce() {
		return serce;
	}

	public void setSerce(Serce serce) {
		this.serce = serce;
	}

	public String getSerImgPath() {
		return serImgPath;
	}

	public void setSerImgPath(String serImgPath) {
		this.serImgPath = serImgPath;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	
	
}
