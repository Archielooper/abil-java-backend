package com.policy.bazaar.customers;

import java.sql.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CreateCustomerRequest {
	
	@NotNull
	@Size(min = 2, max = 30, message="{validation.fullname.size}")
	@Pattern(regexp = "[-A-Za-z]*", message="{validation.fullname.pattern}")
	private String fullname;
	
	@NotNull(message="{validation.email.notnull}")
	@Pattern(regexp="^(.+)@(.+)$", message="{validation.email.pattern}")
	private String email;
	
	@NotNull(message="{validation.mobile.notnull}")
	@Pattern(regexp = "(0/91)?[7-9][0-9]{9}", message="{validation.mobile.pattern}")
	private String mobile;
	private Integer pid;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	private Date startdate;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	private Date enddate;

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
	
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	
	

}
