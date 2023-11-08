package com.itp_shince.dto.request;


public class SignupRequest {
	private String userName;
	private String phoneNumber;
	private String password;
	private String otp;
	
	public SignupRequest() {
		super();
	}

	public SignupRequest(String userName, String phoneNumber, String password, String otp) {
		super();
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.otp = otp;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
