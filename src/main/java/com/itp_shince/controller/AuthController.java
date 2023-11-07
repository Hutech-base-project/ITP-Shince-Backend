package com.itp_shince.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itp_shince.config.TwilioConfig;
import com.itp_shince.dto.reponse.JwtResponse;
import com.itp_shince.dto.reponse.ObjectReponse;
import com.itp_shince.dto.reponse.TokenRefreshResponse;
import com.itp_shince.dto.request.CheckLoginRequest;
import com.itp_shince.dto.request.ForgotPasswordRequest;
import com.itp_shince.dto.request.LoginRequest;
import com.itp_shince.dto.request.OtpRequest;
import com.itp_shince.dto.request.SignupRequest;
import com.itp_shince.dto.request.TokenRefreshRequest;
import com.itp_shince.jwt.JwtUtils;
import com.itp_shince.model.ERole;
import com.itp_shince.model.RefreshToken;
import com.itp_shince.model.UserDetailsImpl;
import com.itp_shince.model.UserRole;
import com.itp_shince.model.Users;
import com.itp_shince.service.OtpService;
import com.itp_shince.service.RefreshTokenService;
import com.itp_shince.service.RoleService;
import com.itp_shince.service.UserRoleService;
import com.itp_shince.service.UserService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequestMapping("/auth")
public class AuthController {
	@Value("${bezkoder.app.jwtSecret}")
	private String jwtSecret;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	private TwilioConfig twilioConfig;

	@Autowired
	public OtpService otpService;

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	RefreshTokenService refreshTokenService;

	HttpSession session;

	HttpHeaders responseHeaders = new HttpHeaders();

	ObjectMapper mapper = new ObjectMapper();

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
		try {
			session = request.getSession();
			Users user = userService.getByPhoneNumber(loginRequest.getPhoneNumber());
			if (user != null) {
				OtpRequest otp  = new OtpRequest(loginRequest.getOtp(),loginRequest.getPhoneNumber());
				ObjectReponse objectOTPReponse =  validateOtp(otp);
				if (objectOTPReponse.getResponseStatus() == 400) {
					return new ResponseEntity<>(objectOTPReponse, responseHeaders, HttpStatus.BAD_REQUEST);
				}else if(objectOTPReponse.getResponseStatus() == 404) {
					return new ResponseEntity<>(objectOTPReponse, responseHeaders, HttpStatus.NOT_FOUND);
				}else if(objectOTPReponse.getResponseStatus() == 500) {
					return new ResponseEntity<>(objectOTPReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
				}else {
					Authentication authentication = authenticationManager.authenticate(
							new UsernamePasswordAuthenticationToken(user.getUsPhoneNo(), loginRequest.getPassword()));
					SecurityContextHolder.getContext().setAuthentication(authentication);
					UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
					String jwt = jwtUtils.generateJwtToken(userDetails);
					List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
							.collect(Collectors.toList());
					RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId(), jwt);
					JwtResponse jwtResponse = new JwtResponse(jwt, refreshToken.getJwtId(), userDetails.getId(),
							userDetails.getUsername(), userDetails.getPhoneNumber(), roles,request);
					request.getSession().setAttribute(jwtResponse.getId()+jwtResponse.getUserName(), jwtResponse);
					ObjectReponse objectReponse = new ObjectReponse("Success", 200, jwtResponse, 120, "Minute");
					return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
				}			
			} else {
				ObjectReponse objectReponse = new ObjectReponse("Phone number or Password error", 404, 0, 120,
						"Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Phone number or Password error", 400, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/checkLogin")
	public ResponseEntity<?> checkUser(@RequestBody CheckLoginRequest checkLoginRequest, HttpServletRequest request) {
		try {
			Users user = userService.getByPhoneNumber(checkLoginRequest.getPhoneNumber());
			if (user != null) {
					authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsPhoneNo(), checkLoginRequest.getPassword()));
					ObjectReponse objectReponse = new ObjectReponse("Success", 200, 0, 120, "Minute");
					return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);		
			} else {
				ObjectReponse objectReponse = new ObjectReponse("Phone number or Password error", 404, 0, 120,
						"Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			ObjectReponse objectReponse = new ObjectReponse("Phone number or Password error", 400, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@RequestBody TokenRefreshRequest request) {

		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(request.getToken());
			ObjectReponse objectReponse = new ObjectReponse("JWT token is not expired: {}", 400, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
		} catch (SignatureException e) {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(request.getToken());
			ObjectReponse objectReponse = new ObjectReponse("Invalid JWT signature: {}", 400, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
		} catch (MalformedJwtException e) {
			ObjectReponse objectReponse = new ObjectReponse("Invalid JWT token: {}", 400, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
		} catch (ExpiredJwtException e) {
			Date date = Date.from(Instant.now());
			String requestRefreshToken = request.getRefreshToken();
			String refreshToken = request.getRefreshToken();
			RefreshToken entity = refreshTokenService.findByToken(refreshToken);
			if (entity == null) {
				ObjectReponse objectReponse = new ObjectReponse("Token does not exist", 400, 0, 120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
			} else if (entity.getJwtId().equals(request.getRefreshToken()) == false) {
				ObjectReponse objectReponse = new ObjectReponse("Refresh Token has not yet expired", 400, 0, 120,
						"Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
			} else if (entity.isIsUsed()) {
				ObjectReponse objectReponse = new ObjectReponse("Refresh Token has been used", 400, 0, 120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
			} else if (entity.isIsRevorked()) {
				ObjectReponse objectReponse = new ObjectReponse("Refresh Token has been revoked", 400, 0, 120,
						"Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
			} else if (entity.getExpiryDate().compareTo(date) < 0) {
				ObjectReponse objectReponse = new ObjectReponse("Refresh Token expired", 400, 0, 120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
			} else {
				entity.setIsUsed(true);
				refreshTokenService.save(entity);
				Users user = userService.getById(entity.getUsers().getUsId());
				String tokenResponse = jwtUtils.generateTokenFromUsername(user.getUsUserName(), user.getUsId(),
						user.getUsEmailNo());
				ObjectReponse objectReponse = new ObjectReponse("Success", 200,
						new TokenRefreshResponse(tokenResponse, requestRefreshToken), 120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
			}

		} catch (UnsupportedJwtException e) {
			ObjectReponse objectReponse = new ObjectReponse("JWT token is unsupported: {}", 400, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
		} catch (IllegalArgumentException e) {
			ObjectReponse objectReponse = new ObjectReponse("JWT claims string is empty: {}", 400, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {

		Date date = Date.from(Instant.now());
		try {
			if (signUpRequest.getPhoneNumber() != null && signUpRequest.getPassword() != null
					&& signUpRequest.getUsername() != null) {
				if (testUsingStrictRegex(signUpRequest.getPhoneNumber())) {
					if (userService.checkPhoneNumber(signUpRequest.getPhoneNumber())) {
						return new ResponseEntity<>("Error: Phone number is already in use!", responseHeaders,
								HttpStatus.NOT_FOUND);
					}else {
						OtpRequest otp  = new OtpRequest(signUpRequest.getOtp(),signUpRequest.getPhoneNumber());
						ObjectReponse objectOTPReponse =  validateOtp(otp);
						if (objectOTPReponse.getResponseStatus() == 400) {
							return new ResponseEntity<>(objectOTPReponse, responseHeaders, HttpStatus.BAD_REQUEST);
						}else if(objectOTPReponse.getResponseStatus() == 404) {
							return new ResponseEntity<>(objectOTPReponse, responseHeaders, HttpStatus.NOT_FOUND);
						}else if(objectOTPReponse.getResponseStatus() == 500) {
							return new ResponseEntity<>(objectOTPReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
						}else {
							Users user = new Users(idUserIentity(), signUpRequest.getUsername(),
									encoder.encode(signUpRequest.getPassword()), signUpRequest.getPhoneNumber());
							Set<UserRole> roles = new HashSet<>();
							user.setIsAdmin(false);
							UserRole userRole = new UserRole();
							userRole.setUsers(user);
							userRole.setRole(roleService.getByName(ERole.ROLE_USER.toString()));
							roles.add(userRole);
							userService.create(user);
							for (UserRole role : roles) {
								role.setCreatedAt(date);
								userRoleService.create(role);
							}
							user.setCreatedAt(date);
							user.setUserRoles(roles);
							userService.create(user);
							ObjectReponse objectReponse = new ObjectReponse("Success", 200, 0, 120, "Minute");
							return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
						}			
					}			
				} else {
					ObjectReponse objectReponse = new ObjectReponse("Invalid phone number", 400, 0, 120, "Minute");
					return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
				}
			} else {
				ObjectReponse objectReponse = new ObjectReponse(
						"This account cannot be created, please check the account information", 400, 0, 120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {

			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PostMapping("/forgot_password")
	public ResponseEntity<?> processForgotPassword(@RequestBody ForgotPasswordRequest forgotPassword) {
		try {
			Users entity = userService.getByPhoneNumber(forgotPassword.getPhoneNumber());	
			if(entity != null) {
				OtpRequest otp  = new OtpRequest(forgotPassword.getOtp(),forgotPassword.getPhoneNumber());
				ObjectReponse objectOTPReponse =  validateOtp(otp);
				if (objectOTPReponse.getResponseStatus() == 400) {
					return new ResponseEntity<>(objectOTPReponse, responseHeaders, HttpStatus.NOT_FOUND);
				}else if(objectOTPReponse.getResponseStatus() == 404) {
					return new ResponseEntity<>(objectOTPReponse, responseHeaders, HttpStatus.BAD_REQUEST);
				}else if(objectOTPReponse.getResponseStatus() == 500) {
					return new ResponseEntity<>(objectOTPReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
				}else {
					entity.setUsPassword(encoder.encode(forgotPassword.getPassword()));
					userService.create(entity);
					otpService.clearOTP(forgotPassword.getPhoneNumber());
					ObjectReponse objectReponse = new ObjectReponse("Success", 200, 0, 120, "Minute");
					return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
				}
			}else {
				return new ResponseEntity<>("Unregistered email", responseHeaders, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/generateOTP/{phoneNumber}")
	public ResponseEntity<?> generateOTP(@PathVariable("phoneNumber") String phoneNumber, HttpServletRequest request) {
		try {
			session = request.getSession();
			Date dateNow = Date.from(Instant.now());
			int count = 0;
			if (Character.toString(phoneNumber.charAt(0)).equals("0") == true) {
				phoneNumber = phoneNumber.substring(1);
			}
			if (session.getAttribute("countGenerate") != null) {
				count = (int) session.getAttribute("countGenerate");
				if (count > 5) {
					Date dateGenerate = (Date) session.getAttribute("timeGenerate");
					long diff = dateNow.getTime() - dateGenerate.getTime();
					long diffMinutes = diff / (60 * 1000);
					if (diffMinutes >= 15) {
						otpService.clearOTP("+84" + phoneNumber);
						request.getSession().removeAttribute("countGenerate");
						request.getSession().removeAttribute("timeGenerate");
					}
				}
			}

			if (count <= 5) {
				if (session.getAttribute("countGenerate") != null) {
					request.getSession().setAttribute("countGenerate", count + 1);
				} else {
					request.getSession().setAttribute("countGenerate", count + 1);
					request.getSession().setAttribute("timeGenerate", dateNow);
				}
				String convertPhone = "+84" + phoneNumber;
				PhoneNumber to = new PhoneNumber(convertPhone);// to
				PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber()); // from
				int otp = otpService.generateOTP(convertPhone);
				String otpMessage = "\nDear Customer ,\nYour OTP is:  " + otp + "\nThank you for using our service.";
				Message.creator(to, from, otpMessage).create();
				ObjectReponse objectReponse = new ObjectReponse("Success", 200, 0, 120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
			} else {
				ObjectReponse objectReponse = new ObjectReponse(
						"Send OTP multiple times, please try again after 15 minutes", 404, 0, 120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
			ObjectReponse objectReponse = new ObjectReponse("Invalid phone number", 400, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/validateOtp")
	public ResponseEntity<?> checkOtp(@RequestBody OtpRequest otp) {
		// Validate the Otp
		if (Integer.parseInt(otp.getOtp()) >= 0 && otp.getPhoneNumber() != null) {
			if (Character.toString(otp.getPhoneNumber().charAt(0)).equals("0") == true) {
				otp.setPhoneNumber("+84" + otp.getPhoneNumber().substring(1));
			} else {
				otp.setPhoneNumber("+84" + otp.getPhoneNumber());
			}
			int serverOtp = otpService.getOtp(otp.getPhoneNumber());
			if (serverOtp > 0) {
				if (Integer.parseInt(otp.getOtp()) == serverOtp) {
					ObjectReponse objectReponse = new ObjectReponse("Success", 200, 0, 120, "Minute");
					return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
				} else {
					ObjectReponse objectReponse = new ObjectReponse("Otp is not correct", 404, 0, 120, "Minute");
					return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
				}
			} else {
				ObjectReponse objectReponse = new ObjectReponse("Invalid phone number or code otp", 400, 0, 120,
						"Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.BAD_REQUEST);
			}
		} else {
			ObjectReponse objectReponse = new ObjectReponse("Sent otp fail", 500, 0, 120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/Session/{id}")
	public ResponseEntity<?> getSessionById(@PathVariable("id") String id, HttpServletRequest request){		
		try {
			session = request.getSession();			
			if(session.getAttribute(id) != null) {
				JwtResponse dtoReponse = (JwtResponse) session.getAttribute(id);
				ObjectReponse objectReponse = new ObjectReponse("Success", 200,dtoReponse ,120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.OK);
			}else {
				ObjectReponse objectReponse = new ObjectReponse("Session could not be found", 404,0 ,120, "Minute");
				return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
			ObjectReponse objectReponse = new ObjectReponse("Connect server fail", 500, 0,
					120, "Minute");
			return new ResponseEntity<>(objectReponse, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	private ObjectReponse validateOtp(OtpRequest otp) {
		// Validate the Otp
		if (Integer.parseInt(otp.getOtp()) >= 0 && otp.getPhoneNumber() != null) {
			if (Character.toString(otp.getPhoneNumber().charAt(0)).equals("0") == true) {
				otp.setPhoneNumber("+84" + otp.getPhoneNumber().substring(1));
			} else {
				otp.setPhoneNumber("+84" + otp.getPhoneNumber());
			}
			int serverOtp = otpService.getOtp(otp.getPhoneNumber());
			if (serverOtp > 0) {
				if (Integer.parseInt(otp.getOtp()) == serverOtp) {
					ObjectReponse objectReponse = new ObjectReponse("Success", 200, 0, 120, "Minute");
					return objectReponse;
				} else {
					ObjectReponse objectReponse = new ObjectReponse("Otp is not correct", 404, 0, 120, "Minute");
					return objectReponse;
				}
			} else {
				ObjectReponse objectReponse = new ObjectReponse("Invalid phone number or code otp", 400, 0, 120,
						"Minute");
				return objectReponse;
			}
		} else {
			ObjectReponse objectReponse = new ObjectReponse("Sent otp fail", 500, 0, 120, "Minute");
			return objectReponse;
		}
	}

	public String idUserIentity() {
		LocalDate today = LocalDate.now();
		int numberUser = userService.getCountUserByDate(today);
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

	public boolean testUsingStrictRegex(String phoneNumber) {
		;
		String regexPattern = "((^(\\+84|84|0|0084){1})(3|5|7|8|9))+([0-9]{8})$";
		return Pattern.compile(regexPattern).matcher(phoneNumber).matches();
	}

}
