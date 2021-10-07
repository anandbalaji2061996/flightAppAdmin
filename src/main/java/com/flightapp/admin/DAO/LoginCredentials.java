package com.flightapp.admin.DAO;

import javax.validation.constraints.NotEmpty;

public class LoginCredentials {
	
	@NotEmpty(message = "Username should not be empty")
	private String username;
	@NotEmpty(message = "Password should not be empty")
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
