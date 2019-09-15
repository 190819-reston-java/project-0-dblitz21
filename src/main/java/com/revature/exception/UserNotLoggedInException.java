package com.revature.exception;

/*
 *  This exception should be thrown if the services are accessed when a user is not logged in.
 *  In theory the program is structured in a way that this exception should never be triggered.
 */
public class UserNotLoggedInException extends RuntimeException {
	public UserNotLoggedInException() {
		super("A user must be logged in to access services");
	}
	
	public UserNotLoggedInException(String message) {
		super(message);
	}

}
