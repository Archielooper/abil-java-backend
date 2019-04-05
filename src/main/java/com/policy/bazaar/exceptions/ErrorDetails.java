package com.policy.bazaar.exceptions;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ErrorDetails {

	private Date timestamp;
	private String message;
	private List<String> error;
	private HttpStatus status;
	
	public ErrorDetails(Date date, String message, List<String> errors, HttpStatus status) {
		super();
		this.timestamp = date;
		this.message = message;
		this.error = errors;
		this.status = status;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public List<String> getError() {
		return error;
	}
	
	public HttpStatus getStatus()
	{
		return status;
	}

	

}
