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
@Table(name = "booking", catalog = "itpshince")
public class Booking implements java.io.Serializable {

	private String boId;
	private Users usersByBoEmployeeId;
	private Users usersByBoUserId;
	private String boPhoneNo;
	private String boStatus;
	private String boNote;
	private String boStartTime;
	private String boEndTime;
	private Float boTotal;
	private Date createdAt;
	private Date updatedAt;
	private Set<BookingDetails> bookingdetailses = new HashSet<BookingDetails>(0);

	public Booking() {
	}

	public Booking(String boId, String boPhoneNo, String boStatus) {
		this.boId = boId;
		this.boPhoneNo = boPhoneNo;
		this.boStatus = boStatus;
	}

	public Booking(String boId, Users usersByBoEmployeeId, Users usersByBoUserId, String boPhoneNo, String boStatus,
			String boNote, String boStartTime, String boEndTime, Float boTotal, Date createdAt, Date updatedAt,
			Set<BookingDetails> bookingdetailses) {
		this.boId = boId;
		this.usersByBoEmployeeId = usersByBoEmployeeId;
		this.usersByBoUserId = usersByBoUserId;
		this.boPhoneNo = boPhoneNo;
		this.boStatus = boStatus;
		this.boNote = boNote;
		this.boStartTime = boStartTime;
		this.boEndTime = boEndTime;
		this.boTotal = boTotal;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.bookingdetailses = bookingdetailses;
	}

	@Id

	@Column(name = "bo_Id", unique = true, nullable = false, length = 128)
	public String getBoId() {
		return this.boId;
	}

	public void setBoId(String boId) {
		this.boId = boId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bo_Employee_Id")
	public Users getUsersByBoEmployeeId() {
		return this.usersByBoEmployeeId;
	}

	public void setUsersByBoEmployeeId(Users usersByBoEmployeeId) {
		this.usersByBoEmployeeId = usersByBoEmployeeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bo_User_Id")
	public Users getUsersByBoUserId() {
		return this.usersByBoUserId;
	}

	public void setUsersByBoUserId(Users usersByBoUserId) {
		this.usersByBoUserId = usersByBoUserId;
	}

	@Column(name = "bo_PhoneNo", nullable = false, length = 15)
	public String getBoPhoneNo() {
		return this.boPhoneNo;
	}

	public void setBoPhoneNo(String boPhoneNo) {
		this.boPhoneNo = boPhoneNo;
	}

	@Column(name = "bo_Status", nullable = false, length = 50)
	public String getBoStatus() {
		return this.boStatus;
	}

	public void setBoStatus(String boStatus) {
		this.boStatus = boStatus;
	}

	@Column(name = "bo_Note", length = 65535)
	public String getBoNote() {
		return this.boNote;
	}

	public void setBoNote(String boNote) {
		this.boNote = boNote;
	}

	@Column(name = "bo_Start_Time", length = 65535)
	public String getBoStartTime() {
		return this.boStartTime;
	}

	public void setBoStartTime(String boStartTime) {
		this.boStartTime = boStartTime;
	}

	@Column(name = "bo_End_Time", length = 65535)
	public String getBoEndTime() {
		return this.boEndTime;
	}

	public void setBoEndTime(String boEndTime) {
		this.boEndTime = boEndTime;
	}

	@Column(name = "bo_Total", precision = 12, scale = 0)
	public Float getBoTotal() {
		return this.boTotal;
	}

	public void setBoTotal(Float boTotal) {
		this.boTotal = boTotal;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "booking")
	public Set<BookingDetails> getBookingdetailses() {
		return this.bookingdetailses;
	}

	public void setBookingdetailses(Set<BookingDetails> bookingdetailses) {
		this.bookingdetailses = bookingdetailses;
	}

}
