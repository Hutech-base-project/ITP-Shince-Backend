package com.itp_shince.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itp_shince.model.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
	@Query(value = "SELECT * FROM refreshtoken WHERE JwtId  = ?", nativeQuery = true)
	RefreshToken findByRefreshToken(String JwtId);
}
