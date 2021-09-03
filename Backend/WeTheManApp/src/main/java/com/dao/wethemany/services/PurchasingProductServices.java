package com.dao.wethemany.services;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.dao.wethemany.models.Carts;
import com.dao.wethemany.models.Product;
import com.dao.wethemany.models.PurchasedProduct;
import com.dao.wethemany.models.Purchasing;
import com.dao.wethemany.repository.Carts_Repository;
import com.dao.wethemany.repository.ProductRepository;
import com.dao.wethemany.repository.PurchasingInfoRepository;
import com.dao.wethemany.response.MessageResponse;

@Service
public class PurchasingProductServices {
	
	@Autowired
	private PurchasingInfoRepository purchasingInfoRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	Carts_Repository carts_Repository;
	
	@Autowired
	MongoOperations mongoOperations;
	
	public void purchaseProduct(Purchasing purchasing) {
		
		purchasing.setPurchasedDate(new Date());
		
		for(PurchasedProduct purchasedProduct:purchasing.getPurchasedproduct()) {
	        
			Query query6 = new Query();
	        query6.addCriteria(Criteria.where("_id").is(purchasedProduct.getCartId()));	        
	        Update update6 = new Update();
	        update6.set("cartsStatus", "False");
	        
	        //FindAndModifyOptions().returnNew(true) = newly updated document
	        //FindAndModifyOptions().returnNew(false) = old document (not update yet)
	        mongoOperations.findAndModify(
	                query6, update6, 
	                new FindAndModifyOptions(), Carts.class);
	        
		}
		
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
		
		messageResponse.setPurchasedValueList(purchasingResponse);
        messageResponse.setHttpStatus(HttpStatus.OK);
        messageResponse.setReturnStatus(1);
        messageResponse.setMessage("Sucessfully Retrieved Data");	
		}
		else {
			
	        messageResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
	        messageResponse.setReturnStatus(0);
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
	        messageResponse.setReturnStatus(1);
	        messageResponse.setMessage("Sucessfully Retrieved Data");	
		}else {
	        messageResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
	        messageResponse.setReturnStatus(0);
	        messageResponse.setMessage("Sucessfully Not Retrieved Data");
		}

		
		
		return messageResponse;
		
	}
	
	public Purchasing getPurchasedById(String id) {
		
		return purchasingInfoRepository.findById(id).get();
	}

}
