package com.itp_shince.dto.request;

import java.util.Date;

public class VoucherRequest {
	private String voId;
	private String voName;
	private String voDescription;
	private float voPrice;
	private Boolean voProduct;
	private Boolean voService;
	private int voCount;
	private Boolean voTypeAuto;
	private Date createdAt;
	private Date expirationDate	;
	private Boolean isDelete;
	
	public VoucherRequest(String voId, String voName, String voDescription, float voPrice, Boolean voProduct,
			Boolean voService, int voCount, Boolean voTypeAuto, Boolean isDelete) {
		super();
		this.voId = voId;
		this.voName = voName;
		this.voDescription = voDescription;
		this.voPrice = voPrice;
		this.voProduct = voProduct;
		this.voService = voService;
		this.voCount = voCount;
		this.voTypeAuto = voTypeAuto;
		this.isDelete = isDelete;
	}
	
	public VoucherRequest(String voId, String voName, String voDescription, float voPrice, Boolean voProduct,
			Boolean voService, int voCount, Boolean voTypeAuto, Date createdAt, Date expirationDate, Boolean isDelete) {
		super();
		this.voId = voId;
		this.voName = voName;
		this.voDescription = voDescription;
		this.voPrice = voPrice;
		this.voProduct = voProduct;
		this.voService = voService;
		this.voCount = voCount;
		this.voTypeAuto = voTypeAuto;
		this.createdAt = createdAt;
		this.expirationDate = expirationDate;
		this.isDelete = isDelete;
	}



	public String getVoId() {
		return voId;
	}

	public void setVoId(String voId) {
		this.voId = voId;
	}

	public String getVoName() {
		return voName;
	}

	public void setVoName(String voName) {
		this.voName = voName;
	}

	public String getVoDescription() {
		return voDescription;
	}

	public void setVoDescription(String voDescription) {
		this.voDescription = voDescription;
	}

	public float getVoPrice() {
		return voPrice;
	}

	public void setVoPrice(float voPrice) {
		this.voPrice = voPrice;
	}

	public Boolean getVoProduct() {
		return voProduct;
	}

	public void setVoProduct(Boolean voProduct) {
		this.voProduct = voProduct;
	}

	public Boolean getVoService() {
		return voService;
	}

	public void setVoService(Boolean voService) {
		this.voService = voService;
	}

	public int getVoCount() {
		return voCount;
	}

	public void setVoCount(int voCount) {
		this.voCount = voCount;
	}

	public Boolean getVoTypeAuto() {
		return voTypeAuto;
	}

	public void setVoTypeAuto(Boolean voTypeAuto) {
		this.voTypeAuto = voTypeAuto;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
}
