package com.nexcode.expensetracker.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.nexcode.expensetracker.model.entity.Type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCategoryRequest {
	
	@NotNull
	@NotBlank
	private String userCategoryName;
	
	@NotNull
	@NotBlank
	private String iconName;
	
	private Type type;
}
