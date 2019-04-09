package com.policy.bazaar.employee;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Employeepasswords 
{
	@Id
	@SequenceGenerator(
			name="emp_password_id_seq",
			sequenceName="emp_password_id_seq",
			allocationSize=1
			)
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="emp_password_id_seq"
			)
	private Integer id;
    private Integer empid;
    private UUID uuid;
    private Boolean status;
    private String password;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getEmpId() {
		return empid;
	}
	public void setEmpId(Integer empId) {
		this.empid = empId;
	}
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "EmployeePasswords [id=" + id + ", empId=" + empid + ", uuid=" + uuid + ", status=" + status
				+ ", password=" + password + "]";
	}
    
    
}
