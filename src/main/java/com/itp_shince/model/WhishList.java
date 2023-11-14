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
@Table(name = "whish_list", catalog = "itpshince")
public class WhishList implements java.io.Serializable {

	private Integer whlId;
	private Product product;
	private Serce serce;
	private Users users;
	private Date createdAt;
	private Date updatedAt;
	private Boolean isDelete;

	public WhishList() {
	}

	public WhishList(Users users) {
		this.users = users;
	}

	public WhishList(Product product, Serce serce, Users users, Date createdAt, Date updatedAt, Boolean isDelete) {
		this.product = product;
		this.serce = serce;
		this.users = users;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isDelete = isDelete;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "whl_Id", unique = true, nullable = false)
	public Integer getWhlId() {
		return this.whlId;
	}

	public void setWhlId(Integer whlId) {
		this.whlId = whlId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "whl_Produc_id")
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "whl_Service_id")
	public Serce getSerce() {
		return this.serce;
	}

	public void setSerce(Serce serce) {
		this.serce = serce;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "whl_User_Id", nullable = false)
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
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
