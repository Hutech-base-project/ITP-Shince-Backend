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
@Table(name = "orderspro", catalog = "itpshince")
public class OrdersPro implements java.io.Serializable {

	private String orProId;
	private Users users;
	private String orProUserName;
	private String orProDob;
	private String orProAddress;
	private String orProPhoneNo;
	private String orProPayStatus;
	private String orProPayment;
	private String orProStatus;
	private String orProNote;
	private float orProPromotion;
	private float orProShip;
	private float orProTotal;
	private Date createdAt;
	private Date updatedAt;
	private Set<OrdersProDetail> ordersprodetails = new HashSet<OrdersProDetail>(0);

	public OrdersPro() {
	}

	public OrdersPro(String orProId, String orProUserName, String orProPhoneNo, String orProPayStatus,
			String orProPayment, String orProStatus, float orProPromotion, float orProShip, float orProTotal) {
		this.orProId = orProId;
		this.orProUserName = orProUserName;
		this.orProPhoneNo = orProPhoneNo;
		this.orProPayStatus = orProPayStatus;
		this.orProPayment = orProPayment;
		this.orProStatus = orProStatus;
		this.orProPromotion = orProPromotion;
		this.orProShip = orProShip;
		this.orProTotal = orProTotal;
	}

	public OrdersPro(String orProId, Users users, String orProUserName, String orProDob, String orProAddress,
			String orProPhoneNo, String orProPayStatus, String orProPayment, String orProStatus, String orProNote,
			float orProPromotion, float orProShip, float orProTotal, Date createdAt, Date updatedAt,
			Set<OrdersProDetail> ordersprodetails) {
		this.orProId = orProId;
		this.users = users;
		this.orProUserName = orProUserName;
		this.orProDob = orProDob;
		this.orProAddress = orProAddress;
		this.orProPhoneNo = orProPhoneNo;
		this.orProPayStatus = orProPayStatus;
		this.orProPayment = orProPayment;
		this.orProStatus = orProStatus;
		this.orProNote = orProNote;
		this.orProPromotion = orProPromotion;
		this.orProShip = orProShip;
		this.orProTotal = orProTotal;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.ordersprodetails = ordersprodetails;
	}

	@Id

	@Column(name = "orPro_Id", unique = true, nullable = false, length = 128)
	public String getOrProId() {
		return this.orProId;
	}

	public void setOrProId(String orProId) {
		this.orProId = orProId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orPro_User_Id")
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Column(name = "orPro_User_Name", nullable = false, length = 65535)
	public String getOrProUserName() {
		return this.orProUserName;
	}

	public void setOrProUserName(String orProUserName) {
		this.orProUserName = orProUserName;
	}

	@Column(name = "orPro_Dob", length = 20)
	public String getOrProDob() {
		return this.orProDob;
	}

	public void setOrProDob(String orProDob) {
		this.orProDob = orProDob;
	}

	@Column(name = "orPro_Address", length = 150)
	public String getOrProAddress() {
		return this.orProAddress;
	}

	public void setOrProAddress(String orProAddress) {
		this.orProAddress = orProAddress;
	}

	@Column(name = "orPro_PhoneNo", nullable = false, length = 15)
	public String getOrProPhoneNo() {
		return this.orProPhoneNo;
	}

	public void setOrProPhoneNo(String orProPhoneNo) {
		this.orProPhoneNo = orProPhoneNo;
	}

	@Column(name = "orPro_Pay_Status", nullable = false, length = 50)
	public String getOrProPayStatus() {
		return this.orProPayStatus;
	}

	public void setOrProPayStatus(String orProPayStatus) {
		this.orProPayStatus = orProPayStatus;
	}

	@Column(name = "orPro_Payment", nullable = false, length = 50)
	public String getOrProPayment() {
		return this.orProPayment;
	}

	public void setOrProPayment(String orProPayment) {
		this.orProPayment = orProPayment;
	}

	@Column(name = "orPro_Status", nullable = false, length = 50)
	public String getOrProStatus() {
		return this.orProStatus;
	}

	public void setOrProStatus(String orProStatus) {
		this.orProStatus = orProStatus;
	}

	@Column(name = "orPro_Note", length = 65535)
	public String getOrProNote() {
		return this.orProNote;
	}

	public void setOrProNote(String orProNote) {
		this.orProNote = orProNote;
	}

	@Column(name = "orPro_Promotion", nullable = false, precision = 12, scale = 0)
	public float getOrProPromotion() {
		return this.orProPromotion;
	}

	public void setOrProPromotion(float orProPromotion) {
		this.orProPromotion = orProPromotion;
	}

	@Column(name = "orPro_Ship", nullable = false, precision = 12, scale = 0)
	public float getOrProShip() {
		return this.orProShip;
	}

	public void setOrProShip(float orProShip) {
		this.orProShip = orProShip;
	}

	@Column(name = "orPro_Total", nullable = false, precision = 12, scale = 0)
	public float getOrProTotal() {
		return this.orProTotal;
	}

	public void setOrProTotal(float orProTotal) {
		this.orProTotal = orProTotal;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderspro")
	public Set<OrdersProDetail> getOrdersprodetails() {
		return this.ordersprodetails;
	}

	public void setOrdersprodetails(Set<OrdersProDetail> ordersprodetails) {
		this.ordersprodetails = ordersprodetails;
	}

}
