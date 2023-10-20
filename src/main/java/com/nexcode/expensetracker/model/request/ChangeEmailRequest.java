package com.nexcode.expensetracker.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeEmailRequest {
	
	@NotNull
	@NotBlank
	private String newEmail;
	
	@NotNull
	@NotBlank
	private String password;
}
