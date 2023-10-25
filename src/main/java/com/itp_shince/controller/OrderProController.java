package com.itp_shince.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itp_shince.dto.reponse.ObjectReponse;
import com.itp_shince.dto.reponse.OrderProDetailsReponse;
import com.itp_shince.dto.reponse.OrderProReponse;
import com.itp_shince.dto.request.OrderProRequest;
import com.itp_shince.model.OrdersPro;
import com.itp_shince.model.OrdersProDetail;
import com.itp_shince.model.Product;
import com.itp_shince.service.OrderProDetailService;
import com.itp_shince.service.OrderProService;
import com.itp_shince.service.ProductService;
import com.itp_shince.service.UserService;




@CrossOrigin
@RestController
@RequestMapping("/api")
public class OrderProController {
	
	@Autowired
	private OrderProService service;
	
	@Autowired
	private OrderProDetailService DeProSer;
	
	@Autowired
	private ProductService proSer;
	
	@Autowired
	private UserService UseSer;

	@Autowired
	private ModelMapper modelMapper;

	HttpHeaders responseHeaders = new HttpHeaders();
	@GetMapping(value = "/OrdersPro")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<?> getAll(){
		try {
			List<OrdersPro> entityList = service.getAll();
			List<OrderProReponse> dtos = entityList.stream().map(user -> modelMapper.map(user, OrderProReponse.class))
					.collect(Collectors.toList());
			for (OrdersPro entity : entityList) {
				for (OrderProReponse dto : dtos) {
					if (dto.getOrProId().equals(entity.getOrProId())) {
						if(entity.getUsers() != null) {
							dto.setOrProUserId(entity.getUsers().getUsId());

						}else {
							dto.setOrProUserId(null);

						}
					}
				}
			}
			ObjectReponse objectReponse = new ObjectReponse("Success", 200,dtos ,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/OrdersPro/User/{userId}")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<?> getAllByUserId(@PathVariable("userId") String userId){
		try {
			List<OrdersPro> entityList = service.getAllByUserId(userId);
			List<OrderProReponse> dtos = entityList.stream().map(user -> modelMapper.map(user, OrderProReponse.class))
					.collect(Collectors.toList());
			for (OrdersPro entity : entityList) {
				List<OrderProDetailsReponse> listDetails = new ArrayList<>();
				for (OrderProReponse dto : dtos) {
					if (dto.getOrProId().equals(entity.getOrProId())) {
						if(entity.getUsers() != null) {
							dto.setOrProUserId(entity.getUsers().getUsId());

						}else {
							dto.setOrProUserId(null);

						}
						for (OrdersProDetail order : entity.getOrdersprodetails()) {
							OrderProDetailsReponse detail = new  OrderProDetailsReponse(order.getOrdProProductName(),order.getOrdProProductPrice(),order.getOrdProQuantity(),order.getProduct().getProId());
							listDetails.add(detail);
						}
						dto.setOrProTotal(entity.getOrProTotal());
						dto.setListProId(listDetails);	
					}
				}
			}
			ObjectReponse objectReponse = new ObjectReponse("Success", 200,dtos ,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/OrdersPro/UpdateDate/{date}")
//	@PreAuthorize("hasRole('USER') or hasRole('ORDER_PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<?> getAllByUpdateDate(@PathVariable("date") String date){
		try {
			List<OrdersPro> entityList = service.getAllByUpdateDate(date);
			List<OrderProReponse> dtos = entityList.stream().map(user -> modelMapper.map(user, OrderProReponse.class))
					.collect(Collectors.toList());

			for (OrdersPro entity : entityList) {
				for (OrderProReponse dto : dtos) {
					if (dto.getOrProId().equals(entity.getOrProId())) {
						if(entity.getUsers() != null) {
							dto.setOrProUserId(entity.getUsers().getUsId());

						}else {
							dto.setOrProUserId(null);
						}
					}
				}
			}
			ObjectReponse objectReponse = new ObjectReponse("Success", 200,dtos ,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/OrdersPro/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<?> getById(@PathVariable("id") String id){
		try {
			OrdersPro entity = service.getById(id);
			if (service.getById(id) != null) {
				OrderProReponse dto = modelMapper.map(entity, OrderProReponse.class);
				if(entity.getUsers() != null) {
					dto.setOrProUserId(entity.getUsers().getUsId());
				}else {
					dto.setOrProUserId(null);
				}
				ObjectReponse objectReponse = new ObjectReponse("Success", 200,dto ,
						120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
			}else {
				ObjectReponse objectReponse = new ObjectReponse("This product order does not exist", 404,0 ,
						120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "/OrdersPro")
	public ResponseEntity<?> create(@RequestBody OrderProReponse dto) {
		try {
			Date date = Date.from(Instant.now());
			OrdersPro entityRequest = modelMapper.map(dto, OrdersPro.class);
			entityRequest.setOrProId(idOrProIentity());
			if(dto.getOrProAddress() != null 
			   && dto.getOrProUserName() != null
			   && dto.getOrProAddress() != null
			   && dto.getOrProPhoneNo() != null
			   && dto.getOrProStatus() != null
			   && dto.getOrProPayment() != null
			   && dto.getOrProTotal() != null
			   && dto.getOrProStatus().equals("Wait for confirmation")
			) {
				entityRequest.setCreatedAt(date);
				if(dto.getOrProUserId() != null) {
					entityRequest.setUsers(UseSer.getById(dto.getOrProUserId()));
				}
				entityRequest.setCreatedAt(date);
				OrdersPro entity = service.create(entityRequest);
				for (OrderProDetailsReponse item : dto.getListProId()) {
					if(!item.getProProductName().isEmpty() && item.getProQuantity()!= 0 && item.getProProductPrice() != 0) {
						if(proSer.getById(item.getProductId())!= null) {
							Product product = proSer.getById(item.getProductId());
							OrdersProDetail OrProDeEntity = new OrdersProDetail(entity,product,item.getProProductName(),item.getProProductPrice(),item.getProQuantity());
							DeProSer.create(OrProDeEntity);
						}
					}
				}			
				ObjectReponse objectReponse = new ObjectReponse("Success", 201,dto ,
						120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.CREATED);
			}else {
				ObjectReponse objectReponse = new ObjectReponse("This product order cant't created, please check order again", 400,0 ,
						120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
			}	
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(value = "/OrdersPro/{id}")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ORDER_PRODUCT') or hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody OrderProRequest dto) {
		try {
			Boolean checkStatus = false;
			Date date = Date.from(Instant.now());
			if (service.getById(id) != null) {
				if(dto.getOrProAddress() != null 
						   && dto.getOrProUserName() != null
						   && dto.getOrProAddress() != null
						   && dto.getOrProPhoneNo() != null
						   && dto.getOrProStatus() != null
						   && dto.getOrProPayment() != null
						   && dto.getOrProTotal() != null
						   && dto.getCreatedAt() != null
				) {
					OrdersPro entityRequest = modelMapper.map(dto, OrdersPro.class);
					entityRequest.setUpdatedAt(date);
					
					OrdersPro entity = service.getById(id);
					if(entity.getOrProStatus().equals("Wait for confirmation") && (dto.getOrProStatus().equals("Cancelled") || dto.getOrProStatus().equals("Confirm"))) {
						checkStatus = false;
					}else if(entity.getOrProStatus().equals("Confirm")&& entity.getOrProStatus().equals("Delivery") ){
						checkStatus = false;
					}else if(entity.getOrProStatus().equals("Delivery")&& entity.getOrProStatus().equals("Delivered")){
						checkStatus = false;
					}else if(entity.getOrProStatus().equals(dto.getOrProStatus())){
						checkStatus = false;
					}else {
						checkStatus = true;
					}
					
					if(checkStatus == false) {
						if(dto.getOrProUserId() != null) {
							entityRequest.setUsers(UseSer.getById(dto.getOrProUserId()));
							entityRequest.setOrProId(id);
							entityRequest.setUpdatedAt(date);
							service.create(entityRequest);
							ObjectReponse objectReponse = new ObjectReponse("Success", 200,0 ,
									120, "Minute");
							return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
						}else {
							entityRequest.setOrProId(id);
							entityRequest.setUpdatedAt(date);
							service.create(entityRequest);
							ObjectReponse objectReponse = new ObjectReponse("Success", 200,0 ,
									120, "Minute");
							return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
						}
					}else {
						ObjectReponse objectReponse = new ObjectReponse("This product order cant't update, please check order again", 400,0 ,
								120, "Minute");
						return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
					}
				}else {
					ObjectReponse objectReponse = new ObjectReponse("This product order cant't update, please check order again", 400,0 ,
							120, "Minute");
					return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
				}
				
			}else {
				ObjectReponse objectReponse = new ObjectReponse("This product order does not exist", 404,0 ,
						120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public String idOrProIentity() {
		LocalDate today = LocalDate.now();
		int numberUser = service.getCountOrProByDate(today);
		int year = (today.getYear() % 100);
		String number = String.valueOf(numberUser);
		String id = null;
		switch (number.length()) {
		case 1:
			if (numberUser != 9) {
				id = "ORPRO" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "000" + (numberUser + 1);
			} else {
				id = "ORPRO" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "00" + (numberUser + 1);
			}
			break;
		case 2:
			if (numberUser != 99) {
				id = "ORPRO" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "00" + (numberUser + 1);
			} else {
				id = "ORPRO" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "0" + (numberUser + 1);
			}
			break;
		case 3:
			if (numberUser != 999) {
				id = "ORPRO" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "0" + (numberUser + 1);
			} else {
				id = "ORPRO" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "" + (numberUser + 1);
			}
			break;
		case 4:
			id = "ORPRO" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "" + (numberUser + 1);
			break;
		default:
			System.out.print("Id error");
			break;
		}
		return id;
	}
}
