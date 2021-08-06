package com.dao.wethemany.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.dao.wethemany.models.Carts;
import com.dao.wethemany.models.Product;
import com.dao.wethemany.models.Purchasing;

public class MessageResponse {

	private String message;
	private String responseType;
	private HttpStatus httpStatus;
	private int returnStatus;

	private Object returnValue;	
	
	private List<?> returnValueList;

	private List<Product> productList;

	private List<Carts> cartsValueList;
	
	private List<Purchasing> purchasedValueList;

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

	public Object getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
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

	public List<Carts> getCartsValueList() {
		return cartsValueList;
	}

	public void setCartsValueList(List<Carts> cartsValueList) {
		this.cartsValueList = cartsValueList;
	}

	public List<Purchasing> getPurchasedValueList() {
		return purchasedValueList;
	}

	@Override
	public String toString() {
		return "MessageResponse [message=" + message + ", responseType=" + responseType + ", httpStatus=" + httpStatus
				+ ", returnStatus=" + returnStatus + ", returnValue=" + returnValue + ", returnValueList="
				+ returnValueList + ", productList=" + productList + ", cartsValueList=" + cartsValueList
				+ ", purchasedValueList=" + purchasedValueList + "]";
	}

	public void setPurchasedValueList(List<Purchasing> purchasedValueList) {
		this.purchasedValueList = purchasedValueList;
	}
	
	


	

	
	

	
}
