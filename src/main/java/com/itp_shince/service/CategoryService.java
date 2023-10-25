package com.itp_shince.service;

import org.springframework.stereotype.Service;

import com.itp_shince.model.Category;
import com.itp_shince.repository.CategoryRepository;



@Service
public class CategoryService extends ServiceAbstract<CategoryRepository, Category, Integer> {
	
	public Category getByNameAndParentsId(String cateName, Integer parId) {
		return repository.getCateByNameAndPrId(cateName, parId);
	}
	
	public Category getByParentsId(Integer parId) {
		return repository.getCateByPrId(parId);
	}
}
