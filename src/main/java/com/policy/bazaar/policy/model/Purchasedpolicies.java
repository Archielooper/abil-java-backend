package com.policy.bazaar.policy.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Purchasedpolicies {

	@Id
	@SequenceGenerator(name = "purchased_policy_id_seq", sequenceName = "purchased_policy_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "purchased_policy_id_seq")
	private Integer purchasedid;
	private Integer cid;
	private Integer pid;
	private Integer empid;
	private Integer status;
	private Date startdate;
	private Date enddate;
	private Date createdon;

	public Integer getPurchasedid() {
		return purchasedid;
	}

	public void setPurchasedid(Integer purchasedid) {
		this.purchasedid = purchasedid;
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

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	@Override
	public String toString() {
		return "purchasedpolicies [purchasedid=" + purchasedid + ", cid=" + cid + ", pid=" + pid + ", empid=" + empid
				+ ", startdate=" + startdate + ", enddate=" + enddate + ", createdon=" + createdon + "]";
	}

}
