package com.nexcode.expensetracker.model.dto;

import java.time.LocalDate;

import com.nexcode.expensetracker.model.entity.Type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinancialTransactionDto {

	private Long id;
	private int amount;
	private LocalDate createdDate;
	private String description;
	private Type type;
	private UserCategoryDto userCategoryDto;
}

