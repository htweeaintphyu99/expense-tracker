package com.nexcode.expensetracker.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BudgetRequest {

	@NotNull
	private int budget;
}
