package com.policy.bazaar.employee.response;

public class AllCountResponse {

	private Long totalPolicyCount;
	private Long totalCustomerCount;
	private Long totalPurchases;
	private Long totalApprovedPolicies;
	private Long totalRejectedPolicies;
	private Long totalEmployeesCount;
	private Long totalClaimCount;

	public Long getTotalEmployeesCount() {
		return totalEmployeesCount;
	}

	public void setTotalEmployeesCount(Long totalEmployeesCount) {
		this.totalEmployeesCount = totalEmployeesCount;
	}

	public Long getTotalClaimCount() {
		return totalClaimCount;
	}

	public void setTotalClaimCount(Long totalClaimCount) {
		this.totalClaimCount = totalClaimCount;
	}

	public Long getTotalPolicyCount() {
		return totalPolicyCount;
	}

	public void setTotalPolicyCount(Long totalPolicyCount) {
		this.totalPolicyCount = totalPolicyCount;
	}

	public Long getTotalCustomerCount() {
		return totalCustomerCount;
	}

	public void setTotalCustomerCount(Long totalCustomerCount) {
		this.totalCustomerCount = totalCustomerCount;
	}

	public Long getTotalPurchases() {
		return totalPurchases;
	}

	public void setTotalPurchases(Long totalPurchases) {
		this.totalPurchases = totalPurchases;
	}

	public Long getTotalApprovedPolicies() {
		return totalApprovedPolicies;
	}

	public void setTotalApprovedPolicies(Long totalApprovedPolicies) {
		this.totalApprovedPolicies = totalApprovedPolicies;
	}

	public Long getTotalRejectedPolicies() {
		return totalRejectedPolicies;
	}

	public void setTotalRejectedPolicies(Long totalRejectedPolicies) {
		this.totalRejectedPolicies = totalRejectedPolicies;
	}

}
