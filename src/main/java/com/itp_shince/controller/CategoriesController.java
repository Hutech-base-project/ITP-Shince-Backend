package com.itp_shince.controller;

import java.time.Instant;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itp_shince.dto.reponse.CategoryReponse;
import com.itp_shince.dto.reponse.ObjectReponse;
import com.itp_shince.dto.request.CategoryRequest;
import com.itp_shince.model.Category;
import com.itp_shince.service.CategoryService;
import com.itp_shince.service.ProductService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CategoriesController {
	
	@Autowired
	CategoryService service;
	
	@Autowired
	ProductService servicePro;
	
	@Autowired
	private ModelMapper modelMapper;
	
	HttpHeaders responseHeaders = new HttpHeaders();
	@GetMapping("/Category")
	public ResponseEntity<?> getAll(){		
		try {
			ObjectReponse objectReponse = new ObjectReponse("Success", 200, service.getAll()
					.stream()
					.map(post -> modelMapper.map(post, CategoryReponse.class))
					.collect(Collectors.toList())
					,
					120, "Minute");
			return new ResponseEntity<>(
					objectReponse, 
					responseHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	@GetMapping("/Category/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Integer id){		
		try {
			Category entity = service.getById(id);
			if(entity != null) {
				CategoryReponse dtoResponse = modelMapper.map(entity,CategoryReponse.class);
				ObjectReponse objectReponse = new ObjectReponse("Success", 200,dtoResponse ,120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
			}else {
				ObjectReponse objectReponse = new ObjectReponse("This category could not be found", 404,null ,120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping("/Category")
//	@PreAuthorize("hasRole('MODERATOR') and hasRole('CATEGORY') or hasRole('ADMIN')")
	public ResponseEntity<?> create_category(@RequestBody CategoryRequest dtoRequest){
		try {
			Date date = Date.from(Instant.now());
			Category entityRequest = modelMapper.map(dtoRequest, Category.class);
			if(entityRequest.getCateName() != null) {
				Category checkCate = service.getByNameAndParentsId(entityRequest.getCateName(), entityRequest.getCateIdParent());
				if(checkCate == null) {
					if(entityRequest.getCateIdParent() == null) {
						entityRequest.setCateIdParent(0);
					}
					entityRequest.setCreatedAt(date);
					entityRequest.setIsDelete(false);
					service.create(entityRequest);
					ObjectReponse objectReponse = new ObjectReponse("Success", 201, 0,
							0, "Minute");
					return new ResponseEntity<>(objectReponse,responseHeaders,HttpStatus.CREATED);
				}else {
					ObjectReponse objectReponse = new ObjectReponse("Category already exists", 400, 0,
							120, "Minute");
					return new ResponseEntity<>(objectReponse,responseHeaders,HttpStatus.BAD_REQUEST);
				}
				
			}else {
				ObjectReponse objectReponse = new ObjectReponse("This directory cannot be initialized", 400, 0,
						120, "Minute");
				return new ResponseEntity<>(objectReponse,responseHeaders,HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PutMapping("/Category")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('CATEGORY') or hasRole('ADMIN')")
	public ResponseEntity<?> update_category(@RequestBody CategoryRequest dto){
		try {
			Date date = Date.from(Instant.now());
			Category entityRequest = modelMapper.map(dto, Category.class);
			if(entityRequest.getCateName() != null
			&& entityRequest.getCateId() != null
			&& entityRequest.getCateIdParent() != null) {
				Category checkCateID = service.getById(entityRequest.getCateId());
				Category checkCateName = service.getByNameAndParentsId(entityRequest.getCateName(), entityRequest.getCateIdParent());
				if(checkCateID != null) {
					if((checkCateName != null && checkCateID.getCateName().equals(entityRequest.getCateName())) || checkCateName == null) {
						entityRequest.setUpdatedAt(date);
						service.create(entityRequest);
						ObjectReponse objectReponse = new ObjectReponse("Success", 200, 0,
								0, "Minute");
						return new ResponseEntity<>(objectReponse,responseHeaders,HttpStatus.OK);
					}else {
						ObjectReponse objectReponse = new ObjectReponse("Category name can't duplicate", 400, 0,
								120, "Minute");
						return new ResponseEntity<>(objectReponse,responseHeaders,HttpStatus.BAD_REQUEST);
					}
				}else {
					ObjectReponse objectReponse = new ObjectReponse("Category doesn't exists yet", 404, 0,
							120, "Minute");
					return new ResponseEntity<>(objectReponse,responseHeaders,HttpStatus.NOT_FOUND);
				}
				
			}else {
				ObjectReponse objectReponse = new ObjectReponse("This directory cannot be updated", 400, 0,
						120, "Minute");
				return new ResponseEntity<>(objectReponse,responseHeaders,HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@DeleteMapping(value = "/Category/{id}")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('CATEGORY') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteCategory(@PathVariable("id") Integer id) {	
		try {
			Category entity = service.getById(id);
			if (service.getById(id) != null ) {
				if(entity.getProducts().size() == 0 && entity.getCateIdParent()==0 && service.getByParentsId(id) == null) {
					service.delete(entity);
					ObjectReponse objectReponse = new ObjectReponse("Success", 200, 0,
							0, "Minute");
					return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
				}else if(entity.getProducts().size() == 0 && entity.getCateIdParent()!=0){
					service.delete(entity);
					ObjectReponse objectReponse = new ObjectReponse("Success", 200, 0,
							0, "Minute");
					return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
				}else if(servicePro.getProByCateIdProOn(id) == null){
					entity.setIsDelete(true);
					service.create(entity);
					ObjectReponse objectReponse = new ObjectReponse("Success", 200, 0,
							0, "Minute");
					return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
				}else {
					ObjectReponse objectReponse = new ObjectReponse("This category cannot be deleted", 400, 0,
							0, "Minute");
					return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
				}
				
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
