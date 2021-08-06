package com.dao.wethemany.models;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Carts {
	
	@Id
	private String id;
	
	private String productid;
	
	private Date cartedDate;
	
	private Product product;
	
	private String cartedBy;

	private int quantity;
	
	private String cartsStatus;
	
	

	public String getCartsStatus() {
		return cartsStatus;
	}

	public void setCartsStatus(String cartsStatus) {
		this.cartsStatus = cartsStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public Date getCartedDate() {
		return cartedDate;
	}

	public void setCartedDate(Date cartedDate) {
		this.cartedDate = cartedDate;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getCartedBy() {
		return cartedBy;
	}

	public void setCartedBy(String cartedBy) {
		this.cartedBy = cartedBy;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Carts [id=" + id + ", productid=" + productid + ", cartedDate=" + cartedDate + ", product=" + product
				+ ", cartedBy=" + cartedBy + ", quantity=" + quantity + ", cartsStatus=" + cartsStatus + "]";
	}
	
	
	

}
