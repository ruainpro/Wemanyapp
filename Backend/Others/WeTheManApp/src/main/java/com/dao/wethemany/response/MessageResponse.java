package com.dao.wethemany.response;

import java.util.List;

import org.springframework.http.HttpStatus;

public class MessageResponse {

	private String message;
	private String responseType;
	private HttpStatus httpStatus;
	private int returnStatus;

	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}

	private Object returnValue;
	
	
	private List<?> returnValueList;

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

	public MessageResponse() {
	}

	@Override
	public String toString() {
		return "MessageResponse [message=" + message + ", responseType=" + responseType + ", httpStatus=" + httpStatus
				+ ", returnStatus=" + returnStatus + ", returnValue=" + returnValue + ", returnValueList="
				+ returnValueList + "]";
	}



	public List<?> getReturnValueList() {
		return returnValueList;
	}

	public void setReturnValueList(List<?> returnValueList) {
		this.returnValueList = returnValueList;
	}
	
	
	

	
}
