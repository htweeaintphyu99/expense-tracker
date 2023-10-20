package com.nexcode.expensetracker.service;

import com.nexcode.expensetracker.model.dto.UserDto;
import com.nexcode.expensetracker.model.request.OtpRequest;

public interface AuthService {
		
	boolean verifyOtp(OtpRequest request);

	String register(UserDto userDto);
	
	String resendOtp(OtpRequest otpRequest);
		
	boolean forgotPassword(String email, String password);

}

