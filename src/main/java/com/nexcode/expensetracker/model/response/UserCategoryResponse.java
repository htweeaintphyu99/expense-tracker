package com.nexcode.expensetracker.model.response;

import com.nexcode.expensetracker.model.entity.Type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCategoryResponse {
	
	private Long id;
	private String name;
	private String iconName;
	private String iconBgColor;
	private Type type;
}
