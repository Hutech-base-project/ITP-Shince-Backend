package com.itp_shince.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itp_shince.model.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, String>{
	@Query(value = "SELECT COUNT(proImg_Id) FROM product_image  WHERE DATE(created_at) = ?", nativeQuery = true)
	Integer countProImgByDate(LocalDate date);
	@Query(value = "SELECT COUNT(proImg_Id) FROM product_image  WHERE product_Id = ?", nativeQuery = true)
	Integer countProImgByProId(String proId);
}
