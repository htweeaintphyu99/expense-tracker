package com.nexcode.expensetracker.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class ChangeNameRequest {

	@NotNull
	@NotBlank
	private String username;
}
