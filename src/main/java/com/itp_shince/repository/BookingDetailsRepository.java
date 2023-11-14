package com.itp_shince.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itp_shince.model.BookingDetails;

public interface BookingDetailsRepository extends JpaRepository<BookingDetails, Integer> {
	@Query(value = "SELECT * FROM orderserdetail WHERE ordSer_OrderId = ?", nativeQuery = true)
	List<BookingDetails> findAllByOrSerId(String id);
}
