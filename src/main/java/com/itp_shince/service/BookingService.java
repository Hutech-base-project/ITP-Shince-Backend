package com.itp_shince.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itp_shince.model.Booking;
import com.itp_shince.repository.BookingRepository;

@Service
public class BookingService extends ServiceAbstract<BookingRepository, Booking, String>{
	public Integer getCountOrSerByDate(LocalDate date) {
		return repository.countOrSerByDate(date);
	}

	public List<Booking> getAllByUserId(String id) {
		return repository.findAllByUserId(id);
	}
	
	public List<Booking> getAllByUpdateDate(String date) {
		return repository.findAllByUpdateDay(date+"%");
	}
}
