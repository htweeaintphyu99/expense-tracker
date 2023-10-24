package com.nexcode.expensetracker.model.request;

import javax.validation.constraints.NotBlank;

import com.nexcode.expensetracker.model.entity.Type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IconRequest {
	
	@NotBlank
	private String iconName;
	
	@NotBlank
	private String iconBgColor;

	private Type type;
}
