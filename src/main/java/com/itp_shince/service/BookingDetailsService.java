package com.itp_shince.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itp_shince.model.BookingDetails;
import com.itp_shince.repository.BookingDetailsRepository;

@Service
public class BookingDetailsService extends ServiceAbstract<BookingDetailsRepository, BookingDetails, Integer>{
	public List<BookingDetails> getAllByOrSerId(String id) {
		return repository.findAllByOrSerId(id);
	}
}
