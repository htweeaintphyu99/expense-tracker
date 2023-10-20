package com.nexcode.expensetracker.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class JwtAuthenticationResponse {
	
	private final String token;
	private String expiredAt;
	
}
