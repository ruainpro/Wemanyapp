package com.example.wethemanyapp.Model;

import java.util.ArrayList;
import java.util.List;


public class MessageResponse {

	private String message;
	private String responseType;
//	private HttpStatus httpStatus;
	private int returnStatus;

	private Product returnValue;
	
	private ArrayList<Product> returnValueList;

	private ArrayList<Carts> cartsValueList;

	private ArrayList<Purchasing> purchasedValueList;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public int getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(int returnStatus) {
		this.returnStatus = returnStatus;
	}

	public Product getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(Product returnValue) {
		this.returnValue = returnValue;
	}

	public ArrayList<Product> getReturnValueList() {
		return returnValueList;
	}

	public void setReturnValueList(ArrayList<Product> returnValueList) {
		this.returnValueList = returnValueList;
	}

	public ArrayList<Carts> getCartsValueList() {
		return cartsValueList;
	}

	public void setCartsValueList(ArrayList<Carts> cartsValueList) {
		this.cartsValueList = cartsValueList;
	}

	public ArrayList<Purchasing> getPurchasedValueList() {
		return purchasedValueList;
	}

	public void setPurchasedValueList(ArrayList<Purchasing> purchasedValueList) {
		this.purchasedValueList = purchasedValueList;
	}

	@Override
	public String toString() {
		return "MessageResponse{" +
				"message='" + message + '\'' +
				", responseType='" + responseType + '\'' +
				", returnStatus=" + returnStatus +
				", returnValue=" + returnValue +
				", returnValueList=" + returnValueList +
				", cartsValueList=" + cartsValueList +
				", purchasedValueList=" + purchasedValueList +
				'}';
	}
}
