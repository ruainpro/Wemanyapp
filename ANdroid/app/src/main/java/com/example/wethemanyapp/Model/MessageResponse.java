package com.example.wethemanyapp.Model;

import java.util.ArrayList;
import java.util.List;


public class MessageResponse {

	private String message;
	private String responseType;
//	private HttpStatus httpStatus;
	private int returnStatus;

	public void setReturnValue(Product returnValue) {
		this.returnValue = returnValue;
	}

	private Product returnValue;
	
	
	private ArrayList<Product> returnValueList;
//	private ArrayList<?> returnValueList;

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

//	public HttpStatus getHttpStatus() {
//		return httpStatus;
//	}
//
//	public void setHttpStatus(HttpStatus httpStatus) {
//		this.httpStatus = httpStatus;
//	}

	public int getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(int returnStatus) {
		this.returnStatus = returnStatus;
	}

	public Product getReturnValue() {
		return returnValue;
	}

	public MessageResponse() {
	}

	@Override
	public String toString() {
		return "MessageResponse [message=" + message + ", responseType=" + responseType
				+ ", returnStatus=" + returnStatus + ", returnValue=" + returnValue + ", returnValueList="
				+ returnValueList + "]";
	}



	public ArrayList<Product> getReturnValueList() {
		return returnValueList;
	}

	public void setReturnValueList(ArrayList<Product> returnValueList) {
		this.returnValueList = returnValueList;
	}
	
	
	

	
}
