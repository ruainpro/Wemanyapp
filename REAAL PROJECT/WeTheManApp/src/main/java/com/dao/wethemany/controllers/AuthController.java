package com.dao.wethemany.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.Multipart;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dao.wethemany.models.ERole;
import com.dao.wethemany.models.Purchasing;
import com.dao.wethemany.models.Role;
import com.dao.wethemany.models.User;
import com.dao.wethemany.request.LoginRequest;
import com.dao.wethemany.request.SignupRequest;
import com.dao.wethemany.response.JwtResponse;
import com.dao.wethemany.response.MessageResponse;
import com.dao.wethemany.services.UserDetailsImpl;
import com.dao.wethemany.repository.UserRepository;
import com.dao.wethemany.repository.RoleRepository;
import com.dao.wethemany.jwtsecurity.JwtUtils;

import com.dao.wethemany.services.PurchasingProductServices;
import com.dao.wethemany.services.Payment_Services;
import com.dao.wethemany.services.Others_Services;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	PurchasingProductServices purchasingProductServices;
	
	@Autowired
	Payment_Services payment_Services;
	
	@Autowired
	Others_Services others_Services;
	
	

    @RequestMapping(value = "/getImages/{Images}", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)

    public byte[] getImage(HttpServletResponse response,@PathVariable(name = "Images") String Images) throws IOException {
//		File file =new File("user-photos/"+Images);
		byte[] array = Files.readAllBytes(Paths.get("user-photos/"+Images));
		
		return array;

    }
	




	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		System.out.println(loginRequest.toString());
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getEmail(), 
												 roles));
	}
	

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
//		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//			return ResponseEntity
//					.badRequest()
//					.body(new MessageResponse("Error: Username is already taken!"));
//		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			MessageResponse messageResponse=new MessageResponse();
			messageResponse.setMessage("Error: Email is already in use!");
			messageResponse.setReturnStatus(0);
			messageResponse.setHttpStatus(HttpStatus.ALREADY_REPORTED);
//			messageResponse.set
			return ResponseEntity
					.badRequest()
					.body(messageResponse);
		}

		// Create new user's account
		User user = new User( 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);
		MessageResponse messageResponse=new MessageResponse();
		messageResponse.setMessage("User registered successfully!");

		return ResponseEntity.ok(messageResponse);
	}
	
	
	@PostMapping("/getpaymentWork")
	public ResponseEntity<?>  getAllPurchasedProduct(@RequestBody Purchasing purchasisng) {
//		MessageResponse messageResponse= purchasingProductServices.getAllPurchaseHistory(authentication.getName());

		String isd =payment_Services.createCharge(purchasisng);
		System.out.println(isd);
		return ResponseEntity.ok(isd);
	}
	
	
	@PostMapping("/calculateTheCo02/{productvalue}")
	public ResponseEntity<?>  calculateTheCo02(@PathVariable(name = "productvalue") double productvalue) {

		
		return ResponseEntity.ok(others_Services.calculateC02Emission(productvalue));
	}
	

}
