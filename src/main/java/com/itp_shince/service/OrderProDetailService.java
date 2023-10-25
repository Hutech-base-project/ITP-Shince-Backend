package com.itp_shince.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itp_shince.model.OrdersProDetail;
import com.itp_shince.repository.OrderProDetailRepository;

@Service
public class OrderProDetailService extends ServiceAbstract<OrderProDetailRepository, OrdersProDetail, String> {
	public List<OrdersProDetail> getAllByOrProId(String id) {
		return repository.findAllByOrProId(id);
	}
}
