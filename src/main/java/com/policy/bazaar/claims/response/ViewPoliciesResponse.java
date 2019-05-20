package com.policy.bazaar.claims.response;

public class ViewPoliciesResponse {

	private String CustomerFirstName;
	private String CustomerLastName;
	private String PolicyName;
	private Integer amount;
	private Integer claimid;
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCustomerFirstName() {
		return CustomerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		CustomerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return CustomerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		CustomerLastName = customerLastName;
	}

	public String getPolicyName() {
		return PolicyName;
	}

	public void setPolicyName(String policyName) {
		PolicyName = policyName;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getClaimid() {
		return claimid;
	}

	public void setClaimid(Integer claimid) {
		this.claimid = claimid;
	}

}
