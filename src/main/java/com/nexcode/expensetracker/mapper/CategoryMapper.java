package com.nexcode.expensetracker.mapper;

import java.util.List;

import com.nexcode.expensetracker.model.dto.CategoryDto;
import com.nexcode.expensetracker.model.entity.Category;
import com.nexcode.expensetracker.model.request.CategoryRequest;
import com.nexcode.expensetracker.model.response.CategoryResponse;

public interface CategoryMapper {
	
	CategoryDto mapToDto(CategoryRequest categoryRequest);
	CategoryDto mapToDto(Category category);
	List<CategoryDto> mapToDto(List<Category> categories);
	List<CategoryResponse> mapToRepsonse(List<CategoryDto> categoryDtos);
}
