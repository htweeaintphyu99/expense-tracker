package com.nexcode.expensetracker.mapper.impl;

import org.springframework.stereotype.Component;

import com.nexcode.expensetracker.mapper.UserMapper;
import com.nexcode.expensetracker.model.dto.UserDto;
import com.nexcode.expensetracker.model.entity.User;
import com.nexcode.expensetracker.model.request.RegisterRequest;
import com.nexcode.expensetracker.model.response.UserResponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class UserMapperImpl implements UserMapper{
	
	@Override
	public UserDto mapToDto(User user) {
		
		if(user == null) {
			return null;
		}
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setEmail(user.getEmail());
		userDto.setUsername(user.getUsername());
		userDto.setPassword(user.getPassword());
		userDto.setOtp(user.getOtp());
		userDto.setOtpExpirationtime(user.getOtpExpirationTime());
		userDto.setBalance(user.getBalance());
		userDto.setBudget(user.getBudget());
		return userDto;
	}
	
	public UserDto mapToDto(RegisterRequest registerRequest) {
		
		if(registerRequest == null) {
			return null;
		}
		UserDto userDto = new UserDto();
		userDto.setUsername(registerRequest.getUsername());
		userDto.setEmail(registerRequest.getEmail());
		userDto.setPassword(registerRequest.getPassword());
		
		return userDto;
	}

	@Override
	public UserResponse mapToResponse(UserDto userDto) {
		
		if(userDto == null) {
			return null;
		}
		UserResponse userResponse = new UserResponse();
		userResponse.setId(userDto.getId());
		userResponse.setEmail(userDto.getEmail());
		userResponse.setUsername(userDto.getUsername());
		userResponse.setBudget(userDto.getBudget());
		userResponse.setBalance(userDto.getBalance());
		
		return userResponse;
	}

}
