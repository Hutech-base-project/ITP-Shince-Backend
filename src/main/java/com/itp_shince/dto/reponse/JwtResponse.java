 package com.itp_shince.dto.reponse;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String refreshToken;
	private String id;
	private String userName;
	private String phoneNumber;
	private List<String> roles;

	public JwtResponse(String accessToken, String refreshToken, String id, String username, String phoneNumber,List<String> roles) {
		this.token = accessToken;
		this.refreshToken = refreshToken;
		this.id = id;
		this.userName = username;
		this.phoneNumber = phoneNumber;
		this.roles = roles;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}
	

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<String> getRoles() {
		return roles;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
