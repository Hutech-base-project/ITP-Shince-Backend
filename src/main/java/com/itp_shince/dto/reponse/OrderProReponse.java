package com.itp_shince.dto.reponse;
import java.util.List;

import com.itp_shince.dto.AbstractDTO;

public class OrderProReponse extends AbstractDTO<OrderProReponse>{
	private String orProId;
	private String orProUserId;
	private String orProUserName;
	private String orProDob;
	private String orProAddress;
	private String orProPhoneNo;
	private String orProPayStatus;
	private String orProPayment;
	private String orProStatus;
	private String orProNote;
	private Float orProTotal;
	private Float orProShip;
	
	private List<OrderProDetailsReponse> listPro;
	
	
	public OrderProReponse() {
	}

	public OrderProReponse(String orProId, String orProUserId, String orProUserName, String orProDob, String orProAddress,
			String orProPhoneNo, String orProPayStatus, String orProPayment, String orProStatus, String orProNote,Float orProTotal,Float orProShip) {
		super();
		this.orProId = orProId;
		this.orProUserId = orProUserId;
		this.orProUserName = orProUserName;
		this.orProDob = orProDob;
		this.orProAddress = orProAddress;
		this.orProPhoneNo = orProPhoneNo;
		this.orProPayStatus = orProPayStatus;
		this.orProPayment = orProPayment;
		this.orProStatus = orProStatus;
		this.orProNote = orProNote;
		this.orProTotal = orProTotal;
		this.orProShip = orProShip;
	}

	public Float getOrProTotal() {
		return orProTotal;
	}

	public void setOrProTotal(Float orProTotal) {
		this.orProTotal = orProTotal;
	}

	public Float getOrProShip() {
		return orProShip;
	}

	public void setOrProShip(Float orProShip) {
		this.orProShip = orProShip;
	}

	public String getOrProId() {
		return orProId;
	}

	public void setOrProId(String orProId) {
		this.orProId = orProId;
	}

	public String getOrProUserId() {
		return orProUserId;
	}

	public void setOrProUserId(String orProUserId) {
		this.orProUserId = orProUserId;
	}

	public String getOrProUserName() {
		return orProUserName;
	}

	public void setOrProUserName(String orProUserName) {
		this.orProUserName = orProUserName;
	}

	public String getOrProDob() {
		return orProDob;
	}

	public void setOrProDob(String orProDob) {
		this.orProDob = orProDob;
	}

	public String getOrProAddress() {
		return orProAddress;
	}

	public void setOrProAddress(String orProAddress) {
		this.orProAddress = orProAddress;
	}

	public String getOrProPhoneNo() {
		return orProPhoneNo;
	}

	public void setOrProPhoneNo(String orProPhoneNo) {
		this.orProPhoneNo = orProPhoneNo;
	}

	public String getOrProPayStatus() {
		return orProPayStatus;
	}

	public void setOrProPayStatus(String orProPayStatus) {
		this.orProPayStatus = orProPayStatus;
	}

	public String getOrProPayment() {
		return orProPayment;
	}

	public void setOrProPayment(String orProPayment) {
		this.orProPayment = orProPayment;
	}

	public String getOrProStatus() {
		return orProStatus;
	}

	public void setOrProStatus(String orProStatus) {
		this.orProStatus = orProStatus;
	}

	public String getOrProNote() {
		return orProNote;
	}

	public void setOrProNote(String orProNote) {
		this.orProNote = orProNote;
	}

	public List<OrderProDetailsReponse> getListPro() {
		return listPro;
	}

	public void setListPro(List<OrderProDetailsReponse> listPro) {
		this.listPro = listPro;
	}
}
