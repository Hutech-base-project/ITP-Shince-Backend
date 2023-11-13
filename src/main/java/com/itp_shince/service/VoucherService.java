package com.itp_shince.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.itp_shince.model.Voucher;
import com.itp_shince.repository.VoucherRepository;

@Service
public class VoucherService extends ServiceAbstract<VoucherRepository, Voucher, String>{
	public Integer getCountVoucherByDate(LocalDate date) {
		return repository.countVoucherByDate(date);
	}
	
}
