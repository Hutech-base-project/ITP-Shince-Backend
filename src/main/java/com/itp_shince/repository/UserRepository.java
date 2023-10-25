package com.itp_shince.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itp_shince.model.Users;

public interface UserRepository extends JpaRepository<Users, String>{
	@Query(value = "SELECT COUNT(us_Id) FROM users WHERE DATE(created_at) = ?", nativeQuery = true)
	Integer countUserByDate(LocalDate date);
	
	@Query(value = "SELECT * FROM users WHERE us_PhoneNo = ?", nativeQuery = true)
	Users getUserByPhoneNumber (String phoneNumber);
}
