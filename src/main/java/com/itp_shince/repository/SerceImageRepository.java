package com.itp_shince.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itp_shince.model.SerceImage;

public interface SerceImageRepository extends JpaRepository<SerceImage, String>{
	@Query(value = "SELECT COUNT(serImg_id) FROM serce_image  WHERE DATE(created_at) = ?", nativeQuery = true)
	Integer countSerImgByDate(LocalDate date);
	@Query(value = "SELECT COUNT(serImg_id) FROM serce_image  WHERE service_Id = ?", nativeQuery = true)
	Integer countSerImgBySerId(String serId);
}
