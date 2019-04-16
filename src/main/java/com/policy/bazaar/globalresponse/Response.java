package com.policy.bazaar.globalresponse;

public class Response {

	
	  private Byte userType;
	  private Boolean Auth = true;
	
	  
	  public Byte getUserType() {
		return userType;
	}
	public void setUserType(Byte userType) {
		this.userType = userType;
	}
	public Boolean getAuth() {
		return Auth;
	}
	public void setAuth(Boolean auth) {
		Auth = auth;
	} 

	  
}
