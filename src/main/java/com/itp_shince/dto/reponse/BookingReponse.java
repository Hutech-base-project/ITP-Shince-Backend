package com.itp_shince.dto.reponse;

import java.util.List;

import com.itp_shince.dto.AbstractDTO;

public class BookingReponse extends AbstractDTO<BookingReponse>{
	private String boId;
	private String employeeId;
	private String userId;
	private String boPhoneNo;
	private String boStatus;
	private String boNote;
	private String boStartTime;
	private String boEndTime;
	private Float boTotal;
	private List<BookingDetailsReponse> listSer;
	
	public BookingReponse() {
		super();
	}

	
	public BookingReponse(String boId, String employeeId, String userId, String boPhoneNo, String boStatus, String boNote,
			String boStartTime, String boEndTime, Float boTotal, List<BookingDetailsReponse> listSer) {
		super();
		this.boId = boId;
		this.employeeId = employeeId;
		this.userId = userId;
		this.boPhoneNo = boPhoneNo;
		this.boStatus = boStatus;
		this.boNote = boNote;
		this.boStartTime = boStartTime;
		this.boEndTime = boEndTime;
		this.boTotal = boTotal;
		this.listSer = listSer;
	}


	public String getBoId() {
		return boId;
	}


	public void setBoId(String boId) {
		this.boId = boId;
	}


	public String getEmployeeId() {
		return employeeId;
	}


	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getBoPhoneNo() {
		return boPhoneNo;
	}


	public void setBoPhoneNo(String boPhoneNo) {
		this.boPhoneNo = boPhoneNo;
	}


	public String getBoStatus() {
		return boStatus;
	}


	public void setBoStatus(String boStatus) {
		this.boStatus = boStatus;
	}


	public String getBoNote() {
		return boNote;
	}


	public void setBoNote(String boNote) {
		this.boNote = boNote;
	}


	public String getBoStartTime() {
		return boStartTime;
	}


	public void setBoStartTime(String boStartTime) {
		this.boStartTime = boStartTime;
	}


	public String getBoEndTime() {
		return boEndTime;
	}


	public void setBoEndTime(String boEndTime) {
		this.boEndTime = boEndTime;
	}


	public Float getBoTotal() {
		return boTotal;
	}


	public void setBoTotal(Float boTotal) {
		this.boTotal = boTotal;
	}


	public List<BookingDetailsReponse> getlistSer() {
		return listSer;
	}

	public void setlistSer(List<BookingDetailsReponse> listSer) {
		this.listSer = listSer;
	}
}
