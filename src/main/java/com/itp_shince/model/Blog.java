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
@Table(name = "blog", catalog = "itpshince")
public class Blog implements java.io.Serializable {

	private String blId;
	private Users users;
	private String blName;
	private String featureImgPath;
	private String blContent;
	private Boolean blTurnOn;
	private Date createdAt;
	private Date updatedAt;
	private Boolean isDelete;

	public Blog() {
	}

	public Blog(String blId, String blName, String blContent) {
		this.blId = blId;
		this.blName = blName;
		this.blContent = blContent;
	}

	public Blog(String blId, Users users, String blName, String featureImgPath, String blContent, Boolean blTurnOn,
			Date createdAt, Date updatedAt, Boolean isDelete) {
		this.blId = blId;
		this.users = users;
		this.blName = blName;
		this.featureImgPath = featureImgPath;
		this.blContent = blContent;
		this.blTurnOn = blTurnOn;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isDelete = isDelete;
	}

	@Id

	@Column(name = "bl_Id", unique = true, nullable = false, length = 128)
	public String getBlId() {
		return this.blId;
	}

	public void setBlId(String blId) {
		this.blId = blId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bl_Employee_Id")
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Column(name = "bl_Name", nullable = false)
	public String getBlName() {
		return this.blName;
	}

	public void setBlName(String blName) {
		this.blName = blName;
	}

	@Column(name = "feature_Img_Path", length = 65535)
	public String getFeatureImgPath() {
		return this.featureImgPath;
	}

	public void setFeatureImgPath(String featureImgPath) {
		this.featureImgPath = featureImgPath;
	}

	@Column(name = "bl_Content", nullable = false)
	public String getBlContent() {
		return this.blContent;
	}

	public void setBlContent(String blContent) {
		this.blContent = blContent;
	}

	@Column(name = "bl_Turn_On")
	public Boolean getBlTurnOn() {
		return this.blTurnOn;
	}

	public void setBlTurnOn(Boolean blTurnOn) {
		this.blTurnOn = blTurnOn;
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
