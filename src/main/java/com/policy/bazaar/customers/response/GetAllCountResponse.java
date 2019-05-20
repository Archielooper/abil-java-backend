package com.policy.bazaar.customers.response;

public class GetAllCountResponse {

	private Long totalClaimsCount;
	private Long approvedClaimsCount;
	private Long totalPoliciesCount;
	private Long approvedPolicesCount;

	public Long getTotalClaimsCount() {
		return totalClaimsCount;
	}

	public void setTotalClaimsCount(Long totalClaimsCount) {
		this.totalClaimsCount = totalClaimsCount;
	}

	public Long getApprovedClaimsCount() {
		return approvedClaimsCount;
	}

	public void setApprovedClaimsCount(Long approvedClaimsCount) {
		this.approvedClaimsCount = approvedClaimsCount;
	}

	public Long getTotalPoliciesCount() {
		return totalPoliciesCount;
	}

	public void setTotalPoliciesCount(Long totalPoliciesCount) {
		this.totalPoliciesCount = totalPoliciesCount;
	}

	public Long getApprovedPolicesCount() {
		return approvedPolicesCount;
	}

	public void setApprovedPolicesCount(Long approvedPolicesCount) {
		this.approvedPolicesCount = approvedPolicesCount;
	}

}
