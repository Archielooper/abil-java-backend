package com.policy.bazaar.employee.model;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity

public class Employees {

	@Id
	@SequenceGenerator(name = "employee_id_seq", sequenceName = "employee_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "employee_id_seq")
	private Integer empid;

	@NotNull
	@Size(min = 2, max = 30, message = "{validation.fullname.size}")
	//@Pattern(regexp = "[-A-Za-z]*", message = "{validation.fullname.pattern}")
	private String fullname;

	@NotNull(message = "{validation.email.notnull}")
	@Pattern(regexp = "^(.+)@(.+)$", message = "{validation.email.pattern}")
	private String email;

	@NotNull(message = "{validation.mobile.notnull}")
	@Pattern(regexp = "(0/91)?[7-9][0-9]{9}", message = "{validation.mobile.pattern}")
	private String mobile;

	@NotNull(message = "{validation.usertype.notnull}")
	@Min(value = 2, message = "{validation.usertype.pattern}")
	@Max(value = 3, message = "{validation.usertype.pattern}")
	private Byte usertype;
	private String password;
	private Date createdon;
	private Date lastupdatedon;

	public Integer getEmpid() {
		return empid;
	}

	public void setEmpid(Integer empid) {
		this.empid = empid;
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

	public Byte getUsertype() {
		return usertype;
	}

	public void setUsertype(Byte usertype) {
		this.usertype = usertype;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public Date getLastupdatedon() {
		return lastupdatedon;
	}

	public void setLastupdatedon(Date lastupdatedon) {
		this.lastupdatedon = lastupdatedon;
	}

	@Override
	public String toString() {
		return "Employees [empid=" + empid + ", fullname=" + fullname + ", email=" + email + ", mobile=" + mobile
				+ ", usertype=" + usertype + ", password=" + password + ", createdon=" + createdon + ", lastupdatedon="
				+ lastupdatedon + "]";
	}

}
