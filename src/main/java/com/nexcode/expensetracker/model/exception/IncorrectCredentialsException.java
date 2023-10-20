package com.nexcode.expensetracker.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class IncorrectCredentialsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public IncorrectCredentialsException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public IncorrectCredentialsException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
}