package com.nexcode.expensetracker.service;

import java.util.List;

import com.nexcode.expensetracker.model.dto.UserCategoryDto;
import com.nexcode.expensetracker.model.request.UserCategoryRequest;

public interface UserCategoryService {
	
	UserCategoryDto createUserCategory(Long userId, UserCategoryRequest userCategoryRequest);

	UserCategoryDto updateUserCategory(Long id, UserCategoryRequest userCategoryRequest, Long userId);
	
	List<UserCategoryDto> getAllUserCategories();

	boolean deleteUserCategoryById(Long userCategoryId);

	List<UserCategoryDto> getUserCategoriesByUserId(Long userId);
}
