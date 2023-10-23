package com.nexcode.expensetracker.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexcode.expensetracker.mapper.CategoryMapper;
import com.nexcode.expensetracker.model.dto.CategoryDto;
import com.nexcode.expensetracker.model.entity.Category;
import com.nexcode.expensetracker.model.exception.NotFoundException;
import com.nexcode.expensetracker.repository.CategoryRepository;
import com.nexcode.expensetracker.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {

		Category category = new Category();
		category.setName(categoryDto.getName());
		category.setIconName(categoryDto.getIconName());
		category.setIconBgColor(categoryDto.getIconBgColor());
		category.setType(categoryDto.getType());

		Category createdCategory = categoryRepository.save(category);

		CategoryDto createdCategoryDto = categoryMapper.mapToDto(createdCategory);
		return createdCategoryDto;
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {

		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new NotFoundException("Category id " + categoryId + " is not found!"));

		category.setName(categoryDto.getName());
		category.setIconName(categoryDto.getIconName());
		category.setType(categoryDto.getType());
		Category updatedCategory = categoryRepository.save(category);

		CategoryDto updatedCategoryDto = categoryMapper.mapToDto(updatedCategory);

		return updatedCategoryDto;
	}

	@Override
	public List<CategoryDto> getAllCategories() {

		List<Category> categories = categoryRepository.findAll();
		List<CategoryDto> categoryDtos = categoryMapper.mapToDto(categories);
		return categoryDtos;
	}

	@Override
	public void deleteCategoryById(Long categoryId) {

		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new NotFoundException("Category with id " + categoryId + " is not found!"));
		categoryRepository.delete(category);

	}
}
