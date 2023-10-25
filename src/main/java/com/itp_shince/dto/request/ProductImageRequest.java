package com.itp_shince.dto.request;

import java.util.Date;

import com.itp_shince.dto.AbstractDTO;
import com.itp_shince.model.Product;

public class ProductImageRequest extends AbstractDTO<ProductRequest>{
	private String proImgId;
	private Product product;
	private String proImgPath;
	private Boolean isDelete;
	public ProductImageRequest(String proImgId, Product product, String proImgPath, Date createdAt, Boolean isDelete) {
		super();
		this.proImgId = proImgId;
		this.product = product;
		this.proImgPath = proImgPath;
		super.setCreatedAt(createdAt);
		this.isDelete = isDelete;
	}
	public String getProImgId() {
		return proImgId;
	}
	public void setProImgId(String proImgId) {
		this.proImgId = proImgId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getProImgPath() {
		return proImgPath;
	}
	public void setProImgPath(String proImgPath) {
		this.proImgPath = proImgPath;
	}
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
}
