package com.itp_shince.service;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.itp_shince.dto.reponse.JwtResponse;

@Service
public class SessionService {
	private static final Integer EXPIRE_MINS = 80;
	private LoadingCache<String, JwtResponse> otpCache;

	public SessionService() {
		super();
		otpCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
				.build(new CacheLoader<String, JwtResponse>() {
					public JwtResponse load(String key) {
						return null;
					}
				});
	}

	public JwtResponse saveSession(String key,JwtResponse jwtResponse) {	
		otpCache.put(key, jwtResponse);
		return jwtResponse;
	}

	public JwtResponse getSession(String key) {
		try {
			return otpCache.get(key);
		} catch (Exception e) {
			return null;
		}
	}

	public void clearOTP(String key) {
		otpCache.invalidate(key);
	}
}
