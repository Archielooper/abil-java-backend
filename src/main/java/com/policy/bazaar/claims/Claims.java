package com.policy.bazaar.claims;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Claims {
	
	
	@Id
	@SequenceGenerator(
			name="claims_id_seq",
			sequenceName="claims_id_seq",
			allocationSize=1
			)
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="claims_id_seq"
			)
	private Integer claimid;
	private Integer cid;
	private Integer pid;
	private Integer empid;
	
	private Integer status;
	private Integer amount;
	private Date startdate;
	private Date lastupdatedon;
	public Integer getClaimid() {
		return claimid;
	}
	public void setClaimid(Integer claimid) {
		this.claimid = claimid;
	}
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getLastupdatedon() {
		return lastupdatedon;
	}
	public void setLastupdatedon(Date lastupdatedon) {
		this.lastupdatedon = lastupdatedon;
	}
	@Override
	public String toString() {
		return "Claims [claimid=" + claimid + ", cid=" + cid + ", pid=" + pid + ", empid=" + empid + ", status="
				+ status + ", amount=" + amount + ", startdate=" + startdate + ", lastupdatedon=" + lastupdatedon + "]";
	}
	

}
