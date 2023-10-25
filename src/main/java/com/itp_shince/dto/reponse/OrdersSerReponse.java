package com.itp_shince.dto.reponse;

import java.util.List;

import com.itp_shince.dto.AbstractDTO;

public class OrdersSerReponse extends AbstractDTO<OrdersSerReponse>{
	private String orSerId;
	private String orSerUserId ;
	private String orSerPhoneNo;
	private String orSerStatus;
	private String orSerStartTime;
	private String orSerEndTime;
	private float orSer_Total;
	private List<ServiceDetailsReponse> listSer;
	
	public OrdersSerReponse() {
		super();
	}

	public OrdersSerReponse(String orSerId, String orSerUserId, String orSerPhoneNo, String orSerStatus,
			String orSerStartTime, String orSerEndTime, Integer orSer_Total, List<ServiceDetailsReponse> listSer) {
		super();
		this.orSerId = orSerId;
		this.orSerUserId = orSerUserId;
		this.orSerPhoneNo = orSerPhoneNo;
		this.orSerStatus = orSerStatus;
		this.orSerStartTime = orSerStartTime;
		this.orSerEndTime = orSerEndTime;
		this.orSer_Total = orSer_Total;
		this.listSer = listSer;
	}

	public String getOrSerId() {
		return orSerId;
	}

	public void setOrSerId(String orSerId) {
		this.orSerId = orSerId;
	}

	public String getOrSerUserId() {
		return orSerUserId;
	}

	public void setOrSerUserId(String orSerUserId) {
		this.orSerUserId = orSerUserId;
	}

	public String getOrSerPhoneNo() {
		return orSerPhoneNo;
	}

	public void setOrSerPhoneNo(String orSerPhoneNo) {
		this.orSerPhoneNo = orSerPhoneNo;
	}

	public String getOrSerStatus() {
		return orSerStatus;
	}

	public void setOrSerStatus(String orSerStatus) {
		this.orSerStatus = orSerStatus;
	}

	public String getOrSerStartTime() {
		return orSerStartTime;
	}

	public void setOrSerStartTime(String orSerStartTime) {
		this.orSerStartTime = orSerStartTime;
	}

	public String getOrSerEndTime() {
		return orSerEndTime;
	}

	public void setOrSerEndTime(String orSerEndTime) {
		this.orSerEndTime = orSerEndTime;
	}

	public float getOrSer_Total() {
		return orSer_Total;
	}

	public void setOrSer_Total(float orSer_Total) {
		this.orSer_Total = orSer_Total;
	}

	public List<ServiceDetailsReponse> getlistSer() {
		return listSer;
	}

	public void setlistSer(List<ServiceDetailsReponse> listSer) {
		this.listSer = listSer;
	}
}
