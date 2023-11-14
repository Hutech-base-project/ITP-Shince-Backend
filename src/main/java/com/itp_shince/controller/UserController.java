
package com.itp_shince.controller;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

//import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itp_shince.dto.reponse.ObjectReponse;
import com.itp_shince.dto.reponse.UsersReponse;
import com.itp_shince.dto.request.ChangePassowrdRequest;
import com.itp_shince.dto.request.UsersRequest;
import com.itp_shince.model.Booking;
import com.itp_shince.model.ERole;
import com.itp_shince.model.OrdersPro;
import com.itp_shince.model.UserRole;
import com.itp_shince.model.Users;
import com.itp_shince.service.BookingService;
import com.itp_shince.service.OrderProService;
import com.itp_shince.service.RoleService;
import com.itp_shince.service.UserRoleService;
import com.itp_shince.service.UserService;

import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	RoleService roleService;

	@Autowired
	private OrderProService ordProService;

	@Autowired
	private BookingService bookingService;

	HttpHeaders responseHeaders = new HttpHeaders();
	ObjectMapper mapper = new ObjectMapper();

	@GetMapping(value = "/Users")
//	@PreAuthorize("hasRole('ACCOUNT') or hasRole('ADMIN')")
	public ResponseEntity<?> get_all_users() {
		try {
			List<Users> entityList = service.getAll();
			List<UsersReponse> dtos = entityList.stream().map(user -> modelMapper.map(user, UsersReponse.class))
					.collect(Collectors.toList());

			for (Users entity : entityList) {
				List<String> listRole = new ArrayList<>();
				for (UsersReponse dto : dtos) {
					if (dto.getUsId().equals(entity.getUsId())) {
						for (UserRole role : entity.getUserRoles()) {
							listRole.add(role.getRole().getRoName());
						}
						dto.setListRole(listRole);
					}
				}
			}
			ObjectReponse objectReponse = new ObjectReponse("Success", 200, dtos, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0, 120, "Minute");
		return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping(value = "/Users/{user_id}")
//	@PreAuthorize("hasRole('USER') or hasRole('ACCOUNT') or hasRole('ADMIN')")
	public ResponseEntity<?> get_user_by_id(@PathVariable("user_id") String id) {
		try {
			List<String> listRole = new ArrayList<>();
			Users entity = service.getById(id);
			for (UserRole role : entity.getUserRoles()) {
				listRole.add(role.getRole().getRoName());
			}
			UsersReponse dto = modelMapper.map(entity, UsersReponse.class);
			dto.setListRole(listRole);
			ObjectReponse objectReponse = new ObjectReponse("Success", 200, dto, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0, 120, "Minute");
		return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping(value = "/Users")
//	@PreAuthorize("hasRole('MODERATOR') and hasRole('ACCOUNT') or hasRole('ADMIN')")
	public ResponseEntity<?> create_user(@RequestPart(required = false) String json,
			@RequestPart(required = false) @ApiParam(required = true, value = "") MultipartFile file) {
		try {
			UsersRequest dto = mapper.readValue(json, UsersRequest.class);
			if ((dto.getUsUserName() != null || !dto.getUsUserName().isEmpty())
					&& (dto.getUsDob() != null || !dto.getUsDob().isEmpty())
					&& (dto.getUsAddress() != null || !dto.getUsAddress().isEmpty())
					&& (dto.getUsEmailNo() != null || !dto.getUsEmailNo().isEmpty())
					&& (dto.getUsPassword() != null || !dto.getUsPassword().isEmpty())
					&& (dto.getUsPhoneNo() != null || !dto.getUsPhoneNo().isEmpty()) && dto.getListRole().size() > 0
					&& file != null) {
				Date date = Date.from(Instant.now());
				String hash = BCrypt.hashpw(dto.getUsPassword(), BCrypt.gensalt(12));
				dto.setUsPassword(hash);
				Users entityResquest = modelMapper.map(dto, Users.class);
				entityResquest.setCreatedAt(date);
				entityResquest.setUsId(idUserIentity());
				entityResquest.setIsDelete(false);
				Users user = service.create(entityResquest);
				List<UserRole> roles = new ArrayList<>();
				List<String> roleRequest = dto.getListRole();
				user.setIsAdmin(true);
				user.setIsDelete(false);
				File folder = new File("Images/Users/" + entityResquest.getUsId());
				folder.mkdirs();
				Path path = Paths.get(folder.getPath());
				InputStream inputStream = file.getInputStream();
				Files.copy(inputStream, path.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
				user.setUsImage(entityResquest.getUsId() + "/" + file.getOriginalFilename().toLowerCase());
				service.create(user);
				for (String nameRole : roleRequest) {
					UserRole userRole = new UserRole();
					if (nameRole.equals(ERole.ROLE_ACCOUNT.toString()) || nameRole.equals(ERole.ROLE_ADMIN.toString())
							|| nameRole.equals(ERole.ROLE_CATEGORY.toString())
							|| nameRole.equals(ERole.ROLE_MODERATOR.toString())
							|| nameRole.equals(ERole.ROLE_ORDER_PRODUCT.toString())
							|| nameRole.equals(ERole.ROLE_ORDER_SERVICE.toString())
							|| nameRole.equals(ERole.ROLE_PRODUCT.toString())
							|| nameRole.equals(ERole.ROLE_SERVICE.toString())
							|| nameRole.equals(ERole.ROLE_USER.toString())
							|| nameRole.equals(ERole.ROLE_EMPLOYYE.toString())) {
						userRole.setRole(roleService.getByName(nameRole));
						userRole.setUsers(user);
						roles.add(userRole);
					} else {
						ObjectReponse objectReponse = new ObjectReponse("Role" + nameRole + "is not correct", 400, 0,
								120, "Minute");
						return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
					}
				}
				UserRole userRole = new UserRole();
				userRole.setRole(roleService.getByName("ROLE_USER"));
				userRole.setUsers(user);
				roles.add(userRole);
				for (UserRole role : roles) {
					role.setCreatedAt(date);
					userRoleService.create(role);
				}
				ObjectReponse objectReponse = new ObjectReponse("Success", 201, 0, 120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.CREATED);
			} else {
				ObjectReponse objectReponse = new ObjectReponse(
						"This user cannot be created, please check the user information", 400, 0, 120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/Users/{user_id}")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') and hasRole('ACCOUNT') or hasRole('ADMIN')")
	public ResponseEntity<?> update_user(@PathVariable("user_id") String id,
			@RequestPart(name = "data_json", required = false) String json,
			@RequestPart(required = false) @ApiParam(required = true, value = "") MultipartFile file) {
		try {
			Date date = Date.from(Instant.now());
			UsersRequest dto = mapper.readValue(json, UsersRequest.class);
			Users entityRequest = modelMapper.map(dto, Users.class);
			if (service.getById(id) != null) {
				Boolean checkOrder = false;
				for (OrdersPro order : service.getById(id).getOrderspros()) {
					if (order.getOrProStatus().equals("Wait for confirmation") == true
							|| order.getOrProStatus().equals("Confirm") == true
							|| order.getOrProStatus().equals("Delivery") == true) {
						checkOrder = true;
					}
				}
				
				Boolean checkBooking = false;
				for (Booking booking : service.getById(id).getBookingsForBoUserId()) {
					if (booking.getBoStatus().equals("Wait for confirmation") == true
							|| booking.getBoStatus().equals("Confirm") == true
							|| booking.getBoStatus().equals("Delivery") == true) {
						checkBooking = true;
					}
				}
				
				Boolean checkBlock = false;
				if(dto.getIsBlock() != null) {
					if(dto.getIsBlock() == true  && (checkBooking == true || checkOrder == true)) {
						checkBlock = true;
					}else {
						checkBlock = false;
					}
				}else {
					checkBlock = false;
				}
				if(checkBlock == false) {
					Users entity = service.getById(id);
					entityRequest.setUpdatedAt(date);
					if (entityRequest.getUsUserName() == null || entityRequest.getUsUserName().isEmpty()) {
						entityRequest.setUsUserName(entity.getUsUserName());
					}
					if (entityRequest.getUsAddress() == null || entityRequest.getUsAddress().isEmpty()) {
						entityRequest.setUsAddress(entity.getUsAddress());
					}
					if (entityRequest.getUsDob() == null || entityRequest.getUsDob().isEmpty()) {
						entityRequest.setUsDob(entity.getUsDob());
					}
					if (entityRequest.getUsEmailNo() == null || entityRequest.getUsEmailNo().isEmpty()) {
						entityRequest.setUsEmailNo(entity.getUsEmailNo());
					}
					if (entityRequest.getUsPhoneNo() == null || entityRequest.getUsPhoneNo().isEmpty()) {
						entityRequest.setUsPhoneNo(entity.getUsPhoneNo());
					}	
					if (entityRequest.getUsNote() == null || entityRequest.getUsNote().isEmpty()) {
						entityRequest.setUsNote(entity.getUsNote());
					}
					if (entityRequest.getUsDob() == null || entityRequest.getUsDob().isEmpty()) {
						entityRequest.setUsDob(entity.getUsDob());
					}
					if (entityRequest.getIsAdmin() == null) {
						entityRequest.setIsAdmin(entity.getIsAdmin());
					}
					if (entityRequest.getIsBlock() == null) {
						entityRequest.setIsBlock(entity.getIsBlock());
					}
					entityRequest.setUsPassword(entity.getUsPassword());
					entityRequest.setCreatedAt(entity.getCreatedAt());
					entityRequest.setUsId(id);
					entityRequest.setIsDelete(false);
					if (file == null) {
						entityRequest.setUsImage(entity.getUsImage());
					} else {
						// delete old image
						if (entityRequest.getUsImage() != null) {
							File directoryToDelete = new File("Images/Users/" + dto.getUsId());
							FileSystemUtils.deleteRecursively(directoryToDelete);
						}
						File folder = new File("Images/Users/" + entityRequest.getUsId());
						folder.mkdirs();
						Path path = Paths.get(folder.getPath());
						InputStream inputStream = file.getInputStream();
						Files.copy(inputStream, path.resolve(file.getOriginalFilename()),
								StandardCopyOption.REPLACE_EXISTING);
						entityRequest.setUsImage(entity.getUsId() + "/" + file.getOriginalFilename().toLowerCase());
					}
					
					service.create(entityRequest);
					if (dto.getListRole().size() > 0) {
						for (UserRole userRole : entity.getUserRoles()) {
							userRoleService.delete(userRole);
						}
						List<UserRole> roles = new ArrayList<>();
						List<String> roleRequest = dto.getListRole();
						for (String nameRole : roleRequest) {
							UserRole userRole = new UserRole();
							if (nameRole.equals(ERole.ROLE_ACCOUNT.toString())
									|| nameRole.equals(ERole.ROLE_ADMIN.toString())
									|| nameRole.equals(ERole.ROLE_CATEGORY.toString())
									|| nameRole.equals(ERole.ROLE_MODERATOR.toString())
									|| nameRole.equals(ERole.ROLE_ORDER_PRODUCT.toString())
									|| nameRole.equals(ERole.ROLE_ORDER_SERVICE.toString())
									|| nameRole.equals(ERole.ROLE_PRODUCT.toString())
									|| nameRole.equals(ERole.ROLE_SERVICE.toString())
									|| nameRole.equals(ERole.ROLE_USER.toString())
									|| nameRole.equals(ERole.ROLE_EMPLOYYE.toString())) {
								userRole.setRole(roleService.getByName(nameRole));
								userRole.setUsers(entity);
								roles.add(userRole);
							} else {
								ObjectReponse objectReponse = new ObjectReponse("Role" + nameRole + "is not correct", 400,
										0, 120, "Minute");
								return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
							}
						}
						for (UserRole role : roles) {
							role.setCreatedAt(date);
							userRoleService.create(role);
						}
					}
					ObjectReponse objectReponse = new ObjectReponse("Success", 200, 0, 120, "Minute");
					return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
				}else {
					ObjectReponse objectReponse = new ObjectReponse("This user cannot update", 404, 0, 120, "Minute");
					return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
				}
			} else {
				ObjectReponse objectReponse = new ObjectReponse("This user does not exist", 404, 0, 120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/UserChangePassword")
//	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> change_user_password(@RequestBody ChangePassowrdRequest changePass) {
		try {
			System.out.print(changePass);
			if (service.getById(changePass.getUserId()) != null) {
				Users entity = service.getById(changePass.getUserId());
				PasswordEncoder passencoder = new BCryptPasswordEncoder();
				boolean chekPass = passencoder.matches(changePass.getOldPassword(), entity.getUsPassword());
				if (chekPass) {
					if (changePass.getOldPassword().equals(changePass.getNewPassword())) {
						ObjectReponse objectReponse = new ObjectReponse(
								"The new password cannot be the same as the old password", 400, 0, 120, "Minute");
						return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
					} else {
						String hash = BCrypt.hashpw(changePass.getNewPassword(), BCrypt.gensalt(12));
						entity.setUsPassword(hash);
						service.create(entity);
						ObjectReponse objectReponse = new ObjectReponse("Success", 200, 0, 120, "Minute");
						return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
					}

				} else {
					ObjectReponse objectReponse = new ObjectReponse("Old password is correct", 400, 0, 120, "Minute");
					return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
				}
			} else {
				ObjectReponse objectReponse = new ObjectReponse("This user does not exist", 404, 0, 120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/Users/{user_id}")
//	@PreAuthorize("hasRole('MODERATOR') and hasRole('ACCOUNT') or hasRole('ADMIN')")
	public ResponseEntity<?> delete_user(@PathVariable("user_id") String id) {
		try {
			Users entity = service.getById(id);
			Boolean checkOrdPro = false;
			Boolean checkOrdSer = false;
			if (service.getById(id) != null) {
				List<OrdersPro> listOrdPro = ordProService.getAllByUserId(id);
				List<Booking> listOrdSer = bookingService.getAllByUserId(id);
				for (OrdersPro ordersPro : listOrdPro) {
					if (ordersPro.getOrProStatus().equals("Wait for confirmation")
							|| ordersPro.getOrProStatus().equals("Confirm")
							|| ordersPro.getOrProStatus().equals("Delivery")) {
						checkOrdPro = true;
					}
				}
				for (Booking booking : listOrdSer) {
					if (booking.getBoStatus().equals("Waiting")
							|| booking.getBoStatus().equals("In process")) {
						checkOrdSer = true;
					}
				}
				if (checkOrdPro == false && checkOrdSer == false) {
					if (entity.getUsImage() != null) {
						File directoryToDelete = new File("Images/Users/" + entity.getUsImage());
						FileSystemUtils.deleteRecursively(directoryToDelete);
					}
					service.delete(entity);
					ObjectReponse objectReponse = new ObjectReponse("Success", 200, 0, 120, "Minute");
					return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
				} else {
					ObjectReponse objectReponse = new ObjectReponse("Can't delete user", 400, 0, 120, "Minute");
					return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
				}
			} else {
				ObjectReponse objectReponse = new ObjectReponse("This user order does not exist", 404, 0, 120,
						"Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public String idUserIentity() {
		LocalDate today = LocalDate.now();
		int numberUser = service.getCountUserByDate(today);
		int year = (today.getYear() % 100);
		String number = String.valueOf(numberUser);
		String id = null;
		switch (number.length()) {
		case 1:
			if (numberUser != 9) {
				id = "MS" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "000" + (numberUser + 1);
			} else {
				id = "MS" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "00" + (numberUser + 1);
			}
			break;
		case 2:
			if (numberUser != 99) {
				id = "MS" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "00" + (numberUser + 1);
			} else {
				id = "MS" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "0" + (numberUser + 1);
			}
			break;
		case 3:
			if (numberUser != 999) {
				id = "MS" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "0" + (numberUser + 1);
			} else {
				id = "MS" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "" + (numberUser + 1);
			}
			break;
		case 4:
			id = "MS" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "" + (numberUser + 1);
			break;
		default:
			System.out.print("Id error");
			break;
		}
		return id;
	}
}
