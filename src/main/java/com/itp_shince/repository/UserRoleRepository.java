package com.itp_shince.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itp_shince.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer>{
	@Query(value = "SELECT * FROM user_role WHERE usr_UserId = ?", nativeQuery = true)
	List<UserRole> findAllByUserId(String id);
}
