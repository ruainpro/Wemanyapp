package com.dao.wethemany.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.dao.wethemany.models.Product;
import com.dao.wethemany.models.PurchasedProduct;
import com.dao.wethemany.models.Purchasing;
import com.dao.wethemany.repository.ProductRepository;
import com.dao.wethemany.repository.PurchasingInfoRepository;
import com.dao.wethemany.response.MessageResponse;

@Service
public class PurchasingProductServices {
	
	@Autowired
	private PurchasingInfoRepository purchasingInfoRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	public void purchaseProduct(Purchasing purchasing) {
		
		purchasing.setPurchasedDate(new Date());
		
		purchasingInfoRepository.save(purchasing);
		
	}
	
	public MessageResponse getAllPurchaseHistory(String email) {
		
		MessageResponse messageResponse=new MessageResponse();
		
//		List<Product> producttt = new ArrayList<Product>();
		List<Purchasing> purchasingResponse=purchasingInfoRepository.findByUserEmail(email);
		
		if(! purchasingResponse.isEmpty() && purchasingResponse !=null) {
		for (int i = 0; i < purchasingResponse.size(); i++) {
			
			for (int j = 0; j < purchasingResponse.get(i).getPurchasedproduct().size(); j++) {
				
				Product product= productRepository.findById(purchasingResponse.get(i).getPurchasedproduct().get(j).getProductId()).get();
				purchasingResponse.get(i).getPurchasedproduct().get(j).setProductresponse(product);
			}	
			
		}
		
		messageResponse.setReturnValueList(purchasingResponse);
        messageResponse.setHttpStatus(HttpStatus.OK);
        messageResponse.setReturnStatus(200);
        messageResponse.setMessage("Sucessfully Retrieved Data");	
		}
		else {
			
	        messageResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
	        messageResponse.setReturnStatus(400);
	        messageResponse.setMessage("Sucessfully Not Retrieved Data");	
			
		}
		
		
		return messageResponse;
		
	}
	
	
	
	public MessageResponse deletePurchaseHistoryById(String id) {
		
		MessageResponse messageResponse=new MessageResponse();
		
		boolean existOrNot=purchasingInfoRepository.existsById(id);
		if(existOrNot ==true) {
			purchasingInfoRepository.deleteById(id);
	        messageResponse.setHttpStatus(HttpStatus.OK);
	        messageResponse.setReturnStatus(200);
	        messageResponse.setMessage("Sucessfully Retrieved Data");	
		}else {
	        messageResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
	        messageResponse.setReturnStatus(400);
	        messageResponse.setMessage("Sucessfully Not Retrieved Data");
		}

		
		
		return messageResponse;
		
	}

}
