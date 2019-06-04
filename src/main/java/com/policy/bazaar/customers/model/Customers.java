package com.policy.bazaar.customers.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Customers {

	@Id
	@SequenceGenerator(name = "customer_id_seq", sequenceName = "customer_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "customer_id_seq")
	private Integer cid;

	@NotNull
	@Size(min = 2, max = 30, message = "{validation.fullname.size}")
	@Pattern(regexp = "[-A-Za-z0-9]*", message = "{validation.fullname.pattern}")
	private String firstname;

	@NotNull
	@Size(min = 2, max = 30, message = "{validation.fullname.size}")
	@Pattern(regexp = "[-A-Za-z0-9]*", message = "{validation.fullname.pattern}")
	private String lastname;

	@NotNull(message = "{validation.email.notnull}")
	@Pattern(regexp = "^(.+)@(.+)$", message = "{validation.email.pattern}")
	private String email;

	@NotNull(message = "{validation.mobile.notnull}")
	@Pattern(regexp = "(0/91)?[6-9][0-9]{9}", message = "{validation.mobile.pattern}")
	private String mobile;

	
	private String password;

	private Integer status;
	private Date lastupdatedon;
	private Date createdon;
	private String address;
	private String imageurl;

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getLastupdatedon() {
		return lastupdatedon;
	}

	public void setLastupdatedon(Date lastupdatedon) {
		this.lastupdatedon = lastupdatedon;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	@Override
	public String toString() {
		return "Customers [cid=" + cid + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
				+ ", mobile=" + mobile + ", password=" + password + ", status=" + status + ", lastupdatedon="
				+ lastupdatedon + ", createdon=" + createdon + "]";
	}

}
