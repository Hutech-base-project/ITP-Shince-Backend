package com.itp_shince.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itp_shince.model.Serce;


public interface SerceRepository extends JpaRepository<Serce, String>{
	@Query(value = "SELECT se_Id FROM serce ORDER BY se_Id DESC LIMIT 1", nativeQuery = true)
	String getLastIdSerce();
	@Query(value = "SELECT COUNT(se_Id) FROM serce WHERE DATE(created_at) = ?", nativeQuery = true)
	Integer countSerByDate(LocalDate date);
}
