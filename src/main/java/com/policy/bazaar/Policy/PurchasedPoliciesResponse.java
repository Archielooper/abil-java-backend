package com.policy.bazaar.Policy;

import java.util.Date;

public class PurchasedPoliciesResponse {
	
	private String customerfirstname;
	private String customerlastname;
	private Date startdate;
	private Date enddate;
	private String policyname;
    private Integer amount;
    
	public String getCustomerfirstname() {
		return customerfirstname;
	}
	public void setCustomerfirstname(String customerfirstname) {
		this.customerfirstname = customerfirstname;
	}
	public String getCustomerlastname() {
		return customerlastname;
	}
	public void setCustomerlastname(String customerlastname) {
		this.customerlastname = customerlastname;
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
	public String getPolicyname() {
		return policyname;
	}
	public void setPolicyname(String policyname) {
		this.policyname = policyname;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
    
    
}
