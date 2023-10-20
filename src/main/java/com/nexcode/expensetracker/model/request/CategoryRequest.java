package com.nexcode.expensetracker.model.request;

import com.nexcode.expensetracker.model.entity.Type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {
	
	private String categoryName;
	private String iconName;
	private String iconBgColor;
	private Type type;
}
