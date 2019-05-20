package com.policy.bazaar.claims.request;

public class AddClaimRequest {

	private Integer cid;
	private Integer pid;
	private Integer amount;
	private Integer status;

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
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

	@Override
	public String toString() {
		return "AddClaimRequest [cid=" + cid + ", pid=" + pid + ", amount=" + amount + ", status="
				+ status + "]";
	}

}
