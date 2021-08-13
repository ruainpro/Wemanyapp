package com.example.wethemanyapp.Model;

import java.util.List;

public class JwtResponse {
	private String accessToken;
	private String tokenType ;
	private String id;
	private String email;
	private List<String> roles;
	private String fullName;
	private String currentAddress;

	@Override
	public String toString() {
		return "JwtResponse{" +
				"accessToken='" + accessToken + '\'' +
				", tokenType='" + tokenType + '\'' +
				", id='" + id + '\'' +
				", email='" + email + '\'' +
				", roles=" + roles +
				", fullName='" + fullName + '\'' +
				", currentAddres='" + currentAddress + '\'' +
				'}';
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCurrentAddres() {
		return currentAddress;
	}

	public void setCurrentAddres(String currentAddres) {
		this.currentAddress = currentAddres;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
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

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<String> getRoles() {
		return roles;
	}
}
