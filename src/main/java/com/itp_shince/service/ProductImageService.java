package com.itp_shince.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.itp_shince.model.ProductImage;
import com.itp_shince.repository.ProductImageRepository;

@Service
public class ProductImageService extends ServiceAbstract<ProductImageRepository, ProductImage, String>{
	public Integer countProImgByDate(LocalDate date) {
		return repository.countProImgByDate(date);
	}
	
	public Integer countProImgByProId(String id) {
		return repository.countProImgByProId(id);
	}
	
	
}
