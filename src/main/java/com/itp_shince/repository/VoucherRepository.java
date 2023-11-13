package com.itp_shince.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itp_shince.model.Voucher;

public interface VoucherRepository extends JpaRepository<Voucher, String>{
	@Query(value = "SELECT * FROM voucher WHERE vo_type_auto = 0", nativeQuery = true)
	List<Voucher> findAll();
	@Query(value = "SELECT COUNT(vo_Id) FROM voucher WHERE DATE(created_at) = ?", nativeQuery = true)
	Integer countVoucherByDate(LocalDate date);
}
