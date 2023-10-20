package com.nexcode.expensetracker.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
	
	private Long id;
	private String username;
	private String email;
	private int budget;
	private int balance;
}
