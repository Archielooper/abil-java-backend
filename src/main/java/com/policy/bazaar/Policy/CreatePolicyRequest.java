package com.policy.bazaar.Policy;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreatePolicyRequest {
    
	@NotNull
   	@Size(min = 2, max = 30, message="{validation.policyname.size}")
	private String policyname;
	
    @NotNull
 	@Size(min = 2, max = 30, message="{validation.description.size}")
	private String description;
    
    
	@NotNull(message = "{validation.amount.notnull}")
	@Min(value = 10000, message = "{validation.amount.min}")
	@Max(value = 10000000, message = "{validation.amount.max}")
	private Integer amount;
	
	public String getPolicyname() {
		return policyname;
	}
	public void setPolicyname(String policyname) {
		this.policyname = policyname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
}
