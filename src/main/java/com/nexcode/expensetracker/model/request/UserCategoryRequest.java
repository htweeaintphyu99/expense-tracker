package com.nexcode.expensetracker.model.request;

import javax.validation.constraints.NotBlank;

import com.nexcode.expensetracker.model.entity.Type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCategoryRequest {
	
	@NotBlank
	private String userCategoryName;
	
	@NotBlank
	private String iconName;
	
	private Type type;
}
