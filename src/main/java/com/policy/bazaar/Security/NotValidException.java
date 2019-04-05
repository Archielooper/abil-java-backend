package com.policy.bazaar.Security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class NotValidException extends RuntimeException
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotValidException(String exception)
	{
		super(exception);
	}
}
