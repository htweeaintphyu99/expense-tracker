package com.nexcode.expensetracker.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexcode.expensetracker.mapper.UserMapper;
import com.nexcode.expensetracker.model.dto.UserDto;
import com.nexcode.expensetracker.model.exception.BadRequestException;
import com.nexcode.expensetracker.model.request.BudgetRequest;
import com.nexcode.expensetracker.model.request.ChangeEmailRequest;
import com.nexcode.expensetracker.model.request.ChangeNameRequest;
import com.nexcode.expensetracker.model.request.ChangePasswordRequest;
import com.nexcode.expensetracker.model.response.ApiResponse;
import com.nexcode.expensetracker.model.response.BudgetResponse;
import com.nexcode.expensetracker.model.response.UserResponse;
import com.nexcode.expensetracker.security.CurrentUser;
import com.nexcode.expensetracker.security.UserPrincipal;
import com.nexcode.expensetracker.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

	private final UserService userService;
	private final UserMapper userMapper;

	@GetMapping
	public UserResponse getCurrentUser(@CurrentUser UserPrincipal currentUser) {

		UserDto userDto = userService.getCurrentUser(currentUser.getEmail());
		return userMapper.mapToResponse(userDto);
	}

	@GetMapping("/budget")
	public BudgetResponse getBudgetByUserId(@CurrentUser UserPrincipal currentUser) {

		int budget = userService.getBudgetByUserId(currentUser.getId());
		BudgetResponse budgetResponse = new BudgetResponse();
		budgetResponse.setBudget(budget);

		return budgetResponse;
	}

	@PutMapping("/budget")
	public ApiResponse updateBudgetByUserId(@Valid @RequestBody BudgetRequest budgetRequest,
			@CurrentUser UserPrincipal currentUser) {

		userService.updateBudgetByUserId(currentUser.getId(), budgetRequest.getBudget());
		return new ApiResponse(true, "Budget updated successfully.");

	}

	@PutMapping("/change-password")
	public ApiResponse changePasssword(@Valid @RequestBody ChangePasswordRequest request,
			@CurrentUser UserPrincipal currentUser) {

		userService.changePassword(request, currentUser.getEmail());
	    return new ApiResponse(true, "Password changed successfully.");
	}

	@PutMapping("/change-username")
	public ApiResponse changeName(@Valid @RequestBody ChangeNameRequest request, @CurrentUser UserPrincipal currentUser) {

		String name = userService.changeName(request.getUsername(), currentUser.getEmail());
		if (name != null) {
			return new ApiResponse(true, "Username changed successfully");
		}
		throw new BadRequestException("Error encountered in changing username!");
	}
	
	@PutMapping("/change-email")
	public ApiResponse changeEmail(@Valid @RequestBody ChangeEmailRequest request,
			@CurrentUser UserPrincipal currentUser) {

		userService.changeEmail(currentUser.getEmail(), request.getNewEmail(), request.getPassword());

		return new ApiResponse(true, "The OTP code has been sent to your new email.");

	}
	
	@DeleteMapping
	public ApiResponse deleteUserAcc(@CurrentUser UserPrincipal currentUser) {
		
		userService.deleteUserAcc(currentUser.getEmail());
		return new ApiResponse(true, "User account deleted successfully.");
	}

}
