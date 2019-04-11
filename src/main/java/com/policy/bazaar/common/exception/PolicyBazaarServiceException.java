package com.policy.bazaar.common.exception;

public class PolicyBazaarServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3922669768927250867L;

	public PolicyBazaarServiceException() {
		super();

	}

	public PolicyBazaarServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public PolicyBazaarServiceException(String message, Throwable cause) {
		super(message, cause);

	}

	public PolicyBazaarServiceException(String message) {
		super(message);

	}

	public PolicyBazaarServiceException(Throwable cause) {
		super(cause);

	}

}
