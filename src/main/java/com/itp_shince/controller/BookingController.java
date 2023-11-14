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

import com.itp_shince.dto.reponse.BookingDetailsReponse;
import com.itp_shince.dto.reponse.BookingReponse;
import com.itp_shince.dto.reponse.ObjectReponse;
import com.itp_shince.dto.request.BookingDetailsRequest;
import com.itp_shince.dto.request.BookingRequest;
import com.itp_shince.model.Booking;
import com.itp_shince.model.BookingDetails;
import com.itp_shince.model.Serce;
import com.itp_shince.service.BookingDetailsService;
import com.itp_shince.service.BookingService;
import com.itp_shince.service.SerceService;
import com.itp_shince.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class BookingController {

	@Autowired
	private BookingService service;

	@Autowired
	private BookingDetailsService OrSerDeSer;
	
	@Autowired
	private SerceService seceSer;

	@Autowired
	private UserService UseSer;

	@Autowired
	private ModelMapper modelMapper;

	HttpHeaders responseHeaders = new HttpHeaders();

	@GetMapping(value = "/Booking")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ORDER_SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<?> get_all_bookings() {
		try {
			List<Booking> entityList = service.getAll();
			List<BookingReponse> dtos = entityList.stream().map(booking -> modelMapper.map(booking, BookingReponse.class))
					.collect(Collectors.toList());

			for (Booking entity : entityList) {
				List<BookingDetailsReponse> listDetails = new ArrayList<>();
				for (BookingReponse dto : dtos) {
					if (dto.getBoId().equals(entity.getBoId())) {
						if (entity.getUsersByBoUserId() != null) {
							dto.setUserId(entity.getUsersByBoUserId().getUsId());			
						} else {
							dto.setUserId(null);
						}
						if (entity.getUsersByBoEmployeeId() != null) {
							dto.setEmployeeId(entity.getUsersByBoEmployeeId().getUsId());		
						} else {
							dto.setEmployeeId(null);
						}
						for (BookingDetails booking : entity.getBookingdetailses()) {
							BookingDetailsReponse serce = new BookingDetailsReponse(booking.getBodServiceName(),booking.getBodServicePrice(),booking.getSerce().getSeId());
							listDetails.add(serce);
						}
						dto.setBoTotal(entity.getBoTotal());
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

	@GetMapping(value = "/Booking/User/{user_id}")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<?> get_all_booking_by_user_id(@PathVariable("userId") String userId) {
		try {
			List<Booking> entityList = service.getAllByUserId(userId);
			if(entityList.size() > 0) {
				List<BookingReponse> dtos = entityList.stream().map(booking -> modelMapper.map(booking, BookingReponse.class))
						.collect(Collectors.toList());

				for (Booking entity : entityList) {
					List<BookingDetailsReponse> listDetails = new ArrayList<>();
					for (BookingReponse dto : dtos) {
						if (dto.getBoId().equals(entity.getBoId())) {
							if (entity.getUsersByBoEmployeeId() != null) {
								dto.setEmployeeId(entity.getUsersByBoEmployeeId().getUsId());		
							} else {
								dto.setEmployeeId(null);
							}
							for (BookingDetails booking : entity.getBookingdetailses()) {
								BookingDetailsReponse serce = new BookingDetailsReponse(booking.getBodServiceName(),booking.getBodServicePrice(),booking.getSerce().getSeId());
								listDetails.add(serce);
							}
							dto.setUserId(entity.getUsersByBoUserId().getUsId());		
							dto.setBoTotal(entity.getBoTotal());
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
	
	@GetMapping(value = "/Booking/UpdateDate/{date}")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<?> get_all_booking_by_update_date(@PathVariable("date") String date) {
		try {
			List<Booking> entityList = service.getAllByUpdateDate(date);
			List<BookingReponse> dtos = entityList.stream().map(booking -> modelMapper.map(booking, BookingReponse.class))
					.collect(Collectors.toList());

			for (Booking entity : entityList) {
				List<BookingDetailsReponse> listDetails = new ArrayList<>();
				for (BookingReponse dto : dtos) {
					if (dto.getBoId().equals(entity.getBoId())) {
						if (entity.getUsersByBoUserId() != null) {
							dto.setUserId(entity.getUsersByBoUserId().getUsId());			
						} else {
							dto.setUserId(null);
						}
						if (entity.getUsersByBoEmployeeId() != null) {
							dto.setEmployeeId(entity.getUsersByBoEmployeeId().getUsId());		
						} else {
							dto.setEmployeeId(null);
						}
						for (BookingDetails booking : entity.getBookingdetailses()) {
							BookingDetailsReponse serce = new BookingDetailsReponse(booking.getBodServiceName(),booking.getBodServicePrice(),booking.getSerce().getSeId());
							listDetails.add(serce);
						}
						dto.setBoTotal(entity.getBoTotal());
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

	@GetMapping(value = "/Booking/{booking_id}")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<?> get_booking_by_id(@PathVariable("booking_id") String id) {
		try {
			Booking entity = service.getById(id);
			List<BookingDetailsReponse> listDetails = new ArrayList<>();
			if (service.getById(id) != null) {
				BookingReponse dto = modelMapper.map(entity, BookingReponse.class);
				if(entity.getUsersByBoUserId() != null) {
					dto.setUserId(entity.getUsersByBoUserId().getUsId());
				}else {
					dto.setUserId(null);
				}
				for (BookingDetails booking : entity.getBookingdetailses()) {
					BookingDetailsReponse serce = new BookingDetailsReponse(booking.getBodServiceName(),booking.getBodServicePrice(),booking.getSerce().getSeId());
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
	
	
	@PostMapping(value = "/Booking")
	public ResponseEntity<?> create_booking(@RequestBody BookingRequest dto) {
		try {
			Date date = Date.from(Instant.now());
			if(dto.getBoPhoneNo() != null
			   && dto.getBoStartTime() != null
			   && dto.getBoEndTime() != null 
			   && dto.getBoTotal() != 0
			   && dto.getBoStatus().equals("Waiting")
			) {
				Booking entityRequest = modelMapper.map(dto, Booking.class);
				entityRequest.setBoId(idOrSerIentity());
				entityRequest.setUsersByBoUserId(UseSer.getById(dto.getUserId()));
				if(!dto.getEmployeeId().isEmpty() || dto.getEmployeeId() != null) {
					entityRequest.setUsersByBoEmployeeId(UseSer.getById(dto.getEmployeeId()));
				}
				entityRequest.setCreatedAt(date);
				Booking entity = service.create(entityRequest);
				for (BookingDetailsRequest ser : dto.getlistSer()) {
					if(ser != null) {
						Serce serce = seceSer.getById(ser.getBodServiceId());
						BookingDetails orderSerDetail = new BookingDetails(entity,serce,ser.getBodServiceName(),ser.getBodServicePrice());
						OrSerDeSer.create(orderSerDetail);
					}
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

	@PutMapping(value = "/Booking/{booking_id}")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ORDER_SERVICE') or hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> update_booking(@PathVariable("booking_id") String id, @RequestBody BookingRequest dto) {
		try {
			Boolean checkStatus = false;
			Date date = Date.from(Instant.now());
			if (service.getById(id) != null) {
				Booking entity = service.getById(id);
				if(entity.getBoStatus().equals("Waiting") && (entity.getBoStatus().equals("Cancelled") || entity.getBoStatus().equals("In process"))) {
					checkStatus = false;
				}else if(entity.getBoStatus().equals("In process")&& entity.getBoStatus().equals("Completed") ){
					checkStatus = false;
				}else if(entity.getBoStatus().equals(dto.getBoStatus())){
					checkStatus = false;
				}else {
					checkStatus = true;
				}
				if(checkStatus == false) {
					Booking entityRequest = modelMapper.map(dto, Booking.class);
					if(dto.getUserId() != null) {
						entityRequest.setUsersByBoUserId(UseSer.getById(dto.getUserId()));
					}
					if(dto.getEmployeeId() != null) {
						entityRequest.setUsersByBoEmployeeId(UseSer.getById(dto.getEmployeeId()));
					}
					entityRequest.setUpdatedAt(date);
					entityRequest.setBoId(id);
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
