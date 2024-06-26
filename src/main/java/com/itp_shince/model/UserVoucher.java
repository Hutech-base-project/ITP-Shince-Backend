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
@Table(name = "user_voucher", catalog = "itpshince")
public class UserVoucher implements java.io.Serializable {

	private Integer usvoId;
	private Users users;
	private Voucher voucher;
	private Date usvoUsedTime;

	public UserVoucher() {
	}

	public UserVoucher(Users users, Voucher voucher) {
		this.users = users;
		this.voucher = voucher;
	}

	public UserVoucher(Users users, Voucher voucher, Date usvoUsedTime) {
		this.users = users;
		this.voucher = voucher;
		this.usvoUsedTime = usvoUsedTime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "usvo_Id", unique = true, nullable = false)
	public Integer getUsvoId() {
		return this.usvoId;
	}

	public void setUsvoId(Integer usvoId) {
		this.usvoId = usvoId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usvo_User_Id", nullable = false)
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usvo_Vocher_Id", nullable = false)
	public Voucher getVoucher() {
		return this.voucher;
	}

	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "usvo_used_time", length = 26)
	public Date getUsvoUsedTime() {
		return this.usvoUsedTime;
	}

	public void setUsvoUsedTime(Date usvoUsedTime) {
		this.usvoUsedTime = usvoUsedTime;
	}

}
