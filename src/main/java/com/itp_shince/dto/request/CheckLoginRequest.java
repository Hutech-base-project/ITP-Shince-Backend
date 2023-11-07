package com.itp_shince.dto.request;

public class CheckLoginRequest {
	private String phoneNumber;
	private String password;
		
	public CheckLoginRequest() {
		super();
	}

	public CheckLoginRequest(String phoneNumber, String password) {
		super();
		this.phoneNumber = phoneNumber;
		this.password = password;
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
}
