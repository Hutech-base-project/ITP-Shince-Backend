package com.itp_shince.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itp_shince.model.Product;

public interface ProductRepository extends JpaRepository<Product, String>{
	@Query(value = "SELECT pro_id FROM product ORDER BY pro_id DESC LIMIT 1", nativeQuery = true)
	String getLastIdProduct();
	@Query(value = "SELECT COUNT(pro_id) FROM product WHERE DATE(created_at) = ?", nativeQuery = true)
	Integer countProByDate(LocalDate date);
	@Query(value = "SELECT * FROM `product` WHERE category_id = ? AND pro_turn_on = true", nativeQuery = true)
	List<Product> getProByCateProOn(Integer id);	
}
