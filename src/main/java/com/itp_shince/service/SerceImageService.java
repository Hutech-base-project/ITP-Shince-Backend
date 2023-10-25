package com.itp_shince.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.itp_shince.model.SerceImage;
import com.itp_shince.repository.SerceImageRepository;

@Service
public class SerceImageService extends ServiceAbstract<SerceImageRepository, SerceImage, String>{
	public Integer countSerImgByDate(LocalDate date) {
		return repository.countSerImgByDate(date);
	}
	
	public Integer countSerImgBySerId(String id) {
		return repository.countSerImgBySerId(id);
	}
}
