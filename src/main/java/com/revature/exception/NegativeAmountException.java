package com.revature.exception;

/*
 * This exception should be triggered when the user enters a negative amount
 */
public class NegativeAmountException extends RuntimeException {
	public NegativeAmountException() {
		super("The amount entered must be a positive value");
	}
	
	public NegativeAmountException(String message) {
		super(message);
	}
}
