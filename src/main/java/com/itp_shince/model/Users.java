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
@Table(name = "users", catalog = "itpshince")
public class Users implements java.io.Serializable {

	private String usId;
	private String usUserName;
	private String usPassword;
	private String usDob;
	private String usAddress;
	private String usPhoneNo;
	private String usEmailNo;
	private String usImage;
	private String usNote;
	private Boolean isAdmin;
	private Boolean isBlock;
	private Date createdAt;
	private Date updatedAt;
	private Boolean isDelete;
	private Set<OrdersPro> orderspros = new HashSet<OrdersPro>(0);
	private Set<Blog> blogs = new HashSet<Blog>(0);
	private Set<Booking> bookingsForBoEmployeeId = new HashSet<Booking>(0);
	private Set<UserVoucher> userVouchers = new HashSet<UserVoucher>(0);
	private Set<WhishList> whishLists = new HashSet<WhishList>(0);
	private Set<RefreshToken> refreshtokens = new HashSet<RefreshToken>(0);
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);
	private Set<Booking> bookingsForBoUserId = new HashSet<Booking>(0);

	public Users() {
	}

	public Users(String usId, String usUserName, String usPassword, String usPhoneNo) {
		this.usId = usId;
		this.usUserName = usUserName;
		this.usPassword = usPassword;
		this.usPhoneNo = usPhoneNo;
	}

	public Users(String usId, String usUserName, String usPassword, String usDob, String usAddress, String usPhoneNo,
			String usEmailNo, String usImage, String usNote, Boolean isAdmin, Boolean isBlock, Date createdAt,
			Date updatedAt, Boolean isDelete, Set<OrdersPro> orderspros, Set<Blog> blogs,
			Set<Booking> bookingsForBoEmployeeId, Set<UserVoucher> userVouchers, Set<WhishList> whishLists,
			Set<RefreshToken> refreshtokens, Set<UserRole> userRoles, Set<Booking> bookingsForBoUserId) {
		this.usId = usId;
		this.usUserName = usUserName;
		this.usPassword = usPassword;
		this.usDob = usDob;
		this.usAddress = usAddress;
		this.usPhoneNo = usPhoneNo;
		this.usEmailNo = usEmailNo;
		this.usImage = usImage;
		this.usNote = usNote;
		this.isAdmin = isAdmin;
		this.isBlock = isBlock;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isDelete = isDelete;
		this.orderspros = orderspros;
		this.blogs = blogs;
		this.bookingsForBoEmployeeId = bookingsForBoEmployeeId;
		this.userVouchers = userVouchers;
		this.whishLists = whishLists;
		this.refreshtokens = refreshtokens;
		this.userRoles = userRoles;
		this.bookingsForBoUserId = bookingsForBoUserId;
	}

	@Id

	@Column(name = "us_Id", unique = true, nullable = false, length = 128)
	public String getUsId() {
		return this.usId;
	}

	public void setUsId(String usId) {
		this.usId = usId;
	}

	@Column(name = "us_User_Name", nullable = false, length = 65535)
	public String getUsUserName() {
		return this.usUserName;
	}

	public void setUsUserName(String usUserName) {
		this.usUserName = usUserName;
	}

	@Column(name = "us_Password", nullable = false, length = 65535)
	public String getUsPassword() {
		return this.usPassword;
	}

	public void setUsPassword(String usPassword) {
		this.usPassword = usPassword;
	}

	@Column(name = "us_Dob", length = 20)
	public String getUsDob() {
		return this.usDob;
	}

	public void setUsDob(String usDob) {
		this.usDob = usDob;
	}

	@Column(name = "us_Address", length = 150)
	public String getUsAddress() {
		return this.usAddress;
	}

	public void setUsAddress(String usAddress) {
		this.usAddress = usAddress;
	}

	@Column(name = "us_PhoneNo", length = 15, nullable = false)
	public String getUsPhoneNo() {
		return this.usPhoneNo;
	}

	public void setUsPhoneNo(String usPhoneNo) {
		this.usPhoneNo = usPhoneNo;
	}

	@Column(name = "us_EmailNo", length = 65535)
	public String getUsEmailNo() {
		return this.usEmailNo;
	}

	public void setUsEmailNo(String usEmailNo) {
		this.usEmailNo = usEmailNo;
	}

	@Column(name = "us_Image", length = 250)
	public String getUsImage() {
		return this.usImage;
	}

	public void setUsImage(String usImage) {
		this.usImage = usImage;
	}

	@Column(name = "us_Note", length = 65535)
	public String getUsNote() {
		return this.usNote;
	}

	public void setUsNote(String usNote) {
		this.usNote = usNote;
	}

	@Column(name = "is_Admin")
	public Boolean getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Column(name = "is_Block")
	public Boolean getIsBlock() {
		return this.isBlock;
	}

	public void setIsBlock(Boolean isBlock) {
		this.isBlock = isBlock;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<OrdersPro> getOrderspros() {
		return this.orderspros;
	}

	public void setOrderspros(Set<OrdersPro> orderspros) {
		this.orderspros = orderspros;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<Blog> getBlogs() {
		return this.blogs;
	}

	public void setBlogs(Set<Blog> blogs) {
		this.blogs = blogs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usersByBoEmployeeId")
	public Set<Booking> getBookingsForBoEmployeeId() {
		return this.bookingsForBoEmployeeId;
	}

	public void setBookingsForBoEmployeeId(Set<Booking> bookingsForBoEmployeeId) {
		this.bookingsForBoEmployeeId = bookingsForBoEmployeeId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<UserVoucher> getUserVouchers() {
		return this.userVouchers;
	}

	public void setUserVouchers(Set<UserVoucher> userVouchers) {
		this.userVouchers = userVouchers;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<WhishList> getWhishLists() {
		return this.whishLists;
	}

	public void setWhishLists(Set<WhishList> whishLists) {
		this.whishLists = whishLists;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<RefreshToken> getRefreshtokens() {
		return this.refreshtokens;
	}

	public void setRefreshtokens(Set<RefreshToken> refreshtokens) {
		this.refreshtokens = refreshtokens;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usersByBoUserId")
	public Set<Booking> getBookingsForBoUserId() {
		return this.bookingsForBoUserId;
	}

	public void setBookingsForBoUserId(Set<Booking> bookingsForBoUserId) {
		this.bookingsForBoUserId = bookingsForBoUserId;
	}

}
