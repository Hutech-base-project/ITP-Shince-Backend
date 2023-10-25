package com.itp_shince.dto.request;

public class LoginRequest {
	private String phoneNumber;
	private String password;
	private String otp;

	public LoginRequest() {
		super();
	}

	
	public LoginRequest(String phoneNumber, String password, String otp) {
		super();
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.otp = otp;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getOtp() {
		return otp;
	}


	public void setOtp(String otp) {
		this.otp = otp;
	}
	
	
}
