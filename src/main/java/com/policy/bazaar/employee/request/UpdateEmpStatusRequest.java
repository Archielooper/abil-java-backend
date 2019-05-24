package com.policy.bazaar.employee.request;

public class UpdateEmpStatusRequest {

	private Integer empId;
	private Integer Status;

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public Integer getStatus() {
		return Status;
	}

	public void setStatus(Integer status) {
		Status = status;
	}

}
