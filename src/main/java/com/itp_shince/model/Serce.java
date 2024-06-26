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
@Table(name = "serce", catalog = "itpshince")
public class Serce implements java.io.Serializable {

	private String seId;
	private String seName;
	private float sePrice;
	private String seDescription;
	private String seNote;
	private String seImage;
	private Boolean seTurnOn;
	private Date createdAt;
	private Date updatedAt;
	private Boolean isDelete;
	private Set<SerceImage> serceImages = new HashSet<SerceImage>(0);
	private Set<WhishList> whishLists = new HashSet<WhishList>(0);
	private Set<BookingDetails> bookingdetailses = new HashSet<BookingDetails>(0);

	public Serce() {
	}

	public Serce(String seId, String seName, float sePrice, String seDescription) {
		this.seId = seId;
		this.seName = seName;
		this.sePrice = sePrice;
		this.seDescription = seDescription;
	}

	public Serce(String seId, String seName, float sePrice, String seDescription, String seNote, String seImage,
			Boolean seTurnOn, Date createdAt, Date updatedAt, Boolean isDelete, Set<SerceImage> serceImages,
			Set<WhishList> whishLists, Set<BookingDetails> bookingdetailses) {
		this.seId = seId;
		this.seName = seName;
		this.sePrice = sePrice;
		this.seDescription = seDescription;
		this.seNote = seNote;
		this.seImage = seImage;
		this.seTurnOn = seTurnOn;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isDelete = isDelete;
		this.serceImages = serceImages;
		this.whishLists = whishLists;
		this.bookingdetailses = bookingdetailses;
	}

	@Id

	@Column(name = "se_Id", unique = true, nullable = false, length = 128)
	public String getSeId() {
		return this.seId;
	}

	public void setSeId(String seId) {
		this.seId = seId;
	}

	@Column(name = "se_Name", nullable = false, length = 65535)
	public String getSeName() {
		return this.seName;
	}

	public void setSeName(String seName) {
		this.seName = seName;
	}

	@Column(name = "se_Price", nullable = false, precision = 12, scale = 0)
	public float getSePrice() {
		return this.sePrice;
	}

	public void setSePrice(float sePrice) {
		this.sePrice = sePrice;
	}

	@Column(name = "se_Description", nullable = false, length = 65535)
	public String getSeDescription() {
		return this.seDescription;
	}

	public void setSeDescription(String seDescription) {
		this.seDescription = seDescription;
	}

	@Column(name = "se_Note", length = 65535)
	public String getSeNote() {
		return this.seNote;
	}

	public void setSeNote(String seNote) {
		this.seNote = seNote;
	}

	@Column(name = "se_Image", length = 65535)
	public String getSeImage() {
		return this.seImage;
	}

	public void setSeImage(String seImage) {
		this.seImage = seImage;
	}

	@Column(name = "se_TurnOn")
	public Boolean getSeTurnOn() {
		return this.seTurnOn;
	}

	public void setSeTurnOn(Boolean seTurnOn) {
		this.seTurnOn = seTurnOn;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "serce")
	public Set<SerceImage> getSerceImages() {
		return this.serceImages;
	}

	public void setSerceImages(Set<SerceImage> serceImages) {
		this.serceImages = serceImages;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "serce")
	public Set<WhishList> getWhishLists() {
		return this.whishLists;
	}

	public void setWhishLists(Set<WhishList> whishLists) {
		this.whishLists = whishLists;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "serce")
	public Set<BookingDetails> getBookingdetailses() {
		return this.bookingdetailses;
	}

	public void setBookingdetailses(Set<BookingDetails> bookingdetailses) {
		this.bookingdetailses = bookingdetailses;
	}

}
