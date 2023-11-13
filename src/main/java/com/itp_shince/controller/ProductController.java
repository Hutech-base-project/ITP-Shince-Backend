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
import com.itp_shince.dto.reponse.ProductImgReponse;
import com.itp_shince.dto.reponse.ProductReponse;
import com.itp_shince.dto.request.ProductImageRequest;
import com.itp_shince.dto.request.ProductRequest;
import com.itp_shince.model.Category;
import com.itp_shince.model.OrdersProDetail;
import com.itp_shince.model.Product;
import com.itp_shince.model.ProductImage;
import com.itp_shince.service.CategoryService;
import com.itp_shince.service.ProductImageService;
import com.itp_shince.service.ProductService;

import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService service;

	@Autowired
	private CategoryService serviceCate;

	@Autowired
	private ProductImageService serviceProImg;

	@Autowired
	private ModelMapper modelMapper;

	HttpHeaders responseHeaders = new HttpHeaders();

	ObjectMapper mapper = new ObjectMapper();

	@GetMapping(value = "/Product")
	public ResponseEntity<?> get_all_products() {
		try {
			List<Product> entityList = service.getAll();
			List<ProductImage> listProductImages = serviceProImg.getAll();
			for (Product entity : entityList) {
				if (entity.getProQuantity() <= 0) {
					entity.setProStatus("Out Of Stock");
					service.create(entity);
				}
			}

			List<ProductReponse> dtos = entityList.stream().map(pro -> modelMapper.map(pro, ProductReponse.class))
					.collect(Collectors.toList());

			for (Product entity : entityList) {
				List<ProductImgReponse> list = new ArrayList<>();
				for (ProductImage image : listProductImages) {
					if (image.getProduct().getProId().equals(entity.getProId())) {
						list.add(new ProductImgReponse(image.getProImgId(), image.getProImgPath()));
					}
				}
				for (ProductReponse dto : dtos) {
					if (dto.getProId().equals(entity.getProId())) {
						dto.setCategory_id(entity.getCategory().getCateId());
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

	@GetMapping(value = "/Product/{product_id}")
	public ResponseEntity<?> get_product_by_id(@PathVariable("product_id") String id) {
		try {
			Product entity = service.getById(id);
			if (service.getById(id) != null) {
				if (entity.getProQuantity() <= 0) {
					entity.setProStatus("Out Of Stock");
					service.create(entity);
				}
				ProductReponse dto = modelMapper.map(entity, ProductReponse.class);
				dto.setCategory_id(entity.getCategory().getCateId());
				ObjectReponse objectReponse = new ObjectReponse("Success", 200, dto, 120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
			} else {
				ObjectReponse objectReponse = new ObjectReponse("This product does not exist yet", 404, 0, 120,
						"Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/Product")
//	@PreAuthorize("hasRole('MODERATOR') and hasRole('PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<?> create_product(@RequestPart(required = false) String json,
			@RequestPart(required = false) @ApiParam(required = true, value = "") MultipartFile file,
			@RequestPart(required = false) @ApiParam(required = true, value = "") MultipartFile[] Mutifile) {
		try {
			Date date = Date.from(Instant.now());
			ProductRequest dtoRequest = mapper.readValue(json, ProductRequest.class);
			Category cate = serviceCate.getById(dtoRequest.getCategory_id());
			Product entityRequest = modelMapper.map(dtoRequest, Product.class);
			List<Product> entityList = service.getAll();
			if (entityRequest.getProName() != null && cate != null && entityRequest.getProBrand() != null && entityRequest.getProPrice() > 0
					&& entityRequest.getProContent() != null && entityRequest.getProQuantity() >= 0 && file != null && Mutifile != null && Mutifile.length == 4 ) {
				if (entityList.size() > 0) {
					if (entityList.stream().filter(n -> n.getProName().equals(entityRequest.getProName())&& n.getIsDelete()== false) == null ) {
						ObjectReponse objectReponse = new ObjectReponse(
								"This product cannot be created, please check the product information", 400, 0, 120,
								"Minute");
						return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
					}
				}
				entityRequest.setProId(idProIdentity());
				entityRequest.setCategory(cate);
				entityRequest.setCreatedAt(date);
				if (entityRequest.getProTurnOn() == null) {
					entityRequest.setProTurnOn(true);
				}
				if (entityRequest.getIsDelete() == null || entityRequest.getIsDelete() == true) {
					entityRequest.setIsDelete(false);
				}
				File folder = new File("Images/Products/" + entityRequest.getProId());
				folder.mkdirs();
				Path path = Paths.get(folder.getPath());
				InputStream inputStream = file.getInputStream();
				Files.copy(inputStream, path.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
				entityRequest
						.setFeatureImgPath(entityRequest.getProId() + "/" + file.getOriginalFilename().toLowerCase());
				if (entityRequest.getProQuantity() > 0) {
					entityRequest.setProStatus("Still Stock");
				} else {
					entityRequest.setProStatus("Out Of Stock");
				}
				service.create(entityRequest);
					File folderMulti = new File("ImagesChildent/Products/" + entityRequest.getProId());
					folderMulti.mkdirs();
					Path pathChild = Paths.get(folderMulti.getPath());
					for (MultipartFile childFile : Mutifile) {
						InputStream inputChildStream = childFile.getInputStream();
						Files.copy(inputChildStream, pathChild.resolve(childFile.getOriginalFilename()),
								StandardCopyOption.REPLACE_EXISTING);
						String imgPath = entityRequest.getProId() + "/" + childFile.getOriginalFilename().toLowerCase();
						ProductImageRequest entityChildRequest = new ProductImageRequest(idProImgIdentity(),
								entityRequest, imgPath, date, false);
						ProductImage entityConvertRequest = modelMapper.map(entityChildRequest, ProductImage.class);
						serviceProImg.create(entityConvertRequest);
				}
				ObjectReponse objectReponse = new ObjectReponse("Success", 201, 0, 120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.CREATED);
			} else {
				ObjectReponse objectReponse = new ObjectReponse(
						"This product cannot be created, please check the product information", 400, 0, 120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/Product/{product_id}")
//	@PreAuthorize("hasRole('MODERATOR') and hasRole('PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<?> update_product(@PathVariable("product_id") String id, @RequestPart(required = false) String json,
			@RequestPart(required = false) @ApiParam(required = true, value = "") MultipartFile file,
			@RequestPart(required = false) @ApiParam(required = true, value = "") MultipartFile[] Mutifile) {
		try {
			Date date = Date.from(Instant.now());
			if (service.getById(id) != null) {
				Boolean check = false;
				for (OrdersProDetail item : service.getById(id).getOrdersprodetails()) {
					if (item.getOrderspro().getOrProStatus().equals("Wait for confirmation") == true
							|| item.getOrderspro().getOrProStatus().equals("Confirm") == true
							|| item.getOrderspro().getOrProStatus().equals("Delivery") == true) {
						check = true;
					}
				}
				ProductRequest dtoRequest = mapper.reader().forType(ProductRequest.class).readValue(json);
				if (check == true && dtoRequest.getProTurnOn() == false) {
					ObjectReponse objectReponse = new ObjectReponse(
							"This product cannot be turn off, , please check the product again", 400, 0, 120, "Minute");
					return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
				} else {
					Category cate = serviceCate.getById(dtoRequest.getCategory_id());
					Product entity = service.getById(id);
					Product entityRequest = modelMapper.map(dtoRequest, Product.class);
					List<Product> entityList = service.getAll();
					List<String> idImgChild = dtoRequest.getIdImgChild();
					entityRequest.setProId(id);
					entityRequest.setCreatedAt(entity.getCreatedAt());
					entityRequest.setCategory(cate);
					entityRequest.setUpdatedAt(date);
					if (entityRequest.getProName() == null || entityRequest.getProName().isEmpty()) {
						entityRequest.setProName(entity.getProName());
					}
					if (entityRequest.getProPrice() == 0) {
						entityRequest.setProPrice(entity.getProPrice());
					}
					if (entityRequest.getProContent() == null || entityRequest.getProContent().isEmpty()) {
						entityRequest.setProContent(entity.getProContent());
					}
					if (entityRequest.getProBrand() == null || entityRequest.getProBrand().isEmpty()) {
						entityRequest.setProBrand(entity.getProBrand());
					}
					if (entityRequest.getProTurnOn() == null) {
						entityRequest.setProTurnOn(entity.getProTurnOn());
					}
					if (entityRequest.getProStatus() == null || entityRequest.getProStatus().isEmpty()) {
						entityRequest.setProStatus(entity.getProStatus());
					}
					entityRequest.setIsDelete(entity.getIsDelete());
					if (entityList.stream().filter(
							n -> n.getProName().equals(entityRequest.getProName())&& n.getIsDelete() == false && n.getProId() != id) != null) {
						if (file == null) {
							entityRequest.setFeatureImgPath(entity.getFeatureImgPath());
						} else {
							// delete old image
							if (dtoRequest.getFeatureImgPath() != null) {
								File directoryToDelete = new File("Images/Products/" + id);
								FileSystemUtils.deleteRecursively(directoryToDelete);
							}
							File folder = new File("Images/Products/" + entityRequest.getProId());
							folder.mkdirs();
							Path path = Paths.get(folder.getPath());
							InputStream inputStream = file.getInputStream();
							Files.copy(inputStream, path.resolve(file.getOriginalFilename()),
									StandardCopyOption.REPLACE_EXISTING);
							entityRequest.setFeatureImgPath(
									entityRequest.getProId() + "/" + file.getOriginalFilename().toLowerCase());
						}
						if (idImgChild != null && Mutifile!= null) {
							if (idImgChild.size() ==  Mutifile.length ) {
								for (String string : idImgChild) {
									if(serviceProImg.getById(string) == null ) {
										ObjectReponse objectReponse = new ObjectReponse(
												"This product cannot be updated, please check the product information", 400,
												0, 120, "Minute");
										return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
									}
								}
								for (int i = 0; i < Mutifile.length; i++) {
									String idImg = idImgChild.get(i);
									ProductImage proImg = serviceProImg.getById(idImg);
									File directoryToDelete = new File("ImagesChildent/Products/" + idImgChild.get(i));
									FileSystemUtils.deleteRecursively(directoryToDelete);
									Path path = Paths.get("ImagesChildent/Products/" + id);
									InputStream inputStream = Mutifile[i].getInputStream();
									Files.copy(inputStream, path.resolve(Mutifile[i].getOriginalFilename()),
											StandardCopyOption.REPLACE_EXISTING);
									proImg.setProImgPath(
											id + "/" + Mutifile[i].getOriginalFilename().toLowerCase());
									proImg.setUpdatedAt(date);
									serviceProImg.create(proImg);
								}
							} else {
								ObjectReponse objectReponse = new ObjectReponse(
										"This product cannot be updated, please check the product information", 400,
										0, 120, "Minute");
								return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
							}
						}
						if (entityRequest.getProQuantity() > 0) {
							entityRequest.setProStatus("Still Stock");
						} else {
							entityRequest.setProStatus("Out Of Stock");
						}
						service.create(entityRequest);
						ObjectReponse objectReponse = new ObjectReponse("Success", 200, 0, 120, "Minute");
						return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
					} else {
						ObjectReponse objectReponse = new ObjectReponse(
								"This product cannot be updated, please check the product information", 400, 0, 120,
								"Minute");
						return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
					}
				}

			} else {
				ObjectReponse objectReponse = new ObjectReponse("This product does not exist yet", 404, 0, 120,
						"Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
			ObjectReponse objectReponse = new ObjectReponse(e.toString(), 500, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/Product/{product_id}")
//	@PreAuthorize("hasRole('MODERATOR') and hasRole('PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<?> delete_product(@PathVariable("product_id") String id) {
		try {
			Date date = Date.from(Instant.now());
			if (service.getById(id) != null) {
				Product entity = service.getById(id);
				entity.setUpdatedAt(date);
				if (entity.getProTurnOn() == false) {
					if (entity.getOrdersprodetails().size() == 0) {
						if (entity.getFeatureImgPath() != null) {
							File directoryToDelete = new File("Images/Products/" + entity.getProId());
							FileSystemUtils.deleteRecursively(directoryToDelete);
						}
						if (entity.getProductImages() != null) {
							File directoryToDelete = new File("ImagesChildent/Products/" + entity.getProId());
							FileSystemUtils.deleteRecursively(directoryToDelete);
							for (ProductImage proImg : entity.getProductImages()) {
								serviceProImg.delete(proImg);
							}
						}
						service.delete(entity);
						ObjectReponse objectReponse = new ObjectReponse("Success", 200, 0, 120, "Minute");
						return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
					} else {
						Boolean check = false;
						for (OrdersProDetail item : entity.getOrdersprodetails()) {
							if (item.getOrderspro().getOrProStatus().equals("Wait for confirmation") == true
									|| item.getOrderspro().getOrProStatus().equals("Confirm") == true
									|| item.getOrderspro().getOrProStatus().equals("Delivery") == true) {
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
									"Many orders have not been processed yet, please check again", 400, 0, 120,
									"Minute");
							return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
						}
					}
				} else {
					ObjectReponse objectReponse = new ObjectReponse(
							"The product does not turn off, please check the product again", 400, 0, 120, "Minute");
					return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
				}
			} else {
				ObjectReponse objectReponse = new ObjectReponse("This product does not exist yet", 404, 0, 120,
						"Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public String idProIdentity() {
		LocalDate today = LocalDate.now();
		int numberUser = service.getCountProByDate(today);
		int year = (today.getYear() % 100);
		String number = String.valueOf(numberUser);
		String id = null;
		switch (number.length()) {
		case 1:
			if (numberUser != 9) {
				id = "P" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "000" + (numberUser + 1);
			} else {
				id = "P" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "00" + (numberUser + 1);
			}
			break;
		case 2:
			if (numberUser != 99) {
				id = "P" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "00" + (numberUser + 1);
			} else {
				id = "P" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "0" + (numberUser + 1);
			}
			break;
		case 3:
			if (numberUser != 999) {
				id = "P" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "0" + (numberUser + 1);
			} else {
				id = "P" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "" + (numberUser + 1);
			}
			break;
		case 4:
			id = "P" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "" + (numberUser + 1);
			break;
		default:
			System.out.print("Id error");
			break;
		}
		return id;
	}

	public String idProImgIdentity() {
		LocalDate today = LocalDate.now();
		int numberUser = serviceProImg.countProImgByDate(today);
		int year = (today.getYear() % 100);
		String number = String.valueOf(numberUser);
		String id = null;
		switch (number.length()) {
		case 1:
			if (numberUser != 9) {
				id = "PI" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "000" + (numberUser + 1);
			} else {
				id = "PI" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "00" + (numberUser + 1);
			}
			break;
		case 2:
			if (numberUser != 99) {
				id = "PI" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "00" + (numberUser + 1);
			} else {
				id = "PI" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "0" + (numberUser + 1);
			}
			break;
		case 3:
			if (numberUser != 999) {
				id = "PI" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "0" + (numberUser + 1);
			} else {
				id = "PI" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "" + (numberUser + 1);
			}
			break;
		case 4:
			id = "PI" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "" + (numberUser + 1);
			break;
		default:
			System.out.print("Id error");
			break;
		}
		return id;
	}

}