package com.nexcode.expensetracker.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexcode.expensetracker.mapper.UserMapper;
import com.nexcode.expensetracker.model.dto.UserDto;
import com.nexcode.expensetracker.model.exception.BadRequestException;
import com.nexcode.expensetracker.model.exception.IncorrectCredentialsException;
import com.nexcode.expensetracker.model.request.LoginRequest;
import com.nexcode.expensetracker.model.request.OtpRequest;
import com.nexcode.expensetracker.model.request.RegisterRequest;
import com.nexcode.expensetracker.model.request.ResetPasswordRequest;
import com.nexcode.expensetracker.model.response.ApiResponse;
import com.nexcode.expensetracker.model.response.JwtAuthenticationResponse;
import com.nexcode.expensetracker.security.JwtTokenProvider;
import com.nexcode.expensetracker.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserMapper userMapper;

	@PostMapping("/register")
	public ApiResponse register(@Valid @RequestBody RegisterRequest registerRequest) {

		UserDto userDto = userMapper.mapToDto(registerRequest);
		String otp = authService.register(userDto);
		if (otp != null) {
			return new ApiResponse(true, "The OTP code has been sent to your email.");
		}
		throw new BadRequestException("Error encountered in user account registration!");

	}

	@PostMapping("/verify-otp")
	public ApiResponse verifyOtp(@Valid @RequestBody OtpRequest otpRequest) {

		boolean otpValid = authService.verifyOtp(otpRequest);
		if (otpValid) {
			return new ApiResponse(true, "OTP verification is successful.");
		}
		throw new BadRequestException("That code was invalid. Please try again!");
	}

	@PostMapping("/login")
	public JwtAuthenticationResponse login(@Valid @RequestBody LoginRequest loginRequest) {

		if (!loginRequest.getEmail().chars().noneMatch(Character::isUpperCase)) {
			throw new BadRequestException("Email must be in lowercase!");
		}

		Date expiredAt = new Date((new Date()).getTime() + 86400 * 1000);

		String jwtToken = null;
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			jwtToken = jwtTokenProvider.createJwtToken(authentication);
		} catch (Exception e) {
			throw new IncorrectCredentialsException("Email or password is incorrect!");
		}

		return new JwtAuthenticationResponse(jwtToken, expiredAt.toInstant().toString());

	}

	@PostMapping("/resend-otp")
	public ApiResponse resendOtp(@Valid @RequestBody OtpRequest otpRequest) {

		authService.resendOtp(otpRequest);
		return new ApiResponse(true, "The new OTP code has been sent to your email.");
	}

	@PostMapping("/forgot-password/send-otp")
	public ApiResponse sendOtpInForgotPassword(@Valid @RequestBody OtpRequest otpRequest) {

		authService.resendOtp(otpRequest);
		return new ApiResponse(true, "The OTP code has been sent to your email.");
	}

	@PostMapping("/forgot-password/reset")
	public ApiResponse forgotPassword(@Valid @RequestBody ResetPasswordRequest request) {

		authService.forgotPassword(request.getEmail(), request.getPassword());
		return new ApiResponse(true, "Password reset successfully.");
	}

}
