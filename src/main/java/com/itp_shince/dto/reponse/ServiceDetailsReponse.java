package com.itp_shince.dto.reponse;

public class ServiceDetailsReponse {
	private String ordSerServiceName;
	private float ordSerServicePrice;
	private String ordSerServiceId ;
	
	public ServiceDetailsReponse() {
		super();
	}

	public ServiceDetailsReponse(String ordSerServiceName, float ordSerServicePrice, String ordSerServiceId) {
		super();
		this.ordSerServiceName = ordSerServiceName;
		this.ordSerServicePrice = ordSerServicePrice;
		this.ordSerServiceId = ordSerServiceId;
	}

	public String getOrdSerServiceName() {
		return ordSerServiceName;
	}

	public void setOrdSerServiceName(String ordSerServiceName) {
		this.ordSerServiceName = ordSerServiceName;
	}

	public float getOrdSerServicePrice() {
		return ordSerServicePrice;
	}

	public void setOrdSerServicePrice(float ordSerServicePrice) {
		this.ordSerServicePrice = ordSerServicePrice;
	}

	public String getOrdSerServiceId() {
		return ordSerServiceId;
	}

	public void setOrdSerServiceId(String ordSerServiceId) {
		this.ordSerServiceId = ordSerServiceId;
	}

	
}
