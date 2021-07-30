package com.dao.wethemany.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dao.wethemany.models.Product;
import com.dao.wethemany.models.Purchasing;
import com.dao.wethemany.models.User;
import com.dao.wethemany.repository.ProductRepository;
import com.dao.wethemany.repository.UserRepository;
import com.dao.wethemany.response.MessageResponse;
import com.dao.wethemany.services.PurchasingProductServices;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth/user/")
public class UserController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private PurchasingProductServices purchasingProductServices;
	
	
	@Autowired
	private UserRepository userRepository;

	
	@GetMapping()
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?>  gettProduct() {
		
		System.out.println("======================         gettProduc  ===========               ");
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
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?>  gettProductById(@PathVariable(name = "id") String id) {
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
	
	
	// Product Section
	
	
	@PostMapping("/purchaseProduct")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?>  purchaseProduct(@RequestBody Purchasing purchasing, Authentication authentication) {
        MessageResponse messageResponse=new MessageResponse();
		
        System.out.println("getCredentials "+authentication.getCredentials()+" getName " +authentication.getName());

		User user=userRepository.findByEmail(authentication.getName()).get();
		if(user !=null ) {
			if(purchasing !=null ) {
				
				purchasing.setUserEmail(authentication.getName());
				purchasing.setUserId(user.getId());
				purchasingProductServices.purchaseProduct(purchasing);
				
		        messageResponse.setHttpStatus(HttpStatus.OK);
		        messageResponse.setReturnStatus(400);
		        messageResponse.setMessage("Sucessfully Retrieved Data");			
			}else {			
		        messageResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
		        messageResponse.setReturnValueList(null);
		        messageResponse.setMessage("No data in Purchasing");
			}
			
		}else {
			
	        messageResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
	        messageResponse.setReturnValueList(null);
	        messageResponse.setMessage("User Not Found");
		}

		
		return ResponseEntity.ok(messageResponse);
	}
	
	
	@GetMapping("/getAllPurchasedProduct")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?>  getAllPurchasedProduct(Authentication authentication) {
		
        MessageResponse messageResponse= purchasingProductServices.getAllPurchaseHistory(authentication.getName());

		return ResponseEntity.ok(messageResponse);
	}
	
	@DeleteMapping("/deletePurchasedProductById/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?>  deletePurchasedProductById(@PathVariable(name = "id") String id) {
		
        MessageResponse messageResponse= purchasingProductServices.deletePurchaseHistoryById(id);
		return ResponseEntity.ok(messageResponse);
	}
	
	
	
	

}
