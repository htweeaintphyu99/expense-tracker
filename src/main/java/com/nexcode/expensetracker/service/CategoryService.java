package com.nexcode.expensetracker.service;

import java.util.List;

import com.nexcode.expensetracker.model.dto.CategoryDto;

public interface CategoryService {
	
	CategoryDto createCategory(CategoryDto categoryDto);

	CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);
	
	List<CategoryDto> getAllCategories();

	void deleteCategoryById(Long categoryId);
}
