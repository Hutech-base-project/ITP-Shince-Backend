package com.itp_shince.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itp_shince.dto.reponse.ObjectReponse;
import com.itp_shince.dto.reponse.WhishListReponse;
import com.itp_shince.model.WhishList;
import com.itp_shince.service.WhishListService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class WhishListController {
	@Autowired
	WhishListService service;
	
	@Autowired
	private ModelMapper modelMapper;
	
	HttpHeaders responseHeaders = new HttpHeaders();
	
	@GetMapping("/WhishList/{user_id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getById(@PathVariable("user_id") String id){		
		try {
			List<WhishList> entityList = service.getAll();
			entityList =  entityList.stream().filter(n -> n.getUsers().getUsId().equals(id)).toList();
			List<WhishListReponse> dtos = entityList.stream().map(pro -> modelMapper.map(pro, WhishListReponse.class))
					.collect(Collectors.toList());
			for (WhishList entity : entityList) {
				for (WhishListReponse whishListReponse : dtos) {
					if(whishListReponse.getWhlId()== entity.getWhlId()) {
						whishListReponse.setProductId(entity.getProduct().getProId());
						whishListReponse.setSerceId(entity.getSerce().getSeId());
						whishListReponse.setUserId(entity.getUsers().getUsId());
					}
				}
			}	
			ObjectReponse objectReponse = new ObjectReponse("Success", 200,dtos ,120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@DeleteMapping(value = "/WhishList/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deleteWhishList(@PathVariable("id") Integer id) {	
		try {
			WhishList whishList = service.getById(id);
			if (service.getById(id) != null ) {
				service.delete(whishList);	
				ObjectReponse objectReponse = new ObjectReponse("Success", 200,0 ,120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
			}else {
				ObjectReponse objectReponse = new ObjectReponse("Category doesn't exists yet", 404, 0,
						0, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
