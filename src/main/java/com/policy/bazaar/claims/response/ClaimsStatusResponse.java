package com.policy.bazaar.claims.response;

import java.util.Date;

public class ClaimsStatusResponse {

	private Integer claimid;
	private String policyname;
	private Date adddate;
	private Integer amount;
	private Integer status;

	public Integer getClaimid() {
		return claimid;
	}

	public void setClaimid(Integer claimid) {
		this.claimid = claimid;
	}

	public String getPolicyname() {
		return policyname;
	}

	public void setPolicyname(String policyname) {
		this.policyname = policyname;
	}

	public Date getAdddate() {
		return adddate;
	}

	public void setAdddate(Date adddate) {
		this.adddate = adddate;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
