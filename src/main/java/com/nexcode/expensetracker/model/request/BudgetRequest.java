package com.nexcode.expensetracker.model.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BudgetRequest {

	@NotNull
	private int budget;
}
