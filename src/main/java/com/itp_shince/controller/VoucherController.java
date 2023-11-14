package com.itp_shince.controller;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itp_shince.dto.reponse.ObjectReponse;
import com.itp_shince.dto.reponse.VoucherReponse;
import com.itp_shince.dto.request.VoucherRequest;
import com.itp_shince.model.Voucher;
import com.itp_shince.service.VoucherService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class VoucherController {
	@Autowired
	VoucherService service;

	@Autowired
	private ModelMapper modelMapper;

	HttpHeaders responseHeaders = new HttpHeaders();

	@GetMapping("/Voucher")
//	@PreAuthorize("hasRole('VOUCHER') or hasRole('ADMIN')")
	public ResponseEntity<?> get_all_vouchers() {
		try {
			ObjectReponse objectReponse = new ObjectReponse("Success", 200, service.getAll().stream()
					.map(post -> modelMapper.map(post, VoucherReponse.class)).collect(Collectors.toList()), 120,
					"Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@GetMapping("/Voucher/{voucher_id}")
	public ResponseEntity<?> get_voucher_by_id(@PathVariable("voucher_id") String id) {
		try {
			Voucher entity = service.getById(id);
			if (entity != null) {
				VoucherReponse dtoResponse = modelMapper.map(entity, VoucherReponse.class);
				ObjectReponse objectReponse = new ObjectReponse("Success", 200, dtoResponse, 120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
			} else {
				ObjectReponse objectReponse = new ObjectReponse("This voucher could not be found", 404, null, 120,
						"Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/Voucher")
//	@PreAuthorize("hasRole('MODERATOR') and hasRole('VOUCHER') or hasRole('ADMIN')")
	public ResponseEntity<?> create_voucher(@RequestBody VoucherRequest dtoRequest) {
		try {
			Voucher entityRequest = modelMapper.map(dtoRequest, Voucher.class);
			if (entityRequest.getVoName() != null && entityRequest.getVoDescription() != null
					&& entityRequest.getVoPrice() > 0
					&& (entityRequest.getVoProduct() == true && entityRequest.getVoService() == false || entityRequest.getVoProduct() == false && entityRequest.getVoService() == true)
					&& entityRequest.getVoCount() > 0) {
				List<Voucher> entityList = service.getAll();
				if (entityList.size() > 0) {
					if (entityList.stream().filter(
							n -> n.getVoName().equals(entityRequest.getVoName()) && n.getIsDelete() == false && n.getVoProduct() != entityRequest.getVoProduct()
									&& n.getVoService() != entityRequest.getVoService()
							) != null) {
						ObjectReponse objectReponse = new ObjectReponse(
								"This voucher cannot be created, please check the product information", 400, 0, 120,
								"Minute");
						return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
					}
				}
				entityRequest.setVoId(idVoucherIdentity());
				
				entityRequest.setIsDelete(false);
				service.create(entityRequest);
				ObjectReponse objectReponse = new ObjectReponse("Success", 201, 0, 0, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.CREATED);
			} else {
				ObjectReponse objectReponse = new ObjectReponse("This directory cannot be initialized", 400, 0, 120,
						"Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/Voucher/{voucher_id}")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('VOUCHER') or hasRole('ADMIN')")
	public ResponseEntity<?> update_voucher(@PathVariable("voucher_id") String id, @RequestBody VoucherRequest dto) {
		try {
			Voucher entityRequest = modelMapper.map(dto, Voucher.class);
			Voucher entity = service.getById(id);
			List<Voucher> entityList = service.getAll();
			if (entityList.size() > 0) {
				if (entityList.stream().filter(n -> n.getVoName().equals(entityRequest.getVoName())
						&& n.getIsDelete() == false && n.getVoId() != id && n.getVoProduct() != entity.getVoProduct()
								&& n.getVoService() != entity.getVoService()) == null) {
					ObjectReponse objectReponse = new ObjectReponse(
							"This voucher cannot be created, please check the product information", 400, 0, 120,
							"Minute");
					return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
				}
			}
			entityRequest.setCreatedAt(entity.getCreatedAt());
			if (entityRequest.getExpirationDate() == null ) {
				entityRequest.setExpirationDate(entity.getCreatedAt());
			}
			entityRequest.setIsDelete(false);
			if (entityRequest.getVoName() == null || entityRequest.getVoName().isEmpty()) {
				entityRequest.setVoName(entity.getVoName());
			}
			if (entityRequest.getVoPrice() == 0) {
				entityRequest.setVoPrice(entity.getVoPrice());
			}
			if (entityRequest.getVoDescription() == null || entityRequest.getVoDescription().isEmpty()) {
				entityRequest.setVoDescription(entity.getVoDescription());
			}
			if (entityRequest.getVoProduct() == null) {
				entityRequest.setVoProduct(entity.getVoProduct());
			}
			if (entityRequest.getVoService() == null) {
				entityRequest.setVoService(entity.getVoService());
			}
			if (entityRequest.getVoCount() == 0) {
				entityRequest.setVoCount(entity.getVoCount());
			}
			if (entityRequest.getVoPrice() == 0) {
				entityRequest.setVoPrice(entity.getVoPrice());
			}
			if (entityRequest.getVoService() == null) {
				entityRequest.setVoService(entity.getVoService());
			}
			service.create(entityRequest);
			ObjectReponse objectReponse = new ObjectReponse("Success", 200, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/Voucher/{voucher_id}")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('VOUCHER') or hasRole('ADMIN')")
	public ResponseEntity<?> delete_voucher(@PathVariable("voucher_id") String id) {
		try {
			Voucher entity = service.getById(id);
			if (service.getById(id) != null) {
				entity.setIsDelete(true);
				service.create(entity);
				ObjectReponse objectReponse = new ObjectReponse("Success", 200, 0, 0, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
			} else {
				ObjectReponse objectReponse = new ObjectReponse("Category doesn't exists yet", 404, 0, 0, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public String idVoucherIdentity() {
		LocalDate today = LocalDate.now();
		int numberUser = service.getCountVoucherByDate(today);
		int year = (today.getYear() % 100);
		String number = String.valueOf(numberUser);
		String id = null;
		switch (number.length()) {
		case 1:
			if (numberUser != 9) {
				id = "V" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "000" + (numberUser + 1);
			} else {
				id = "V" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "00" + (numberUser + 1);
			}
			break;
		case 2:
			if (numberUser != 99) {
				id = "V" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "00" + (numberUser + 1);
			} else {
				id = "V" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "0" + (numberUser + 1);
			}
			break;
		case 3:
			if (numberUser != 999) {
				id = "V" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "0" + (numberUser + 1);
			} else {
				id = "V" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "" + (numberUser + 1);
			}
			break;
		case 4:
			id = "V" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "" + (numberUser + 1);
			break;
		default:
			System.out.print("Id error");
			break;
		}
		return id;
	}
}