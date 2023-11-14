package com.itp_shince.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "product_image", catalog = "itpshince")
public class ProductImage implements java.io.Serializable {

	private String proImgId;
	private Product product;
	private String proImgPath;
	private Date createdAt;
	private Date updatedAt;
	private Boolean isDelete;

	public ProductImage() {
	}

	public ProductImage(String proImgId, Product product, String proImgPath) {
		this.proImgId = proImgId;
		this.product = product;
		this.proImgPath = proImgPath;
	}

	public ProductImage(String proImgId, Product product, String proImgPath, Date createdAt, Date updatedAt,
			Boolean isDelete) {
		this.proImgId = proImgId;
		this.product = product;
		this.proImgPath = proImgPath;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isDelete = isDelete;
	}

	@Id

	@Column(name = "proImg_Id", unique = true, nullable = false, length = 128)
	public String getProImgId() {
		return this.proImgId;
	}

	public void setProImgId(String proImgId) {
		this.proImgId = proImgId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_Id", nullable = false)
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(name = "proImg_Path", nullable = false, length = 65535)
	public String getProImgPath() {
		return this.proImgPath;
	}

	public void setProImgPath(String proImgPath) {
		this.proImgPath = proImgPath;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_At", length = 26)
	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_At", length = 26)
	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Column(name = "is_Delete")
	public Boolean getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

}
