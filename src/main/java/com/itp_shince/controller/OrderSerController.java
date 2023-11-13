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
import com.itp_shince.dto.reponse.OrdersSerReponse;
import com.itp_shince.dto.reponse.ServiceDetailsReponse;
import com.itp_shince.model.OrderSerDetail;
import com.itp_shince.model.OrdersSer;
import com.itp_shince.model.Serce;
import com.itp_shince.service.OrderSerDetailService;
import com.itp_shince.service.OrderSerService;
import com.itp_shince.service.SerceService;
import com.itp_shince.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class OrderSerController {

	@Autowired
	private OrderSerService service;

	@Autowired
	private OrderSerDetailService OrSerDeSer;
	
	@Autowired
	private SerceService seceSer;

	@Autowired
	private UserService UseSer;

	@Autowired
	private ModelMapper modelMapper;

	HttpHeaders responseHeaders = new HttpHeaders();

	@GetMapping(value = "/OrdersSer")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ORDER_SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<?> get_all_bookings() {
		try {
			List<OrdersSer> entityList = service.getAll();
			List<OrdersSerReponse> dtos = entityList.stream().map(user -> modelMapper.map(user, OrdersSerReponse.class))
					.collect(Collectors.toList());

			for (OrdersSer entity : entityList) {
				List<ServiceDetailsReponse> listDetails = new ArrayList<>();
				for (OrdersSerReponse dto : dtos) {
					if (dto.getOrSerId().equals(entity.getOrSerId())) {
						if (entity.getUsers() != null) {
							dto.setOrSerUserId(entity.getUsers().getUsId());			
						} else {
							dto.setOrSerUserId(null);
						}
						for (OrderSerDetail order : entity.getOrderserdetails()) {
							ServiceDetailsReponse serce = new ServiceDetailsReponse(order.getOrdSerServiceName(),order.getOrdSerServicePrice(),order.getSerce().getSeId());
							listDetails.add(serce);
						}
						dto.setOrSer_Total(entity.getOrSerTotal());
						dto.setlistSer(listDetails);
					}
				}
			}
			ObjectReponse objectReponse = new ObjectReponse("Success", 200,dtos ,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500,0 ,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/OrdersSer/User/{user_id}")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<?> get_all_booking_by_user_id(@PathVariable("userId") String userId) {
		try {
			List<OrdersSer> entityList = service.getAllByUserId(userId);
			if(entityList.size() > 0) {
				List<OrdersSerReponse> dtos = entityList.stream().map(user -> modelMapper.map(user, OrdersSerReponse.class))
						.collect(Collectors.toList());

				for (OrdersSer entity : entityList) {
					List<ServiceDetailsReponse> listDetails = new ArrayList<>();
					for (OrdersSerReponse dto : dtos) {
						if (dto.getOrSerId().equals(entity.getOrSerId())) {
							if(entity.getUsers() != null) {
								dto.setOrSerUserId(entity.getUsers().getUsId());
							}else {
								dto.setOrSerUserId(null);
							}				
							for (OrderSerDetail order : entity.getOrderserdetails()) {
								ServiceDetailsReponse serce = new ServiceDetailsReponse(order.getOrdSerServiceName(),order.getOrdSerServicePrice(),order.getSerce().getSeId());
								listDetails.add(serce);
							}
							dto.setOrSer_Total(entity.getOrSerTotal());
							dto.setlistSer(listDetails);			
						}
					}
				}
				ObjectReponse objectReponse = new ObjectReponse("Success", 200,dtos ,
						120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
			}else {
				ObjectReponse objectReponse = new ObjectReponse("There are no orders for this user id", 404,0 ,
						120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500,0 ,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/OrdersSer/UpdateDate/{date}")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<?> get_all_booking_by_update_date(@PathVariable("date") String date) {
		try {
			List<OrdersSer> entityList = service.getAllByUpdateDate(date);
			List<OrdersSerReponse> dtos = entityList.stream().map(user -> modelMapper.map(user, OrdersSerReponse.class))
					.collect(Collectors.toList());

			for (OrdersSer entity : entityList) {
				List<ServiceDetailsReponse> listDetails = new ArrayList<>();
				for (OrdersSerReponse dto : dtos) {
					if (dto.getOrSerId().equals(entity.getOrSerId())) {
						if (entity.getUsers() != null) {
							dto.setOrSerUserId(entity.getUsers().getUsId());
							for (OrderSerDetail order : entity.getOrderserdetails()) {
								ServiceDetailsReponse serce = new ServiceDetailsReponse(order.getOrdSerServiceName(),order.getOrdSerServicePrice(),order.getSerce().getSeId());
								listDetails.add(serce);
							}
							dto.setlistSer(listDetails);
						} else {
							dto.setOrSerUserId(null);
						}
						dto.setOrSer_Total(entity.getOrSerTotal());

					}
				}
			}
			ObjectReponse objectReponse = new ObjectReponse("Success", 200,dtos ,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500,0 ,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/OrdersSer/{booking_id}")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<?> get_booking_by_id(@PathVariable("booking_id") String id) {
		try {
			OrdersSer entity = service.getById(id);
			List<ServiceDetailsReponse> listDetails = new ArrayList<>();
			if (service.getById(id) != null) {
				OrdersSerReponse dto = modelMapper.map(entity, OrdersSerReponse.class);
				if(entity.getUsers() != null) {
					dto.setOrSerUserId(entity.getUsers().getUsId());
				}else {
					dto.setOrSerUserId(null);
				}
				for (OrderSerDetail order : entity.getOrderserdetails()) {
					ServiceDetailsReponse serce = new ServiceDetailsReponse(order.getOrdSerServiceName(),order.getOrdSerServicePrice(),order.getSerce().getSeId());
					listDetails.add(serce);
				}
				dto.setlistSer(listDetails);
				return new ResponseEntity<>(dto, responseHeaders, HttpStatus.OK);
			}else {
				ObjectReponse objectReponse = new ObjectReponse("Service order does not exist", 404,0 ,
						120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500,0 ,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value = "/OrdersSer")
	public ResponseEntity<?> create_booking(@RequestBody OrdersSerReponse dto) {
		try {
			Date date = Date.from(Instant.now());
			if(dto.getOrSerPhoneNo() != null
			   && dto.getOrSerStartTime() != null
			   && dto.getOrSerEndTime() != null 
			   && dto.getOrSerPhoneNo() != null
			   && dto.getOrSer_Total() != 0
			   && dto.getOrSerStatus().equals("Waiting")
			) {
				OrdersSer entityRequest = modelMapper.map(dto, OrdersSer.class);
				entityRequest.setOrSerId(idOrSerIentity());
				entityRequest.setUsers(UseSer.getById(dto.getOrSerUserId()));
				entityRequest.setCreatedAt(date);
				OrdersSer entity = service.create(entityRequest);
				for (ServiceDetailsReponse ser : dto.getlistSer()) {
					if(ser != null) {
						Serce serce = seceSer.getById(ser.getOrdSerServiceId());
						OrderSerDetail orderSerDetail = new OrderSerDetail(entity,serce,ser.getOrdSerServiceName(),ser.getOrdSerServicePrice());
						OrSerDeSer.create(orderSerDetail);
					}
				}			
				OrdersSerReponse dtoReponse = modelMapper.map(entity, OrdersSerReponse.class);
				if(entity.getUsers() != null) {
					dtoReponse.setOrSerUserId(entity.getUsers().getUsId());

				}else {
					dtoReponse.setOrSerUserId(null);
				}
				ObjectReponse objectReponse = new ObjectReponse("Success", 201, 0,
						120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.CREATED);
			}else {
				ObjectReponse objectReponse = new ObjectReponse("This product cannot be created, please check the order service information", 400, 0,
						120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/OrdersSer/{booking_id}")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ORDER_SERVICE') or hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> update_booking(@PathVariable("booking_id") String id, @RequestBody OrdersSerReponse dto) {
		try {
			Boolean checkStatus = false;
			Date date = Date.from(Instant.now());
			if (service.getById(id) != null) {
				OrdersSer entity = service.getById(id);
				if(entity.getOrSerStatus().equals("Waiting") && (entity.getOrSerStatus().equals("Cancelled") || entity.getOrSerStatus().equals("In process"))) {
					checkStatus = false;
				}else if(entity.getOrSerStatus().equals("In process")&& entity.getOrSerStatus().equals("Completed") ){
					checkStatus = false;
				}else if(entity.getOrSerStatus().equals(dto.getOrSerStatus())){
					checkStatus = false;
				}else {
					checkStatus = true;
				}
				if(checkStatus == false) {
					OrdersSer entityRequest = modelMapper.map(dto, OrdersSer.class);
					if(dto.getOrSerUserId() != null) {
						entityRequest.setUsers(UseSer.getById(dto.getOrSerUserId()));
					}	
					entityRequest.setUpdatedAt(date);
					entityRequest.setOrSerId(id);
					service.create(entityRequest);
					ObjectReponse objectReponse = new ObjectReponse("Success", 200, 0,
							120, "Minute");
					return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
				}else {
					ObjectReponse objectReponse = new ObjectReponse("This booking service cant't update, please check booking again", 400,0 ,
							120, "Minute");
					return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
				}	
			}else {
				ObjectReponse objectReponse = new ObjectReponse("This booking service order does not exist", 404, 0,
						120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}		
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public String idOrSerIentity() {
		LocalDate today = LocalDate.now();
		int numberUser = service.getCountOrSerByDate(today);
		int year = (today.getYear() % 100);
		String number = String.valueOf(numberUser);
		String id = null;
		switch (number.length()) {
		case 1:
			if (numberUser != 9) {
				id = "ORSER" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "000" + (numberUser + 1);
			} else {
				id = "ORSER" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "00" + (numberUser + 1);
			}
			break;
		case 2:
			if (numberUser != 99) {
				id = "ORSER" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "00" + (numberUser + 1);
			} else {
				id = "ORSER" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "0" + (numberUser + 1);
			}
			break;
		case 3:
			if (numberUser != 999) {
				id = "ORSER" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "0" + (numberUser + 1);
			} else {
				id = "ORSER" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "" + (numberUser + 1);
			}
			break;
		case 4:
			id = "ORSER" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "" + (numberUser + 1);
			break;
		default:
			System.out.print("Id error");
			break;
		}
		return id;
	}
}
