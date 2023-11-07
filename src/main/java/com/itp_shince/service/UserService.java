package com.itp_shince.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itp_shince.model.Users;
import com.itp_shince.repository.UserRepository;


@Service
public class UserService extends ServiceAbstract<UserRepository, Users, String> {
	@Autowired
	private UserRepository repository;
	
	public Boolean checkPhoneNumber(String phoneNumber) {
		if(repository.getUserByPhoneNumber(phoneNumber) != null) {
			return true;
	}
		return false;
	}
	
	public Integer getCountUserByDate(LocalDate date) {
		return repository.countUserByDate(date);
	}
	public Users getByPhoneNumber(String phoneNumber) {
		return repository.getUserByPhoneNumber(phoneNumber);
	}
	
}
