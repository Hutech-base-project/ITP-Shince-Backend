package com.itp_shince.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itp_shince.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, String>{
	@Query(value = "SELECT * FROM ordersser WHERE orSer_UserId  = ?", nativeQuery = true)
	List<Booking> findAllByUserId(String id);
	@Query(value = "SELECT COUNT(orSer_id) FROM ordersser WHERE DATE(created_at) LIKE ?", nativeQuery = true)
	Integer countOrSerByDate(LocalDate date);
	@Query(value = "SELECT * FROM ordersser WHERE updated_at LIKE ? and orSer_Status = 'Đã hoàn thành'", nativeQuery = true)
	List<Booking> findAllByUpdateDay(String date);
}
