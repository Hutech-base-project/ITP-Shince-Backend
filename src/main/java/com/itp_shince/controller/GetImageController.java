package com.itp_shince.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itp_shince.dto.reponse.ObjectReponse;

@CrossOrigin
@Controller
@RequestMapping("/image")
@SuppressWarnings("null")
public class GetImageController {

	HttpHeaders responseHeaders = new HttpHeaders();

	@GetMapping("/user/{uesr_id}/{photo}")
//	@PreAuthorize("hasRole('USER')")
	@ResponseBody
	public ResponseEntity<?> get_user_images(@PathVariable("uesr_id") String id, @PathVariable("photo") String photo) {
		if (photo != null || !photo.equals(" ")) {
			try {
				Path fileName = Paths.get("Images/Users/" + id, photo);
				byte[] buffet = Files.readAllBytes(fileName);
				String base64EncodedImageBytes = Base64.getEncoder().encodeToString(buffet);
				return ResponseEntity.ok().body(base64EncodedImageBytes);
			} catch (Exception e) {
				ObjectReponse objectReponse = new ObjectReponse("This image is not exist ", 404, 0,
						0, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping("/allUser/{uesr_id}/{photo}")
	@ResponseBody
	public ResponseEntity<?> get_all_user_images(@PathVariable("uesr_id") String id, @PathVariable("photo") String photo) {
		if (photo != null || !photo.equals(" ")) {
			try {
				Path fileName = Paths.get("Images/Users/" + id, photo);
				byte[] buffet = Files.readAllBytes(fileName);
				ByteArrayResource byteArrayResource = new ByteArrayResource(buffet);
				return ResponseEntity.ok().contentLength(buffet.length)
						.contentType(MediaType.parseMediaType("image/png")).body(byteArrayResource);
			} catch (Exception e) {
				ObjectReponse objectReponse = new ObjectReponse("This image is not exist ", 404, 0,
						0, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping("/product/{product_id}/{photo}")
	@ResponseBody
	public ResponseEntity<?> get_product_images(@PathVariable("product_id") String id, @PathVariable("photo") String photo) {
		if (photo != null || !photo.equals(" ")) {
			try {
				Path fileName = Paths.get("Images/Products/" + id, photo);
				byte[] buffet = Files.readAllBytes(fileName);
				ByteArrayResource byteArrayResource = new ByteArrayResource(buffet);
				return ResponseEntity.ok().contentLength(buffet.length)
						.contentType(MediaType.parseMediaType("image/png")).body(byteArrayResource);
			} catch (Exception e) {
				ObjectReponse objectReponse = new ObjectReponse("This image is not exist ", 404, 0,
						0, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping("/product-child/{product_image_id}/{photo}")
	@ResponseBody
	public ResponseEntity<?> get_product_child_images(@PathVariable("product_image_id") String id, @PathVariable("photo") String photo) {
		if (photo != null || !photo.equals(" ")) {
			try {
				Path fileName = Paths.get("ImagesChildent/Products/" + id, photo);
				byte[] buffet = Files.readAllBytes(fileName);
				ByteArrayResource byteArrayResource = new ByteArrayResource(buffet);
				return ResponseEntity.ok().contentLength(buffet.length)
						.contentType(MediaType.parseMediaType("image/png")).body(byteArrayResource);
			} catch (Exception e) {
				ObjectReponse objectReponse = new ObjectReponse("This image is not exist ", 404, 0,
						0, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping("/service/{service_id}/{photo}")
	@ResponseBody
	public ResponseEntity<?> get_image(@PathVariable("service_id") String id, @PathVariable("photo") String photo) {
		if (photo != null || !photo.equals("")) {
			try {
				Path filename = Paths.get("Images/Services/" + id, photo);
				byte[] buffer = Files.readAllBytes(filename);
				ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
				return ResponseEntity.ok().contentLength(buffer.length)
						.contentType(MediaType.parseMediaType("image/png")).body(byteArrayResource);
			} catch (Exception e) {
				ObjectReponse objectReponse = new ObjectReponse("This image is not exist ", 404, 0,
						0, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping("/service-child/{service_image_id}/{photo}")
	@ResponseBody
	public ResponseEntity<?> get_service_child_images(@PathVariable("service_image_id") String id, @PathVariable("photo") String photo) {
		if (photo != null || !photo.equals(" ")) {
			try {
				Path fileName = Paths.get("ImagesChildent/Services/" + id, photo);
				byte[] buffet = Files.readAllBytes(fileName);
				ByteArrayResource byteArrayResource = new ByteArrayResource(buffet);
				return ResponseEntity.ok().contentLength(buffet.length)
						.contentType(MediaType.parseMediaType("image/png")).body(byteArrayResource);
			} catch (Exception e) {
				ObjectReponse objectReponse = new ObjectReponse("This image is not exist ", 404, 0,
						0, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
		}
		return ResponseEntity.badRequest().build();
	}
}
