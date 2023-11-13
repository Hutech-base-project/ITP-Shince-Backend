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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itp_shince.dto.reponse.ObjectReponse;
import com.itp_shince.dto.reponse.RoleReponse;
import com.itp_shince.model.Role;
import com.itp_shince.service.RoleService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class RoleController {
	
	@Autowired
	private RoleService service;
	
	@Autowired
	private ModelMapper modelMapper;

	HttpHeaders responseHeaders = new HttpHeaders();
	
	@GetMapping(value = "/Role")
//	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> get_all_roles(){
		List<Role> entityList = service.getAll();
		List<RoleReponse> dtos = entityList.stream().map(rol -> modelMapper.map(rol, RoleReponse.class))
				.collect(Collectors.toList());
		try {
			ObjectReponse objectReponse = new ObjectReponse("Success", 200,dtos ,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping(value = "/Role/{role_id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> get_role_by_id(@PathVariable("role_id") Integer id){
		try {		 
			Role entity = service.getById(id);
			if (service.getById(id) != null) {
				RoleReponse dto = modelMapper.map(entity, RoleReponse.class);
				ObjectReponse objectReponse = new ObjectReponse("Success", 200,dto ,
						120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
			}else {
				ObjectReponse objectReponse = new ObjectReponse("This role was not found", 404, 0,
						120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
