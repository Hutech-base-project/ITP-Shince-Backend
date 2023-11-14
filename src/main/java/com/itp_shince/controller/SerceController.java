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

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itp_shince.dto.reponse.ObjectReponse;
import com.itp_shince.dto.reponse.SerceImageReponse;
import com.itp_shince.dto.reponse.SerceReponse;
import com.itp_shince.dto.request.SerceImageRequest;
import com.itp_shince.dto.request.SerceRequest;
import com.itp_shince.model.BookingDetails;
import com.itp_shince.model.Serce;
import com.itp_shince.model.SerceImage;
import com.itp_shince.service.SerceImageService;
import com.itp_shince.service.SerceService;

import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class SerceController {

	@Autowired
	private SerceService service;

	@Autowired
	private SerceImageService serviceSerImg;

	@Autowired
	private ModelMapper modelMapper;

	HttpHeaders responseHeaders = new HttpHeaders();
	ObjectMapper mapper = new ObjectMapper();

	@GetMapping(value = "/Serce")
	public ResponseEntity<?> get_all_services() {
		try {
			List<Serce> entityList = service.getAll();
			List<SerceImage> listServiceImages = serviceSerImg.getAll();
			List<SerceReponse> dtos = entityList.stream().map(ser -> modelMapper.map(ser, SerceReponse.class))
					.collect(Collectors.toList());
			for (Serce entity : entityList) {
				List<SerceImageReponse> list = new ArrayList<>();
				for (SerceImage image : listServiceImages) {
					if (image.getSerce().getSeId().equals(entity.getSeId())) {
						list.add(new SerceImageReponse(image.getSerImgId(), image.getSerImgPath()));
					}
				}
				for (SerceReponse dto : dtos) {
					if (dto.getSeId().equals(entity.getSeId())) {
						dto.setListImg(list);
					}
				}

			}
			ObjectReponse objectReponse = new ObjectReponse("Success", 200, dtos, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/Serce/{service_id}")
	public ResponseEntity<?> get_service_by_id(@PathVariable("service_id") String id) {
		try {
			Serce entity = service.getById(id);
			if (service.getById(id) != null) {
				SerceReponse dto = modelMapper.map(entity, SerceReponse.class);
				ObjectReponse objectReponse = new ObjectReponse("Success", 200, dto, 120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
			} else {
				ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0, 120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/Serce")
//	@PreAuthorize("hasRole('MODERATOR') and hasRole('SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<?> create_service(@RequestPart(required = false) String json,
			@RequestPart(required = false) @ApiParam(required = true, value = "") MultipartFile file,
			@RequestPart(required = false) @ApiParam(required = true, value = "") MultipartFile[] Mutifile) {
		try {
			Date date = Date.from(Instant.now());		
			SerceRequest dto = mapper.readValue(json, SerceRequest.class);
			Serce entityRequest = modelMapper.map(dto, Serce.class);
			List<Serce> entityList = service.getAll();
			if (dto.getSeName() != null &&
				dto.getSeDescription() != null &&
				dto.getSePrice() != null &&
				entityList.stream().filter(n -> n.getSeName().equals(dto.getSeName())).count() == 0 &&
				file != null &&
				Mutifile != null &&
				Mutifile.length == 4) {
				if (entityList.size() > 0) {
					if (entityList.stream().filter(n -> n.getSeName().equals(entityRequest.getSeName())&& n.getIsDelete() == false) == null) {
						ObjectReponse objectReponse = new ObjectReponse(
								"This product cannot be created, please check the product information", 400, 0, 120,
								"Minute");
						return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
					}
				}
				entityRequest.setSeId(idSerceIdentity());
				entityRequest.setCreatedAt(date);
				if(entityRequest.getSeTurnOn() == null ) {
					entityRequest.setSeTurnOn(true);
				}
				if(entityRequest.getIsDelete() == null ||  entityRequest.getIsDelete() == true) {
					entityRequest.setIsDelete(false);
				}
				File folder = new File("Images/Services/" + entityRequest.getSeId());
				folder.mkdirs();
				Path path = Paths.get(folder.getPath());
				InputStream inputStream = file.getInputStream();
				Files.copy(inputStream, path.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
				entityRequest.setSeImage(entityRequest.getSeId() + "/" + file.getOriginalFilename().toLowerCase());
				service.create(entityRequest);
				File folderMulti = new File("ImagesChildent/Services/" + entityRequest.getSeId());
				folderMulti.mkdirs();
				Path pathChild = Paths.get(folderMulti.getPath());
				for (MultipartFile childFile : Mutifile) {
					InputStream inputChildStream = childFile.getInputStream();
					Files.copy(inputChildStream, pathChild.resolve(childFile.getOriginalFilename()),
							StandardCopyOption.REPLACE_EXISTING);
					String imgPath = entityRequest.getSeId() + "/" + childFile.getOriginalFilename().toLowerCase();
					SerceImageRequest entityChildRequest = new SerceImageRequest(idSerImgIdentity(), entityRequest,
							imgPath, date, false);
					SerceImage entityConvertRequest = modelMapper.map(entityChildRequest, SerceImage.class);
					serviceSerImg.create(entityConvertRequest);
				}
				ObjectReponse objectReponse = new ObjectReponse("Success", 201, 0, 120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.CREATED);
			} else {
				ObjectReponse objectReponse = new ObjectReponse(
						"This service cannot be created, please check the service information", 400, 0, 120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {		
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/Serce/{service_id}")
//	@PreAuthorize("hasRole('MODERATOR') and hasRole('SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<?> update_service(@PathVariable("service_id") String id, @RequestPart(required = false) String json,
			@RequestPart(required = false) @ApiParam(required = false, value = "") MultipartFile file,
			@RequestPart(required = false) @ApiParam(required = false, value = "") MultipartFile[] Mutifile) {
		try {
			Date date = Date.from(Instant.now());
			if (service.getById(id) != null) {
				Boolean check = false;
				for (BookingDetails item : service.getById(id).getBookingdetailses()) {
					if (item.getBooking().getBoStatus().equals("Waiting") == true
							|| item.getBooking().getBoStatus().equals("In process") == true) {
						check = true;
					}
				}
				SerceRequest dtoRequest = mapper.reader().forType(SerceRequest.class).readValue(json);
				if (check == true && dtoRequest.getSeTurnOn() == false) {
					ObjectReponse objectReponse = new ObjectReponse(
							"This service cannot be turn off, please check the service again", 400, 0, 120, "Minute");
					return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
				} else {
					Serce entity = service.getById(id);
					Serce entityRequest = modelMapper.map(dtoRequest, Serce.class);
					List<Serce> entityList = service.getAll();
					List<String> idImgChild = dtoRequest.getIdImgChild();
					entityRequest.setSeId(id);
					entityRequest.setCreatedAt(entity.getCreatedAt());
					entityRequest.setUpdatedAt(date);
					if(entityRequest.getSeName() == null || entityRequest.getSeName().isEmpty()) {
						entityRequest.setSeName(entity.getSeName());
					}
					if(entityRequest.getSePrice() == 0) {
						entityRequest.setSePrice(entity.getSePrice());
					}
					if(entityRequest.getSeDescription() == null || entityRequest.getSeDescription().isEmpty()) {
						entityRequest.setSeDescription(entity.getSeDescription());
					}
					if((entityRequest.getSeNote() == null || entityRequest.getSeNote().isEmpty()) && entity.getSeNote() != null ) {
						entityRequest.setSeNote(entity.getSeNote());
					}
					if(entity.getSeTurnOn() == null) {
						entityRequest.setSeTurnOn(entity.getSeTurnOn());
					}
					entityRequest.setIsDelete(entity.getIsDelete());				
					if (entityList.stream().filter(n -> n.getSeName().equals(entityRequest.getSeName())&& n.getIsDelete() == false && n.getSeId() != id) != null) {
						if (file == null) {
							entityRequest.setSeImage(entity.getSeImage());
						} else {
							if (dtoRequest.getSeImage() != null) {
								File directoryToDelete = new File("Images/Services/" + id);
								FileSystemUtils.deleteRecursively(directoryToDelete);
							}
							File folder = new File("Images/Services/" + entityRequest.getSeId());
							folder.mkdirs();
							Path path = Paths.get(folder.getPath());						
							InputStream inputStream = file.getInputStream();
							Files.copy(inputStream, path.resolve(file.getOriginalFilename()),
									StandardCopyOption.REPLACE_EXISTING);
							entityRequest.setSeImage(
									entityRequest.getSeId() + "/" + file.getOriginalFilename().toLowerCase());
						}

						if(idImgChild != null && Mutifile!= null) {
							if (idImgChild.size() ==  Mutifile.length) {
								for (String string : idImgChild) {
									if(serviceSerImg.getById(string) == null ) {
										ObjectReponse objectReponse = new ObjectReponse(
												"This product cannot be updated, please check the product information", 400,
												0, 120, "Minute");
										return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
									}
								}
								for (int i = 0; i < Mutifile.length; i++) {
									String idImg = idImgChild.get(i);
									SerceImage serImg = serviceSerImg.getById(idImg);
									File directoryToDelete = new File(
											"ImagesChildent/Services/" + id + "/" + serImg.getSerImgPath());
									FileSystemUtils.deleteRecursively(directoryToDelete);
									Path path = Paths.get("ImagesChildent/Services/" + id);
									InputStream inputStream = Mutifile[i].getInputStream();
									Files.copy(inputStream, path.resolve(Mutifile[i].getOriginalFilename()),
											StandardCopyOption.REPLACE_EXISTING);
									serImg.setSerImgPath(id + "/" + Mutifile[i].getOriginalFilename().toLowerCase());
									serImg.setUpdatedAt(date);
									serviceSerImg.create(serImg);
								}
							} else {
								ObjectReponse objectReponse = new ObjectReponse(
										"This service cannot be updated, please check the service information", 400, 0,
										120, "Minute");
								return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
							}
						}		
						service.create(entityRequest);
						ObjectReponse objectReponse = new ObjectReponse("Success", 200, 0, 120, "Minute");
						return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
					}else {
						ObjectReponse objectReponse = new ObjectReponse(
								"This service cannot be updated, please check the service information", 400, 0, 120,
								"Minute");
						return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
					}
				}

			} else {
				ObjectReponse objectReponse = new ObjectReponse("This service does not exist", 404, 0, 120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@DeleteMapping(value = "/Serce/{service_id}")
//	@PreAuthorize("hasRole('MODERATOR') and hasRole('SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<?> delete_service(@PathVariable("service_id") String id) {
		try {
			Date date = Date.from(Instant.now());
			Serce entity = service.getById(id);
			entity.setUpdatedAt(date);
			if (service.getById(id) != null) {
				if (entity.getSeTurnOn() == false) {
					if (entity.getBookingdetailses().size() == 0) {
						if (entity.getSeImage() != null) {
							File directoryToDelete = new File("Images/Services/" + entity.getSeId());
							FileSystemUtils.deleteRecursively(directoryToDelete);
						}
						if (entity.getSerceImages() != null) {
                            File directoryToDelete = new File("ImagesChildent/Services/" + entity.getSeId());
                            FileSystemUtils.deleteRecursively(directoryToDelete);
                            for (SerceImage seImg : entity.getSerceImages()) {
                                serviceSerImg.delete(seImg);
                            }
                        }
						service.delete(entity);
						return new ResponseEntity<>("Success", responseHeaders, HttpStatus.OK);

					} else {
						Boolean check = false;
						for (BookingDetails item : entity.getBookingdetailses()) {
							if (item.getBooking().getBoStatus().equals("In process") == true) {
								check = true;
							}
						}
						if (check == false) {
							entity.setIsDelete(true);
							service.create(entity);
							ObjectReponse objectReponse = new ObjectReponse("Success", 200, 0, 120, "Minute");
							return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
						} else {
							ObjectReponse objectReponse = new ObjectReponse(
									"Many booking have not been processed yet, please check again", 400, 0, 120,
									"Minute");
							return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
						}
					}
				} else {
					ObjectReponse objectReponse = new ObjectReponse(
							"The service does not turn off, please check the service again", 400, 0, 120, "Minute");
					return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
				}
			} else {
				ObjectReponse objectReponse = new ObjectReponse("This service does not exist", 404, 0, 120,
						"Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public String idSerceIdentity() {
		LocalDate today = LocalDate.now();
		int numberUser = service.getCountSerByDate(today);
		int year = (today.getYear() % 100);
		String number = String.valueOf(numberUser);
		String id = null;
		switch (number.length()) {
		case 1:
			if (numberUser != 9) {
				id = "S" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "000" + (numberUser + 1);
			} else {
				id = "S" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "00" + (numberUser + 1);
			}
			break;
		case 2:
			if (numberUser != 99) {
				id = "S" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "00" + (numberUser + 1);
			} else {
				id = "S" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "0" + (numberUser + 1);
			}
			break;
		case 3:
			if (numberUser != 999) {
				id = "S" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "0" + (numberUser + 1);
			} else {
				id = "S" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "" + (numberUser + 1);
			}
			break;
		case 4:
			id = "S" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "" + (numberUser + 1);
			break;
		default:
			System.out.print("Id error");
			break;
		}
		return id;
	}

	public String idSerImgIdentity() {
		LocalDate today = LocalDate.now();
		int numberUser = serviceSerImg.countSerImgByDate(today);
		int year = (today.getYear() % 100);
		String number = String.valueOf(numberUser);
		String id = null;
		switch (number.length()) {
		case 1:
			if (numberUser != 9) {
				id = "SI" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "000" + (numberUser + 1);
			} else {
				id = "SI" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "00" + (numberUser + 1);
			}
			break;
		case 2:
			if (numberUser != 99) {
				id = "SI" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "00" + (numberUser + 1);
			} else {
				id = "SI" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "0" + (numberUser + 1);
			}
			break;
		case 3:
			if (numberUser != 999) {
				id = "SI" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "0" + (numberUser + 1);
			} else {
				id = "SI" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "" + (numberUser + 1);
			}
			break;
		case 4:
			id = "SI" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "" + (numberUser + 1);
			break;
		default:
			System.out.print("Id error");
			break;
		}
		return id;
	}
}
