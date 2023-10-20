package com.nexcode.expensetracker.mapper;

import com.nexcode.expensetracker.model.dto.UserDto;
import com.nexcode.expensetracker.model.entity.User;
import com.nexcode.expensetracker.model.request.RegisterRequest;
import com.nexcode.expensetracker.model.response.UserResponse;

public interface UserMapper {
	UserDto mapToDto(User user);
	UserDto mapToDto(RegisterRequest request);
	UserResponse mapToResponse(UserDto userDto);
}
