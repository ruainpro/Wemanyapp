package com.dao.wethemany.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dao.wethemany.models.Product;
import com.dao.wethemany.repository.ProductRepository;
import com.dao.wethemany.response.MessageResponse;
import com.dao.wethemany.services.FileUploadUtil;
import com.dao.wethemany.services.Others_Services;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth/admin/product/")
public class AdminProductController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private Others_Services others_Services;
	
	@PostMapping()
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?>  postProduct( Product product,
			@RequestParam("image") MultipartFile multipartFile) {
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		System.out.println(product.toString());
        String uploadDir = "user-photos/";
 
        try {			
			product.setImages(FileUploadUtil.saveFile(uploadDir, fileName, multipartFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if(product.getWeight()>=1) {
        	
        	product.setCo2Emission(others_Services.calculateC02Emission(product.getCo2Emission()));
        }
        
        productRepository.save(product);
		
        MessageResponse messageResponse=new MessageResponse();
        messageResponse.setHttpStatus(HttpStatus.OK);
        messageResponse.setMessage("User registered successfully!");
        messageResponse.setReturnStatus(400);
        
		
		return ResponseEntity.ok(messageResponse);
	}
	
	@GetMapping()
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?>  gettProduct() {
        MessageResponse messageResponse=new MessageResponse();
		
		List<Product> returnValue=productRepository.findAll();
		if(returnValue !=null && !returnValue.isEmpty()) {
	        messageResponse.setHttpStatus(HttpStatus.OK);
	        messageResponse.setReturnValueList(returnValue);
	        messageResponse.setReturnStatus(200);
	        messageResponse.setMessage("Sucessfully Retrieved Data");			
		}else {
			
			
	        messageResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
	        messageResponse.setReturnValueList(null);
	        messageResponse.setMessage("Sucessfully Retrieved Data");
		}
		
		return ResponseEntity.ok(messageResponse);
	}

	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?>  gettProduct(@PathVariable(name = "id") String id) {
        MessageResponse messageResponse=new MessageResponse();
		
		Product returnValue=productRepository.findById(id).get();
		if(returnValue.getId() !=null ) {
	        messageResponse.setHttpStatus(HttpStatus.OK);
	        messageResponse.setReturnValue(returnValue);
	        messageResponse.setReturnStatus(200);
	        messageResponse.setMessage("Sucessfully Retrieved Data");			
		}else {			
	        messageResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
	        messageResponse.setReturnValueList(null);
	        messageResponse.setMessage("Sucessfully Retrieved Data");
		}
		
		return ResponseEntity.ok(messageResponse);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?>  deleteProductById(@PathVariable(name = "id") String id) {
        MessageResponse messageResponse=new MessageResponse();
		
			productRepository.deleteById(id);
	        messageResponse.setHttpStatus(HttpStatus.OK);
	        messageResponse.setReturnStatus(200);
	        messageResponse.setMessage("Sucessfully Delete Data");			

		
		return ResponseEntity.ok(messageResponse);
	}
}
