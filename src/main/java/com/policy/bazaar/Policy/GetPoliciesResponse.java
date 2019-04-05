package com.policy.bazaar.Policy;

public class GetPoliciesResponse {
	
	    private String policyname;
	    private String description;
	    private Integer amount;
	    private Integer pid;
	    
		public Integer getPid() {
			return pid;
		}
		public void setPid(Integer pid) {
			this.pid = pid;
		}
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
		@Override
		public String toString() {
			return "GetPoliciesResponse [policyname=" + policyname + ", description=" + description + ", amount="
					+ amount + "]";
		}
	    
	    

}
