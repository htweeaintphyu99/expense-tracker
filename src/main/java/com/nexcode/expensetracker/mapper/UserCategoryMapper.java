package com.nexcode.expensetracker.mapper;

import java.util.List;

import com.nexcode.expensetracker.model.dto.UserCategoryDto;
import com.nexcode.expensetracker.model.entity.UserCategory;
import com.nexcode.expensetracker.model.response.UserCategoryResponse;

public interface UserCategoryMapper {
	
	UserCategoryDto mapToDto(UserCategory userCategory);
	List<UserCategoryDto> mapToDto(List<UserCategory> userCategories);
	List<UserCategoryResponse> mapToRepsonse(List<UserCategoryDto> userCategoryDtos);
	UserCategoryResponse mapToResponse(UserCategoryDto userCategoryDto);
}
