package com.policy.bazaar.claims;

public class AddClaimRequest {

	private Integer cid;
	private Integer pid;
	private Integer empid;
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

	public Integer getEmpid() {
		return empid;
	}

	public void setEmpid(Integer empid) {
		this.empid = empid;
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
		return "AddClaimRequest [cid=" + cid + ", pid=" + pid + ", empid=" + empid + ", amount=" + amount + ", status="
				+ status + "]";
	}

}
