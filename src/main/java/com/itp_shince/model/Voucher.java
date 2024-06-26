package com.itp_shince.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "voucher", catalog = "itpshince")
public class Voucher implements java.io.Serializable {

	private String voId;
	private String voName;
	private String voDescription;
	private float voPrice;
	private Boolean voProduct;
	private Boolean voService;
	private int voCount;
	private Boolean voTypeAuto;
	private Date createdAt;
	private Date expirationDate;
	private Boolean isDelete;
	private Set<UserVoucher> userVouchers = new HashSet<UserVoucher>(0);

	public Voucher() {
	}

	public Voucher(String voId, String voName, String voDescription, float voPrice, int voCount) {
		this.voId = voId;
		this.voName = voName;
		this.voDescription = voDescription;
		this.voPrice = voPrice;
		this.voCount = voCount;
	}

	public Voucher(String voId, String voName, String voDescription, float voPrice, Boolean voProduct,
			Boolean voService, int voCount, Boolean voTypeAuto, Date createdAt, Date expirationDate, Boolean isDelete,
			Set<UserVoucher> userVouchers) {
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
		this.userVouchers = userVouchers;
	}

	@Id

	@Column(name = "vo_Id", unique = true, nullable = false, length = 128)
	public String getVoId() {
		return this.voId;
	}

	public void setVoId(String voId) {
		this.voId = voId;
	}

	@Column(name = "vo_Name", nullable = false, length = 128)
	public String getVoName() {
		return this.voName;
	}

	public void setVoName(String voName) {
		this.voName = voName;
	}

	@Column(name = "vo_Description", nullable = false, length = 65535)
	public String getVoDescription() {
		return this.voDescription;
	}

	public void setVoDescription(String voDescription) {
		this.voDescription = voDescription;
	}

	@Column(name = "vo_Price", nullable = false, precision = 12, scale = 0)
	public float getVoPrice() {
		return this.voPrice;
	}

	public void setVoPrice(float voPrice) {
		this.voPrice = voPrice;
	}

	@Column(name = "vo_Product")
	public Boolean getVoProduct() {
		return this.voProduct;
	}

	public void setVoProduct(Boolean voProduct) {
		this.voProduct = voProduct;
	}

	@Column(name = "vo_Service")
	public Boolean getVoService() {
		return this.voService;
	}

	public void setVoService(Boolean voService) {
		this.voService = voService;
	}

	@Column(name = "vo_Count", nullable = false)
	public int getVoCount() {
		return this.voCount;
	}

	public void setVoCount(int voCount) {
		this.voCount = voCount;
	}

	@Column(name = "vo_type_auto")
	public Boolean getVoTypeAuto() {
		return this.voTypeAuto;
	}

	public void setVoTypeAuto(Boolean voTypeAuto) {
		this.voTypeAuto = voTypeAuto;
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
	@Column(name = "expiration_Date", length = 26)
	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Column(name = "is_Delete")
	public Boolean getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "voucher")
	public Set<UserVoucher> getUserVouchers() {
		return this.userVouchers;
	}

	public void setUserVouchers(Set<UserVoucher> userVouchers) {
		this.userVouchers = userVouchers;
	}

}
