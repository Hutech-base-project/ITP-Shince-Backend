package com.itp_shince.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "product", catalog = "itpshince")
public class Product implements java.io.Serializable {

	private String proId;
	private Category category;
	private String proName;
	private float proPrice;
	private String featureImgPath;
	private String proContent;
	private String proBrand;
	private Boolean proTurnOn;
	private int proQuantity;
	private String proStatus;
	private Date createdAt;
	private Date updatedAt;
	private Boolean isDelete;
	private Set<OrdersProDetail> ordersprodetails = new HashSet<OrdersProDetail>(0);
	private Set<ProductImage> productImages = new HashSet<ProductImage>(0);
	private Set<WhishList> whishLists = new HashSet<WhishList>(0);

	public Product() {
	}

	public Product(String proId, Category category, String proName, float proPrice, String proContent, String proBrand,
			int proQuantity, String proStatus) {
		this.proId = proId;
		this.category = category;
		this.proName = proName;
		this.proPrice = proPrice;
		this.proContent = proContent;
		this.proBrand = proBrand;
		this.proQuantity = proQuantity;
		this.proStatus = proStatus;
	}

	public Product(String proId, Category category, String proName, float proPrice, String featureImgPath,
			String proContent, String proBrand, Boolean proTurnOn, int proQuantity, String proStatus, Date createdAt,
			Date updatedAt, Boolean isDelete, Set<OrdersProDetail> ordersprodetails, Set<ProductImage> productImages,
			Set<WhishList> whishLists) {
		this.proId = proId;
		this.category = category;
		this.proName = proName;
		this.proPrice = proPrice;
		this.featureImgPath = featureImgPath;
		this.proContent = proContent;
		this.proBrand = proBrand;
		this.proTurnOn = proTurnOn;
		this.proQuantity = proQuantity;
		this.proStatus = proStatus;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isDelete = isDelete;
		this.ordersprodetails = ordersprodetails;
		this.productImages = productImages;
		this.whishLists = whishLists;
	}

	@Id

	@Column(name = "pro_Id", unique = true, nullable = false, length = 128)
	public String getProId() {
		return this.proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_Id", nullable = false)
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Column(name = "pro_Name", nullable = false)
	public String getProName() {
		return this.proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	@Column(name = "pro_Price", nullable = false, precision = 12, scale = 0)
	public float getProPrice() {
		return this.proPrice;
	}

	public void setProPrice(float proPrice) {
		this.proPrice = proPrice;
	}

	@Column(name = "feature_Img_Path", length = 65535)
	public String getFeatureImgPath() {
		return this.featureImgPath;
	}

	public void setFeatureImgPath(String featureImgPath) {
		this.featureImgPath = featureImgPath;
	}

	@Column(name = "pro_Content", nullable = false)
	public String getProContent() {
		return this.proContent;
	}

	public void setProContent(String proContent) {
		this.proContent = proContent;
	}

	@Column(name = "pro_Brand", nullable = false)
	public String getProBrand() {
		return this.proBrand;
	}

	public void setProBrand(String proBrand) {
		this.proBrand = proBrand;
	}

	@Column(name = "pro_Turn_On")
	public Boolean getProTurnOn() {
		return this.proTurnOn;
	}

	public void setProTurnOn(Boolean proTurnOn) {
		this.proTurnOn = proTurnOn;
	}

	@Column(name = "pro_Quantity", nullable = false)
	public int getProQuantity() {
		return this.proQuantity;
	}

	public void setProQuantity(int proQuantity) {
		this.proQuantity = proQuantity;
	}

	@Column(name = "pro_Status", nullable = false)
	public String getProStatus() {
		return this.proStatus;
	}

	public void setProStatus(String proStatus) {
		this.proStatus = proStatus;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	public Set<OrdersProDetail> getOrdersprodetails() {
		return this.ordersprodetails;
	}

	public void setOrdersprodetails(Set<OrdersProDetail> ordersprodetails) {
		this.ordersprodetails = ordersprodetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	public Set<ProductImage> getProductImages() {
		return this.productImages;
	}

	public void setProductImages(Set<ProductImage> productImages) {
		this.productImages = productImages;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	public Set<WhishList> getWhishLists() {
		return this.whishLists;
	}

	public void setWhishLists(Set<WhishList> whishLists) {
		this.whishLists = whishLists;
	}

}
