package com.nexcode.expensetracker.service;

import com.nexcode.expensetracker.model.dto.UserDto;
import com.nexcode.expensetracker.model.request.ChangePasswordRequest;

public interface UserService {

	int getBudgetByUserId(Long userId);

	int updateBudgetByUserId(Long userId, int budget);

	boolean changePassword(ChangePasswordRequest request, String email);

	UserDto getCurrentUser(String email);

	String changeName(String username, String email);

	boolean changeEmail(String email, String newEmail, String password);

	void deleteUserAcc(String password, String email);
	
}
