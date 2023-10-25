package com.itp_shince.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.itp_shince.model.Serce;
import com.itp_shince.repository.SerceRepository;


@Service
public class SerceService extends ServiceAbstract<SerceRepository, Serce, String>{

	public String getIdLast() {
		return repository.getLastIdSerce();
	}

	public Integer getCountSerByDate(LocalDate date) {
		return repository.countSerByDate(date);
	}
}
