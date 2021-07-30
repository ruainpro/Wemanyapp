package com.dao.wethemany.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.dao.wethemany.models.Product;

public class MessageResponse {

	private String message;
	private String responseType;
	private HttpStatus httpStatus;
	private int returnStatus;

	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}

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

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public int getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(int returnStatus) {
		this.returnStatus = returnStatus;
	}

	public List<?> getReturnValueList() {
		return returnValueList;
	}

	public void setReturnValueList(List<?> returnValueList) {
		this.returnValueList = returnValueList;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public Object getReturnValue() {
		return returnValue;
	}

	private Object returnValue;
	
	
	private List<?> returnValueList;

	private List<Product> productList;

	@Override
	public String toString() {
		return "MessageResponse [message=" + message + ", responseType=" + responseType + ", httpStatus=" + httpStatus
				+ ", returnStatus=" + returnStatus + ", returnValue=" + returnValue + ", returnValueList="
				+ returnValueList + ", productList=" + productList + "]";
	}


	
}
