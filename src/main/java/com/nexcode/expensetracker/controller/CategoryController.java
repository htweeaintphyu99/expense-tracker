package com.nexcode.expensetracker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexcode.expensetracker.mapper.CategoryMapper;
import com.nexcode.expensetracker.model.dto.CategoryDto;
import com.nexcode.expensetracker.model.exception.BadRequestException;
import com.nexcode.expensetracker.model.request.CategoryRequest;
import com.nexcode.expensetracker.model.response.ApiResponse;
import com.nexcode.expensetracker.model.response.CategoryResponse;
import com.nexcode.expensetracker.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	private final CategoryService categoryService;
	private final CategoryMapper categoryMapper;

	@PostMapping
	public ApiResponse createCategory(@RequestBody CategoryRequest categoryRequest) {

		CategoryDto createdcategory = categoryService.createCategory(categoryMapper.mapToDto(categoryRequest));
		if (createdcategory != null) {
			return new ApiResponse(true, "Category creation Successful");
		}
		throw new BadRequestException("An error occurred in creating category!");

	}

	@PutMapping("/{categoryId}")
	public ApiResponse updateCategory(@RequestBody CategoryRequest categoryRequest, @PathVariable Long categoryId) {

		CategoryDto updatedcategory = categoryService.updateCategory(categoryMapper.mapToDto(categoryRequest),
				categoryId);
		if (updatedcategory != null) {
			return new ApiResponse(true, "Category Update Successful");
		}
		throw new BadRequestException("An error occurred in updating category!");

	}

	@GetMapping
	public List<CategoryResponse> getAllCategorys() {

		List<CategoryDto> categoryDtos = categoryService.getAllCategories();

		return categoryMapper.mapToRepsonse(categoryDtos);

	}

	@DeleteMapping("/{categoryId}")
	public ApiResponse deleteCategoryById(@PathVariable Long categoryId) {

		categoryService.deleteCategoryById(categoryId);
		return new ApiResponse(true, "Category deletion successful");

	}
}
