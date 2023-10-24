package com.nexcode.expensetracker.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class ChangeNameRequest {

	@NotBlank
	private String username;
}
