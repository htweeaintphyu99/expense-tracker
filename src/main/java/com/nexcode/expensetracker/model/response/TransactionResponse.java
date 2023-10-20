package com.nexcode.expensetracker.model.response;

import java.util.Date;

import com.nexcode.expensetracker.model.entity.Type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponse {

	private Long id;
	private int amount;
	private Date createdDate;
	private String description;
	private Type type;
}
