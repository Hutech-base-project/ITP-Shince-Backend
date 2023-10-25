package com.itp_shince.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itp_shince.model.UserDetailsImpl;
import com.itp_shince.model.Users;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserService service;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String phoneNumber) {
    try {
    	Users user = service.getByPhoneNumber(phoneNumber);
        return UserDetailsImpl.build(user);
	} catch (UsernameNotFoundException e) {
		return null;
	}
  }
}
