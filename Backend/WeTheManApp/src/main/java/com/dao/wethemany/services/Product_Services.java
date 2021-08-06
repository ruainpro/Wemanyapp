package com.dao.wethemany.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.dao.wethemany.models.Product;
import com.dao.wethemany.repository.ProductRepository;
import com.dao.wethemany.response.MessageResponse;

@Service
public class Product_Services {
	
	@Autowired
	private ProductRepository productRepository;
	
	// Method For Geting All Product
	public MessageResponse getAllProductInfo() {
		
        MessageResponse messageResponse=new MessageResponse();
		
		List<Product> returnValue=productRepository.findAll();
		if(returnValue !=null && !returnValue.isEmpty()) {
	        messageResponse.setHttpStatus(HttpStatus.OK);
	        messageResponse.setReturnValueList(returnValue);
	        messageResponse.setReturnStatus(1);
	        messageResponse.setMessage("Sucessfully Retrieved Data");			
		}else {
			
			
	        messageResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
	        messageResponse.setReturnStatus(0);
	        messageResponse.setReturnValueList(null);
	        messageResponse.setMessage("Sucessfully Retrieved Data");
		}
		
		return messageResponse;
	}
	
	public MessageResponse getAllProductById(String productId) {
		
	      MessageResponse messageResponse=new MessageResponse();

			Product returnValue=productRepository.findById(productId).get();
			if(returnValue.getId() !=null ) {
		        messageResponse.setHttpStatus(HttpStatus.OK);
		        messageResponse.setReturnValue(returnValue);
		        messageResponse.setReturnStatus(1);
		        messageResponse.setMessage("Sucessfully Retrieved Data");			
			}else {			
		        messageResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
		        messageResponse.setReturnValueList(null);
		        messageResponse.setReturnStatus(0);
		        messageResponse.setMessage("Sucessfully Retrieved Data");
			}
			
			return messageResponse;
			
	}
	
	

}
