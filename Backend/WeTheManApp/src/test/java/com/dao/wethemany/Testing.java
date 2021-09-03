package com.dao.wethemany;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dao.wethemany.models.Carts;
import com.dao.wethemany.response.MessageResponse;
import com.dao.wethemany.services.Cart_Service;
import com.dao.wethemany.services.Others_Services;
import com.dao.wethemany.services.Product_Services;
import com.dao.wethemany.services.PurchasingProductServices;
import com.dao.wethemany.services.UserDetailsServiceImpl;

@SpringBootTest

public class Testing {
	
	@Autowired
	private Others_Services others_Services;
	
	@Autowired
	private Cart_Service cart_Service;
	
	@Autowired
	private Product_Services product_Services;
	
	@Autowired
	private PurchasingProductServices purchasingProductServices;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpll;
	
//	//This Test WIll calculate and check whether the c02 emission generated value is correct or not
//	  @Test
//	   public void testCalculatec02Emission() {	
//	      assertEquals(0.13, others_Services.calculateC02Emission(5));     
//	   }
//	  
//	  // It Will Test whether the getAllProduct Work or Not
//	  @Test
//	  public void testGetAllProduct() {
//		  
//		  assertEquals(1,product_Services.getAllProductInfo().getReturnStatus());
//
//		  
//	  }
//	  
//	  // It Will Test whether the getAllProductById Work or Not
//	  @Test
//	  public void testGetAllProductById() {
//		  
//		  assertEquals(1,product_Services.getAllProductById("610041f2777c7f0c4014f1cc").getReturnStatus());
//		  
//	  }
//	  
//	  // It Will Test whether the getAllPurchasedProduct Work or Not
//	  @Test
//	  public void testAllPurchasedProduct() {
//		  
//		  assertEquals(1,purchasingProductServices.getAllPurchaseHistory("aaaaaaa@gmail.com").getReturnStatus());
//		  
//	  }
//	  
//	  // It Will Test whether the testAlldeletePurchasedProductById Work or Not
//	  @Test
//	  public void testAllDeletePurchasedProductById() {
//		  
//		  assertEquals(1, purchasingProductServices.deletePurchaseHistoryById("610b77213452f74594bdc067").getReturnStatus());
//		  
//	  }
//	  
//	  // It will test whether the addtocart WOrk Or Not
//	  @Test
//	  public void testAddToCart() {
//		  
//		  Carts cart=new Carts();
//		  cart.setCartedBy("aaaaaaa@gmail.com");
//		  cart.setProductid("61062af9936ab84f25351694");
//		  cart.setQuantity(2);		  
//		  MessageResponse messageResponse=cart_Service.addToCartService(cart, "aaaaaaa@gmail.com");	  
//		  assertEquals(1, messageResponse.getReturnStatus());
//		  
//	  }
//	  
//	  // Get all Purchased Product By Id
	  @Test
	  public void testthePurchaseProductById() {
		 
		  assertNotNull(purchasingProductServices.getPurchasedById("6115e99a02b92b619b2e00a1"));
	  }
	  
	  // Test all the Users whether it exist or not
	  @Test
	  public void testAllUsers() {		  
		  assertNotNull(userDetailsServiceImpll.getAllUsers());		  
	  }
	  
	  // Get all Purchased Product By Id	  
	  @Test
	  public void testtheUserById() {
		  
		  assertNotNull(userDetailsServiceImpll.getUserByEmail("ruppu@gmail.com"));		  
	  }
	  
	  
	  @Test
	  public void testTheCartById() {
		  
		  assertNotNull(cart_Service.getAllCartsInfoById("61155f28b0b8cc115800c87c"));
	  }

}
