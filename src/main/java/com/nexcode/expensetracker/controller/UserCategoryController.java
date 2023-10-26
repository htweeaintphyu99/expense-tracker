package com.nexcode.expensetracker.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexcode.expensetracker.mapper.UserCategoryMapper;
import com.nexcode.expensetracker.model.dto.UserCategoryDto;
import com.nexcode.expensetracker.model.exception.BadRequestException;
import com.nexcode.expensetracker.model.request.UserCategoryRequest;
import com.nexcode.expensetracker.model.response.ApiResponse;
import com.nexcode.expensetracker.model.response.UserCategoryResponse;
import com.nexcode.expensetracker.security.CurrentUser;
import com.nexcode.expensetracker.security.UserPrincipal;
import com.nexcode.expensetracker.service.UserCategoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-categories")
public class UserCategoryController {

	private final UserCategoryService userCategoryService;
	private final UserCategoryMapper userCategoryMapper;
	
	@PostMapping
	public ApiResponse createUserCategory(@CurrentUser UserPrincipal currentUser,
			@Valid @RequestBody UserCategoryRequest userCategoryRequest) {

		UserCategoryDto createduserCategory = userCategoryService.createUserCategory(currentUser.getId(),
				userCategoryRequest);
		if (createduserCategory != null) {
			return new ApiResponse(true, "User category creation successful");
		}
		throw new BadRequestException("An error occurred in user category creation!");

	}

	@PutMapping("/{id}")
	public ApiResponse updateUserCategory(@Valid @RequestBody UserCategoryRequest userCategoryRequest,
			@PathVariable Long id, @CurrentUser UserPrincipal currentUser) {

		UserCategoryDto updateduserCategory = userCategoryService.updateUserCategory(id, userCategoryRequest, currentUser.getId());
		if (updateduserCategory != null) {
			return new ApiResponse(true, "User category updation successful");
		}
		throw new BadRequestException("An error occurred in user category creation!");

	}

	@GetMapping
	public List<UserCategoryResponse> getUserCategoriesByUserId(@CurrentUser UserPrincipal currentUser) {

		List<UserCategoryDto> userCategoryDtos = userCategoryService.getUserCategoriesByUserId(currentUser.getId());

		return userCategoryMapper.mapToRepsonse(userCategoryDtos);

	}
	


	@DeleteMapping("/{id}")
	public ApiResponse deleteUserCategoryById(@PathVariable Long id) {

		userCategoryService.deleteUserCategoryById(id);

		return new ApiResponse(true, "User category deletion successful");
	}
}
