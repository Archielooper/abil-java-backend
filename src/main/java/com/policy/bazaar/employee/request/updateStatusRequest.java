package com.policy.bazaar.employee.request;

public class updateStatusRequest {

	private Integer purchasedid;
	private Integer status;

	public Integer getPurchasedid() {
		return purchasedid;
	}

	public void setPurchasedid(Integer purchasedid) {
		this.purchasedid = purchasedid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
