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
@Table(name = "serce_image", catalog = "itpshince")
public class SerceImage implements java.io.Serializable {

	private String serImgId;
	private Serce serce;
	private String serImgPath;
	private Date createdAt;
	private Date updatedAt;
	private Boolean isDelete;

	public SerceImage() {
	}

	public SerceImage(String serImgId, Serce serce, String serImgPath) {
		this.serImgId = serImgId;
		this.serce = serce;
		this.serImgPath = serImgPath;
	}

	public SerceImage(String serImgId, Serce serce, String serImgPath, Date createdAt, Date updatedAt,
			Boolean isDelete) {
		this.serImgId = serImgId;
		this.serce = serce;
		this.serImgPath = serImgPath;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isDelete = isDelete;
	}

	@Id

	@Column(name = "serImg_id", unique = true, nullable = false, length = 128)
	public String getSerImgId() {
		return this.serImgId;
	}

	public void setSerImgId(String serImgId) {
		this.serImgId = serImgId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_Id", nullable = false)
	public Serce getSerce() {
		return this.serce;
	}

	public void setSerce(Serce serce) {
		this.serce = serce;
	}

	@Column(name = "serImg_Path", nullable = false, length = 65535)
	public String getSerImgPath() {
		return this.serImgPath;
	}

	public void setSerImgPath(String serImgPath) {
		this.serImgPath = serImgPath;
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
