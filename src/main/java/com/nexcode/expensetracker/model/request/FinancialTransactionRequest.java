package com.nexcode.expensetracker.model.request;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.nexcode.expensetracker.model.entity.Type;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FinancialTransactionRequest {
	
	@NotNull 
	private Long userCategoryId;
	
	@NotNull
	private LocalDate createdDate;
	
	@NotNull
	private int amount;
	
	private String description;
	
	@NotNull
	private Type type;
}
