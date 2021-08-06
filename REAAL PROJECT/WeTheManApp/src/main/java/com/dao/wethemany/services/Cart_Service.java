package com.dao.wethemany.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.dao.wethemany.repository.Carts_Repository;
import com.dao.wethemany.repository.ProductRepository;
import com.dao.wethemany.response.MessageResponse;
import com.dao.wethemany.models.Carts;
import com.dao.wethemany.models.Product;

@Service
public class Cart_Service {
	
	@Autowired
	Carts_Repository carts_Repository;
	
	@Autowired
	ProductRepository productRepository;
	
	public MessageResponse getAllCartsInfo(String email) {
		
		MessageResponse messageResponse= new MessageResponse();
		
		List<Carts> carts=carts_Repository.findByCartedByAndCartsStatus(email,"True");
		
		if(carts !=null) {
		
			for (int i = 0; i < carts.size(); i++) {
				Product product=productRepository.findById(carts.get(i).getProductid()).get();
				carts.get(i).setProduct(product);
			}
			
			messageResponse.setCartsValueList(carts);
			messageResponse.setMessage("Sucessfully Fetched Data");
			messageResponse.setReturnStatus(1);
			messageResponse.setHttpStatus(HttpStatus.OK);
		}else {
			
			messageResponse.setCartsValueList(carts);
			messageResponse.setMessage("Fail to Fetched Data");
			messageResponse.setReturnStatus(0);
			messageResponse.setHttpStatus(HttpStatus.NOT_FOUND);
			
		}
		
		return messageResponse;
		
	}
	
	public MessageResponse deleteCartsById(String email, String id) {
		
		MessageResponse messageResponse= new MessageResponse();
		
		boolean returnStatus=carts_Repository.findByIdAndCartedBy(id,email);
		
		if(returnStatus==true) {
		
			
			carts_Repository.deleteByIdAndCartedBy(id,email);
			messageResponse.setMessage("Sucessfully Deleted Cart");
			messageResponse.setReturnStatus(1);
			messageResponse.setHttpStatus(HttpStatus.OK);
		}else {
			
			messageResponse.setMessage(" Fail To Fetch Data By Id and your Email Address");
			messageResponse.setReturnStatus(0);
			messageResponse.setHttpStatus(HttpStatus.NOT_FOUND);
			
		}
		
		return messageResponse;
		
	}

}
