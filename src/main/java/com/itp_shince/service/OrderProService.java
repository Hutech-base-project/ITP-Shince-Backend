package com.itp_shince.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itp_shince.model.OrdersPro;
import com.itp_shince.repository.OrderProRepository;

@Service
public class OrderProService extends ServiceAbstract<OrderProRepository, OrdersPro, String>{
	public Integer getCountOrProByDate(LocalDate date) {
		return repository.countOrProByDate(date);
	}
	public List<OrdersPro> getAllByUserId(String id) {
		return repository.findAllByUserId(id);
	}
	
	public List<OrdersPro> getAllByUpdateDate(String date) {
		return repository.findAllByUpdateDay(date+"%");
	}
}
