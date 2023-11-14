package com.itp_shince.dto.reponse;

public class BookingDetailsReponse {
	private String bodServiceId;
	private String bodServiceName;
	private float  bodServicePrice;
	
	public BookingDetailsReponse( String bodServiceName, float bodServicePrice, String bodServiceId) {
		super();
		this.bodServiceName = bodServiceName;
		this.bodServicePrice = bodServicePrice;
		this.bodServiceId = bodServiceId;
	}

	public String getBodServiceId() {
		return bodServiceId;
	}

	public void setBodServiceId(String bodServiceId) {
		this.bodServiceId = bodServiceId;
	}

	public String getBodServiceName() {
		return bodServiceName;
	}

	public void setBodServiceName(String bodServiceName) {
		this.bodServiceName = bodServiceName;
	}

	public float getBodServicePrice() {
		return bodServicePrice;
	}

	public void setBodServicePrice(float bodServicePrice) {
		this.bodServicePrice = bodServicePrice;
	}	
}
