package com.example.wethemanyapp.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Purchasing {
	
	private String id;
	
	private ArrayList<PurchasedProduct> purchasedproduct;
	
	private Date purchasedDate;
	
	private PaymentInfo paymentInfo;	
		
	private String userId;	
	
	private boolean status;

	private String userEmail;

	@Override
	public String toString() {
		return "Purchasing{" +
				"id='" + id + '\'' +
				", purchasedproduct=" + purchasedproduct +
				", purchasedDate=" + purchasedDate +
				", paymentInfo=" + paymentInfo +
				", userId='" + userId + '\'' +
				", status=" + status +
				", userEmail='" + userEmail + '\'' +
				'}';
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<PurchasedProduct> getPurchasedproduct() {
		return purchasedproduct;
	}

	public void setPurchasedproduct(ArrayList<PurchasedProduct> purchasedproduct) {
		this.purchasedproduct = purchasedproduct;
	}

	public Date getPurchasedDate() {
		return purchasedDate;
	}

	public void setPurchasedDate(Date purchasedDate) {
		this.purchasedDate = purchasedDate;
	}

	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
}
