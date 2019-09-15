package com.revature.exception;

/*
 *  This exception should be triggered if user tries to withdraw more than they have 
 *  in their accounts.
 */
public class OverdraftException extends RuntimeException {
	public OverdraftException() {
		super("Insufficient Funds");
	}
	
	public OverdraftException(String message) {
		super(message);
	}
}
