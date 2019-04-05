package com.policy.bazaar.globalresponse;

public class Response {

	
	  private Integer userType;
	  private Boolean Auth = true;
	
	  
	  public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public Boolean getAuth() {
		return Auth;
	}
	public void setAuth(Boolean auth) {
		Auth = auth;
	} 

	  
}
