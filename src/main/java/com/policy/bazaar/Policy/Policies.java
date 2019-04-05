package com.policy.bazaar.Policy;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Policies {
        
        @Id
        @SequenceGenerator(
				name="policy_id_seq",
				sequenceName="policy_id_seq",
				allocationSize=1
				)
		@GeneratedValue(
				strategy=GenerationType.AUTO,
				generator="policy_id_seq"
				)
	    private Integer pid;
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
	    private Date createdon;
	    
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
		public Date getCreatedon() {
			return createdon;
		}
		public void setCreatedon(Date createdon) {
			this.createdon = createdon;
		}
		@Override
		public String toString() {
			return "Policies [pid=" + pid + ", policyname=" + policyname + ", description=" + description + ", amount="
					+ amount + ", createdon=" + createdon + "]";
		}
	    
	    
	   
}
