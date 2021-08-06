package com.dao.wethemany.models;

public class PaymentInfo {

	private String paymentMedium;
	
	private double payment_Amount;
	
	private String paymentInfo;
	
	private String paymentreturnsid;
	
	private String paymentreturnRecipt;
	
	
	

	public String getPaymentreturnsid() {
		return paymentreturnsid;
	}

	public void setPaymentreturnsid(String paymentreturnsid) {
		this.paymentreturnsid = paymentreturnsid;
	}

	public String getPaymentreturnRecipt() {
		return paymentreturnRecipt;
	}

	public void setPaymentreturnRecipt(String paymentreturnRecipt) {
		this.paymentreturnRecipt = paymentreturnRecipt;
	}

	@Override
	public String toString() {
		return "PaymentInfo [paymentMedium=" + paymentMedium + ", payment_Amount=" + payment_Amount + ", paymentInfo="
				+ paymentInfo + ", paymentreturnsid=" + paymentreturnsid + ", paymentreturnRecipt="
				+ paymentreturnRecipt + "]";
	}

	public String getPaymentMedium() {
		return paymentMedium;
	}

	public void setPaymentMedium(String paymentMedium) {
		this.paymentMedium = paymentMedium;
	}

	public double getPayment_Amount() {
		return payment_Amount;
	}

	public void setPayment_Amount(double payment_Amount) {
		this.payment_Amount = payment_Amount;
	}

	public String getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
	}
	
	
}
