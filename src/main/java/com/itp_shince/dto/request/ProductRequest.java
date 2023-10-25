package com.itp_shince.dto.request;

import java.util.List;

import com.itp_shince.dto.AbstractDTO;

public class ProductRequest extends AbstractDTO<ProductRequest>{
	private String proId;
	private Integer category_id ;
	private String proName;
	private Float proPrice;
	private String featureImgPath;
	private String proContent;
	private String proBrand;
	private Boolean proTurnOn;
	private int proQuantity;
	private String proStatus;
	private Boolean isDelete;
	private List<String> idImgChild ;
	
	
	public ProductRequest() {
	}

	public ProductRequest(String proId, Integer category_id, String proName, Float proPrice, String featureImgPath,
			String proContent, String proBrand,  Boolean proTurnOn, Boolean isDelete) {
		super();
		this.proId = proId;
		this.category_id = category_id;
		this.proName = proName;
		this.proPrice = proPrice;
		this.featureImgPath = featureImgPath;
		this.proContent = proContent;
		this.proBrand = proBrand;
		this.proTurnOn = proTurnOn;
		this.isDelete = isDelete;
	}
	
	public ProductRequest(String proId, Integer category_id, String proName, Float proPrice, String featureImgPath,
			String proContent, String proBrand, Boolean proTurnOn, int proQuantity, String proStatus, Boolean isDelete,
			List<String> idImgChild) {
		super();
		this.proId = proId;
		this.category_id = category_id;
		this.proName = proName;
		this.proPrice = proPrice;
		this.featureImgPath = featureImgPath;
		this.proContent = proContent;
		this.proBrand = proBrand;
		this.proTurnOn = proTurnOn;
		this.proQuantity = proQuantity;
		this.proStatus = proStatus;
		this.isDelete = isDelete;
		this.idImgChild = idImgChild;
	}

	public String getProId() {
		return proId;
	}
	public void setProId(String proId) {
		this.proId = proId;
	}
	public Integer getCategory_id() {
		return category_id;
	}
	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public Float getProPrice() {
		return proPrice;
	}
	public void setProPrice(Float proPrice) {
		this.proPrice = proPrice;
	}
	public String getFeatureImgPath() {
		return featureImgPath;
	}
	public void setFeatureImgPath(String featureImgPath) {
		this.featureImgPath = featureImgPath;
	}
	public String getProContent() {
		return proContent;
	}
	public void setProContent(String proContent) {
		this.proContent = proContent;
	}
	public String getProBrand() {
		return proBrand;
	}
	public void setProBrand(String proBrand) {
		this.proBrand = proBrand;
	}
	public Boolean getProTurnOn() {
		return proTurnOn;
	}

	public void setProTurnOn(Boolean proTurnOn) {
		this.proTurnOn = proTurnOn;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public List<String> getIdImgChild() {
		return idImgChild;
	}

	public void setIdImgChild(List<String> idImgChild) {
		this.idImgChild = idImgChild;
	}

	public int getProQuantity() {
		return proQuantity;
	}

	public void setProQuantity(int proQuantity) {
		this.proQuantity = proQuantity;
	}

	public String getProStatus() {
		return proStatus;
	}

	public void setProStatus(String proStatus) {
		this.proStatus = proStatus;
	}
}
