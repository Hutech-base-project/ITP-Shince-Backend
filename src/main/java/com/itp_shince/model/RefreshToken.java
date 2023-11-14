package com.itp_shince.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@SuppressWarnings("serial")
@Entity
@Table(name = "refreshtoken", catalog = "itpshince")
public class RefreshToken implements java.io.Serializable {

	private Integer id;
	private Users users;
	private String token;
	private String jwtId;
	private boolean isUsed;
	private boolean isRevorked;
	private Date addedDate;
	private Date expiryDate;

	public RefreshToken() {
	}

	public RefreshToken(Users users, String token, String jwtId, boolean isUsed, boolean isRevorked) {
		this.users = users;
		this.token = token;
		this.jwtId = jwtId;
		this.isUsed = isUsed;
		this.isRevorked = isRevorked;
	}

	public RefreshToken(Users users, String token, String jwtId, boolean isUsed, boolean isRevorked, Date addedDate,
			Date expiryDate) {
		this.users = users;
		this.token = token;
		this.jwtId = jwtId;
		this.isUsed = isUsed;
		this.isRevorked = isRevorked;
		this.addedDate = addedDate;
		this.expiryDate = expiryDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "Id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "User_Id", nullable = false)
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Column(name = "Token", nullable = false)
	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Column(name = "JwtId", nullable = false)
	public String getJwtId() {
		return this.jwtId;
	}

	public void setJwtId(String jwtId) {
		this.jwtId = jwtId;
	}

	@Column(name = "IsUsed", nullable = false)
	public boolean isIsUsed() {
		return this.isUsed;
	}

	public void setIsUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	@Column(name = "IsRevorked", nullable = false)
	public boolean isIsRevorked() {
		return this.isRevorked;
	}

	public void setIsRevorked(boolean isRevorked) {
		this.isRevorked = isRevorked;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AddedDate", length = 26)
	public Date getAddedDate() {
		return this.addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ExpiryDate", length = 26)
	public Date getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

}
