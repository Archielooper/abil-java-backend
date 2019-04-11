package com.policy.bazaar.customers.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CustomerSignUpRequest {
	
	private String firstname;
	private String lastname;
	private String email;
	private String mobile;
	
	
	@NotNull(message="Password must not be null")
	@Pattern(regexp="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})", message= "{validation.password.pattern}")
	private String password;
	
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
