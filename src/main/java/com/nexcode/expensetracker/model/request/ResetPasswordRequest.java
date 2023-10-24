package com.nexcode.expensetracker.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class ResetPasswordRequest {

	@NotBlank
	private String email;
	
	@NotBlank
	private String password;
}
