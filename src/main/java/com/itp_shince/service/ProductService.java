package com.itp_shince.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itp_shince.model.Product;
import com.itp_shince.repository.ProductRepository;

@Service
public class ProductService extends ServiceAbstract<ProductRepository, Product, String>{
	public String getIdLast() {
		return repository.getLastIdProduct();
	}

	public Integer getCountProByDate(LocalDate date) {
		return repository.countProByDate(date);
	}
	
	public List<Product> getProByCateIdProOn(Integer id){
		return repository.getProByCateProOn(id);
	}
}
