package com.policy.bazaar.Policy;

import java.sql.Date;

public class PurchasedPoliciesRequest {
	
	private Integer pid;
	private Date startdate;
	private Date enddate;
	
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
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
	
	

}
