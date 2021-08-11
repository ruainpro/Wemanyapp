package com.dao.wethemany.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.dao.wethemany.models.Carts;
import com.dao.wethemany.models.Product;
import com.dao.wethemany.models.Purchasing;
import com.dao.wethemany.models.User;
import com.dao.wethemany.repository.Carts_Repository;
import com.dao.wethemany.repository.ProductRepository;
import com.dao.wethemany.repository.UserRepository;
import com.dao.wethemany.response.MessageResponse;
import com.dao.wethemany.services.Cart_Service;
import com.dao.wethemany.services.Payment_Services;
import com.dao.wethemany.services.Product_Services;
import com.dao.wethemany.services.PurchasingProductServices;
import com.stripe.model.Charge;

@CrossOrigin(origins = "*", maxAge = 6000)
@RestController
@RequestMapping("/api/auth/user/")
public class UserController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private PurchasingProductServices purchasingProductServices;
	
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Carts_Repository carts_Repository;

	@Autowired
	private Cart_Service cart_Service ;
	
	@Autowired
	Payment_Services payment_Services;
	
	@Autowired
	Product_Services product_Services;

	
	@GetMapping()
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?>  gettProduct() {
		
		MessageResponse messageResponse=product_Services.getAllProductInfo();
		
		return ResponseEntity.ok(messageResponse);
	}

	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?>  gettProductById(@PathVariable(name = "id") String id) {
		
        MessageResponse messageResponse=product_Services.getAllProductById(id);

		return ResponseEntity.ok(messageResponse);
	}
	
	
	// Product Section
	
	
	@PostMapping("/purchaseProduct")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?>  purchaseProduct(@RequestBody Purchasing purchasing, Authentication authentication) {
        MessageResponse messageResponse=new MessageResponse();
        String isd="";
        purchasing.setUserEmail(authentication.getName());
		User user=userRepository.findByEmail(authentication.getName()).get();
		if(user !=null ) {
			if(purchasing !=null ) {
				
				Map returnMap =payment_Services.createCharge(purchasing);
				isd=returnMap.get("id").toString();
				purchasing.getPaymentInfo().setPaymentreturnsid(isd);
				purchasing.getPaymentInfo().setPaymentreturnRecipt(returnMap.get("receiptlink").toString());


				if(StringUtils.hasText(isd) ==true){
					
					purchasing.setUserId(user.getId());
					purchasing.getPaymentInfo().setPaymentMedium("Credit Card");
				
					purchasingProductServices.purchaseProduct(purchasing);
					messageResponse.setResponseType(isd);
			
			        messageResponse.setHttpStatus(HttpStatus.OK);
			        
			        messageResponse.setReturnStatus(400);
			        messageResponse.setMessage("Sucessfully Retrieved Data");	
				}else {
					
			        messageResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
			        messageResponse.setReturnStatus(400);
			        messageResponse.setMessage("Payment is Not Done");	
				}

		
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
	
	
	
	// Carts 
	
	
	@PostMapping("/addToCart")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?>  addToCart(@RequestBody Carts carts,Authentication authentication) {
		
		MessageResponse messageResponse=cart_Service.addToCartService(carts, authentication.getName());		

		return ResponseEntity.ok(messageResponse);
	}
	
	@GetMapping("/getAllCartdata")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?>  getAllCartdata(Authentication authentication) {
		
        MessageResponse messageResponse= cart_Service.getAllCartsInfo(authentication.getName());

		return ResponseEntity.ok(messageResponse);
	}
	
	@DeleteMapping("/deleteCartdataById/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?>  deleteCartdataById(Authentication authentication,@PathVariable (name="id") String id) {
		
        MessageResponse messageResponse= cart_Service.deleteCartsById(authentication.getName(),id);

		return ResponseEntity.ok(messageResponse);
	}
	
	

}
