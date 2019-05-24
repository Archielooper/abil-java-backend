package com.policy.bazaar.policy.response;

public class GetPoliciesResponse {

	private String policyname;
	private String description;
	private Integer amount;
	private Integer pid;
	private Integer tenure;

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getPolicyname() {
		return policyname;
	}

	public void setPolicyname(String policyname) {
		this.policyname = policyname;
	}

	public Integer getTenure() {
		return tenure;
	}

	public void setTenure(Integer tenure) {
		this.tenure = tenure;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "GetPoliciesResponse [policyname=" + policyname + ", description=" + description + ", amount=" + amount
				+ "]";
	}

}
