package com.dao.wethemany.response;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String id;
	private String email;
	private List<String> roles;	
	private String fullName;	
	private String currentAddress;

	public JwtResponse(String accessToken, String id,String email, List<String> roles,String fullName,  String currentAddress) {
		this.token = accessToken;
		this.id = id;
		this.email = email;
		this.roles = roles;
		this.fullName=fullName;
		this.currentAddress=currentAddress;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public List<String> getRoles() {
		return roles;
	}
}
