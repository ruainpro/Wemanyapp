package com.dao.wethemany.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dao.wethemany.models.Product;
import com.dao.wethemany.repository.ProductRepository;
import com.dao.wethemany.response.MessageResponse;
import com.dao.wethemany.services.FileUploadUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth/user/")
public class UserController {
	
	@Autowired
	private ProductRepository productRepository;
	

	
	@GetMapping()
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?>  gettProduct() {
        MessageResponse messageResponse=new MessageResponse();
		
		List<Product> returnValue=productRepository.findAll();
		if(returnValue !=null && !returnValue.isEmpty()) {
	        messageResponse.setHttpStatus(HttpStatus.OK);
	        messageResponse.setReturnValueList(returnValue);
	        messageResponse.setReturnStatus(400);
	        messageResponse.setMessage("Sucessfully Retrieved Data");			
		}else {
			
			
	        messageResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
	        messageResponse.setReturnValueList(null);
	        messageResponse.setMessage("Sucessfully Retrieved Data");
		}
		
		return ResponseEntity.ok(messageResponse);
	}

	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?>  gettProduct(@PathVariable(name = "id") String id) {
        MessageResponse messageResponse=new MessageResponse();
		
		Product returnValue=productRepository.findById(id).get();
		if(returnValue.getId() !=null ) {
	        messageResponse.setHttpStatus(HttpStatus.OK);
	        messageResponse.setReturnValue(returnValue);
	        messageResponse.setReturnStatus(400);
	        messageResponse.setMessage("Sucessfully Retrieved Data");			
		}else {			
	        messageResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
	        messageResponse.setReturnValueList(null);
	        messageResponse.setMessage("Sucessfully Retrieved Data");
		}
		
		return ResponseEntity.ok(messageResponse);
	}
	
	

}
