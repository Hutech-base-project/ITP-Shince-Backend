package com.itp_shince.dto.reponse;

import com.itp_shince.dto.AbstractDTO;

public class WhishListReponse extends AbstractDTO<WhishListReponse> {
	private Integer whlId;
	private String productId;
	private String serceId;
	private String userId;
	private Boolean isDelete;
	
	
	public WhishListReponse() {
		super();
	}

	public WhishListReponse(Integer whlId, String productId, String serceId, String userId, Boolean isDelete) {
		super();
		this.whlId = whlId;
		this.productId = productId;
		this.serceId = serceId;
		this.userId = userId;
		this.isDelete = isDelete;
	}

	public Integer getWhlId() {
		return whlId;
	}

	public void setWhlId(Integer whlId) {
		this.whlId = whlId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSerceId() {
		return serceId;
	}

	public void setSerceId(String serceId) {
		this.serceId = serceId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
}
