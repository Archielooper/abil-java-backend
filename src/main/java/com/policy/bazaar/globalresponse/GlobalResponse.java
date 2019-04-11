package com.policy.bazaar.globalresponse;

public class GlobalResponse {

	private Object data;
	private Boolean status;
	private String message;

	public GlobalResponse() {
		super();
		setStatus(Boolean.TRUE);
	}

	public GlobalResponse(Object data, Boolean status, String message) {
		super();
		this.data = data;
		this.status = status;
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
