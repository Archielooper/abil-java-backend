package com.policy.bazaar.employee;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EmployeeCreateRequest {
    
	@NotNull
	@Size(min = 2, max = 30, message="{validation.fullname.size}")
	//@Pattern(regexp = "[-A-Za-z]*", message="{validation.fullname.pattern}")
	private String fullname;
	
	@NotNull(message="{validation.email.notnull}")
	@Pattern(regexp="^(.+)@(.+)$", message="{validation.email.pattern}")
	private String email;
	
	@NotNull(message="{validation.mobile.notnull}")
	@Pattern(regexp = "(0/91)?[7-9][0-9]{9}", message="{validation.mobile.pattern}")
	private String mobile;
	
	@NotNull(message="{validation.usertype.notnull}")
	@Min(value=2, message="{validation.usertype.pattern}")
	@Max(value=3, message="{validation.usertype.pattern}")
	private Integer usertype;
	
	public Integer getUsertype() {
		return usertype;
	}
	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
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
	
	
}
