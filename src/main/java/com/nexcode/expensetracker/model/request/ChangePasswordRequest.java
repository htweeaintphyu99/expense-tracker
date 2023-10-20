package com.nexcode.expensetracker.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {
	
	@NotNull
	@NotBlank
	private String oldPassword;
	
	@NotNull
	@NotBlank
	private String newPassword;
}
