package com.example.wethemanyapp.Model;

import java.util.List;


public class PurchasedProduct {
	
	private String productId;
	
	private double productQuantity;

	private String cartId;

	@Override
	public String toString() {
		return "PurchasedProduct{" +
				"productId='" + productId + '\'' +
				", productQuantity=" + productQuantity +
				", cartId='" + cartId + '\'' +
				", productresponse=" + productresponse +
				'}';
	}

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	Product productresponse;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public double getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(double productQuantity) {
		this.productQuantity = productQuantity;
	}

	public Product getProductresponse() {
		return productresponse;
	}

	public void setProductresponse(Product productresponse) {
		this.productresponse = productresponse;
	}


	

}
