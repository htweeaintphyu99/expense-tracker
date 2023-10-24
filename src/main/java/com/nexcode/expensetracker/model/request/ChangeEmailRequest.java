package com.nexcode.expensetracker.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeEmailRequest {
	
	@NotBlank
	private String newEmail;
	
	@NotBlank
	private String password;
}
