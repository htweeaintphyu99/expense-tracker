package com.nexcode.expensetracker.model.request;

import javax.validation.constraints.NotBlank;

import com.nexcode.expensetracker.model.entity.Type;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FinancialTransactionRequest {
	
	@NotBlank 
	private Long userCategoryId;
	
	@NotBlank
	private String createdDate;
	
	@NotBlank
	private int amount;
	
	private String description;
	
	@NotBlank
	private Type type;
}
