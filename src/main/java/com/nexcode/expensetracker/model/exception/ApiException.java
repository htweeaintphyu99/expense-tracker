package com.nexcode.expensetracker.model.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiException {
	
	private String timestamp;
	private HttpStatus error;
	private int status;
	private String message;
	private String controllerName;
	private String serviceName;
	
}
