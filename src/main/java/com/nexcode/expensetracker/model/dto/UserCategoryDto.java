package com.nexcode.expensetracker.model.dto;

import com.nexcode.expensetracker.model.entity.Type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCategoryDto {

	private Long userId;
	private Long userCategoryId;
	private String userCategoryName;
	private String iconName;
	private String iconBgColor;
	private Type type;
}
