package com.itp_shince.dto.reponse;

public class ObjectReponse {
	private String responseMessage = "";
	private int responseStatus = 0;
	private Object responseData = 0;
	private int expireTimeValue = 0;
	private String expireTimeUnit = "";
	
	public ObjectReponse(String responseMessage, int responseStatus, Object responseData, int expireTimeValue,
			String expireTimeUnit) {
		super();
		this.responseMessage = responseMessage;
		this.responseStatus = responseStatus;
		this.responseData = responseData;
		this.expireTimeValue = expireTimeValue;
		this.expireTimeUnit = expireTimeUnit;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public int getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(int responseStatus) {
		this.responseStatus = responseStatus;
	}

	public Object getResponseData() {
		return responseData;
	}

	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}

	public int getExpireTimeValue() {
		return expireTimeValue;
	}

	public void setExpireTimeValue(int expireTimeValue) {
		this.expireTimeValue = expireTimeValue;
	}

	public String getExpireTimeUnit() {
		return expireTimeUnit;
	}

	public void setExpireTimeUnit(String expireTimeUnit) {
		this.expireTimeUnit = expireTimeUnit;
	}
}
