package com.itp_shince.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@ConfigurationProperties(prefix = "twilio")
public class TwilioConfig {
    private String accountSid ;
    private String authToken;
    private String phoneNumber;
    
	public TwilioConfig() {
		super();
	}
	public TwilioConfig(String accountSid, String authToken, String phoneNumber) {
		super();
		this.accountSid = accountSid;
		this.authToken = authToken;
		this.phoneNumber = phoneNumber;
	}
	public String getAccountSid() {
		return accountSid;
	}
	public String getAuthToken() {
		return authToken;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setAccountSid(String accountSid) {
		this.accountSid = accountSid;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}    
	
}
