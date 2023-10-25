package com.itp_shince.dto.request;

public class OtpRequest {
	private String otp;
	private String phoneNumber;

	
	public OtpRequest() {
		super();
	}

	public OtpRequest(String otp, String phoneNumber) {
		super();
		this.otp = otp;
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}
	
}
