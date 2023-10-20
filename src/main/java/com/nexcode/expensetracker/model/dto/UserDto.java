package com.nexcode.expensetracker.model.dto;

import java.time.Instant;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	
	private Long id;
	private String username;
	private String email;
	private String password;
	private int budget;
	private int balance;
	private String otp;
	private Instant otpExpirationtime;
	private Set<RoleDto> roles;
}
