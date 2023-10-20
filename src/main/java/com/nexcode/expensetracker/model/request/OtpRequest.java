package com.nexcode.expensetracker.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpRequest {
	
	private String otp;
	
	@NotNull
	@NotBlank
	private String email;
	
	private String newEmail;
}
