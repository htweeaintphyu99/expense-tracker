package com.nexcode.expensetracker.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.nexcode.expensetracker.model.dto.CategoryDto;
import com.nexcode.expensetracker.model.entity.Category;
import com.nexcode.expensetracker.model.request.CategoryRequest;
import com.nexcode.expensetracker.model.response.CategoryResponse;

@Component
public class CategoryMapperImpl implements CategoryMapper {
	@Override
	public CategoryDto mapToDto(CategoryRequest categoryRequest) {

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName(categoryRequest.getCategoryName());
		categoryDto.setIconName(categoryRequest.getIconName());
		categoryDto.setIconBgColor(categoryRequest.getIconBgColor());
		categoryDto.setType(categoryRequest.getType());

		return categoryDto;
	}

	@Override
	public CategoryDto mapToDto(Category category) {

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setId(category.getId());
		categoryDto.setName(category.getName());
		categoryDto.setIconName(category.getIconName());
		categoryDto.setIconBgColor(category.getIconBgColor());
		categoryDto.setType(category.getType());

		return categoryDto;
	}

	@Override
	public List<CategoryDto> mapToDto(List<Category> categories) {

		return categories.stream().map(category -> {
			CategoryDto categoryDto = new CategoryDto();
			categoryDto.setId(category.getId());
			categoryDto.setName(category.getName());
			categoryDto.setIconName(category.getIconName());
			categoryDto.setIconBgColor(category.getIconBgColor());
			categoryDto.setType(category.getType());

			return categoryDto;
		}).collect(Collectors.toList());

	}

	@Override
	public List<CategoryResponse> mapToRepsonse(List<CategoryDto> categoryDtos) {

		return categoryDtos.stream().map(categoryDto -> {
			CategoryResponse categoryResponse = new CategoryResponse();
			categoryResponse.setId(categoryDto.getId());
			categoryResponse.setIconName(categoryDto.getIconName());
			categoryResponse.setIconName(categoryDto.getIconName());
			categoryResponse.setIconBgColor(categoryDto.getIconBgColor());
			categoryResponse.setType(categoryDto.getType());

			return categoryResponse;
		}).collect(Collectors.toList());
	}

}
