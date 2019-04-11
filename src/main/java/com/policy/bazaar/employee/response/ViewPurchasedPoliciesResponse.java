package com.policy.bazaar.employee.response;

public class ViewPurchasedPoliciesResponse {

	private String customerFullName;
	private String customerLastName;
	private String policyName;
	private Integer amount;
	private Integer purchasedid;

	public String getCustomerFullName() {
		return customerFullName;
	}

	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getPurchasedid() {
		return purchasedid;
	}

	public void setPurchasedid(Integer purchasedid) {
		this.purchasedid = purchasedid;
	}

}
