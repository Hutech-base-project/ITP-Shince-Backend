package com.itp_shince.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itp_shince.model.OrderSerDetail;
import com.itp_shince.repository.OrderSerceDetailRepository;

@Service
public class OrderSerDetailService extends ServiceAbstract<OrderSerceDetailRepository, OrderSerDetail, Integer>{
	public List<OrderSerDetail> getAllByOrSerId(String id) {
		return repository.findAllByOrSerId(id);
	}
}
