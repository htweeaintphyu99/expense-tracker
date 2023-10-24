package com.nexcode.expensetracker.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.nexcode.expensetracker.model.entity.Type;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FinancialTransactionRequest {
	
	@NotNull
	private Long userCategoryId;
	
	@NotBlank
	private String createdDate;
	
	@NotNull
	@Positive
	private int amount;
	
	private String description;
	
	@NotNull
	private Type type;
}
