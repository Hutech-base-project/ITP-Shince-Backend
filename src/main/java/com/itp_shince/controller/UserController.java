
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import com.itp_shince.model.ERole;
import com.itp_shince.model.OrdersPro;
import com.itp_shince.model.OrdersSer;
import com.itp_shince.model.UserRole;
import com.itp_shince.model.Users;
import com.itp_shince.service.OrderProService;
import com.itp_shince.service.OrderSerService;
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
	private OrderSerService ordSerService;

	HttpHeaders responseHeaders = new HttpHeaders();
	ObjectMapper mapper = new ObjectMapper();

	@GetMapping(value = "/Users")
	@PreAuthorize("hasRole('ACCOUNT') or hasRole('ADMIN')")
	public ResponseEntity<?> getAll() {
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

	@GetMapping(value = "/Users/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ACCOUNT') or hasRole('ADMIN')")
	public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
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
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ACCOUNT') or hasRole('ADMIN')")
	public ResponseEntity<?> create(@RequestBody UsersRequest dto) {
		try {
			if ((dto.getUsUserName() != null || !dto.getUsUserName().isEmpty())
					&& (dto.getUsDob() != null || !dto.getUsDob().isEmpty())
					&& (dto.getUsAddress() != null || !dto.getUsAddress().isEmpty())
					&& (dto.getUsEmailNo() != null || !dto.getUsEmailNo().isEmpty())
					&& (dto.getUsPassword() != null || !dto.getUsPassword().isEmpty())
					&& (dto.getUsPhoneNo() != null || !dto.getUsPhoneNo().isEmpty()) 
					&& dto.getListRole().size() > 0) {
				Date date = Date.from(Instant.now());
				String hash = BCrypt.hashpw(dto.getUsPassword(), BCrypt.gensalt(12));
				dto.setUsPassword(hash);
				Users entityResquest = modelMapper.map(dto, Users.class);
				entityResquest.setCreatedAt(date);
				entityResquest.setUsId(idUserIentity());
				entityResquest.setIsDelete(false);
				Users user = service.create(entityResquest);
				Set<UserRole> roles = new HashSet<>();
				List<String> roleRequest = dto.getListRole();
				user.setIsAdmin(true);
				UserRole userRole = new UserRole();
				for (String nameRole : roleRequest) {
					if (nameRole.equals(ERole.ROLE_ACCOUNT.toString()) || nameRole.equals(ERole.ROLE_ADMIN.toString())
							|| nameRole.equals(ERole.ROLE_CATEGORY.toString())
							|| nameRole.equals(ERole.ROLE_MODERATOR.toString())
							|| nameRole.equals(ERole.ROLE_ORDER_PRODUCT.toString())
							|| nameRole.equals(ERole.ROLE_ORDER_SERVICE.toString())
							|| nameRole.equals(ERole.ROLE_PRODUCT.toString())
							|| nameRole.equals(ERole.ROLE_SERVICE.toString())
							|| nameRole.equals(ERole.ROLE_USER.toString())) {
						userRole.setRole(roleService.getByName(nameRole));
						roles.add(userRole);
					} else {
						ObjectReponse objectReponse = new ObjectReponse("Role" + nameRole + "is not correct", 400, 0,
								120, "Minute");
						return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
					}
				}
				userRole.setUsers(user);
				service.create(user);
				for (UserRole role : roles) {
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

	@PutMapping(value = "/Users")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') and hasRole('ACCOUNT') or hasRole('ADMIN')")
	public ResponseEntity<?> update(
			@RequestPart(name = "data_json", required = false) String json,
			@RequestPart(required = false) @ApiParam(required = true, value = "") MultipartFile file) {
		try {
			Date date = Date.from(Instant.now());	
			UsersRequest dto = mapper.readValue(json, UsersRequest.class);
			Users entityRequest = modelMapper.map(dto, Users.class);
			if (service.getById(dto.getUsId()) != null) {
				Users entity = service.getById(dto.getUsId());
				entityRequest.setUpdatedAt(date);
				if(entityRequest.getUsUserName() != null || !entityRequest.getUsUserName().isEmpty()) {
					entityRequest.setUsUserName(entity.getUsUserName());
				}
				if(entityRequest.getUsAddress() != null || !entityRequest.getUsAddress().isEmpty()) {
					entityRequest.setUsAddress(entity.getUsAddress());
				}
				if(entityRequest.getUsDob() != null || !entityRequest.getUsDob().isEmpty()) {
					entityRequest.setUsDob(entity.getUsAddress());		
				}
				if(entityRequest.getUsEmailNo() != null || !entityRequest.getUsEmailNo().isEmpty()) {
					entityRequest.setUsEmailNo(entity.getUsEmailNo());	
				}
				if(entityRequest.getUsPassword() != null || !entityRequest.getUsPassword().isEmpty()) {
					entityRequest.setUsPassword(entity.getUsPassword());	
				}else {
					entityRequest.setUsPassword(encoder.encode(entityRequest.getUsPassword()));
				}
				if(entityRequest.getUsPhoneNo() != null || !entityRequest.getUsPhoneNo().isEmpty()) {
					entityRequest.setUsPhoneNo(entity.getUsPhoneNo());
				}
				entityRequest.setCreatedAt(entity.getCreatedAt());
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
					entityRequest.setUsImage(entityRequest.getUsId() + "/" + file.getOriginalFilename().toLowerCase());
				}
				service.create(entityRequest);
				ObjectReponse objectReponse = new ObjectReponse("Success", 200, 0, 120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
			}else {
				ObjectReponse objectReponse = new ObjectReponse("This user does not exist", 404,0 ,
						120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/UserChangePassword")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> changeUserPassword(@RequestBody ChangePassowrdRequest changePass) {
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

	@DeleteMapping(value = "/Users")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ACCOUNT') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteUsers(@RequestBody String id) {
		try {
			Users entity = service.getById(id);
			Boolean checkOrdPro = false;
			Boolean checkOrdSer = false;
			if (service.getById(id) != null) {
				List<OrdersPro> listOrdPro = ordProService.getAllByUserId(id);
				List<OrdersSer> listOrdSer = ordSerService.getAllByUserId(id);
				for (OrdersPro ordersPro : listOrdPro) {
					if (ordersPro.getOrProStatus().equals("Wait for confirmation")
							|| ordersPro.getOrProStatus().equals("Confirm")
							|| ordersPro.getOrProStatus().equals("Delivery")) {
						checkOrdPro = true;
					}
				}
				for (OrdersSer ordersSer : listOrdSer) {
					if (ordersSer.getOrSerStatus().equals("Waiting")
							|| ordersSer.getOrSerStatus().equals("In process")) {
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