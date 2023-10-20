package com.nexcode.expensetracker.model.dto;

import com.nexcode.expensetracker.model.entity.RoleName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto {
	
	private Long id;
	private RoleName name;
}
