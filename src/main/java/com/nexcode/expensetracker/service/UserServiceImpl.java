package com.nexcode.expensetracker.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nexcode.expensetracker.emailsender.EmailSender;
import com.nexcode.expensetracker.mapper.UserMapper;
import com.nexcode.expensetracker.model.dto.UserDto;
import com.nexcode.expensetracker.model.entity.User;
import com.nexcode.expensetracker.model.exception.BadRequestException;
import com.nexcode.expensetracker.model.exception.ConflictException;
import com.nexcode.expensetracker.model.exception.IncorrectCredentialsException;
import com.nexcode.expensetracker.model.exception.InternalServerErrorException;
import com.nexcode.expensetracker.model.exception.NotFoundException;
import com.nexcode.expensetracker.model.request.ChangePasswordRequest;
import com.nexcode.expensetracker.otpgenerator.OtpGenerator;
import com.nexcode.expensetracker.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;
	private final EmailSender emailSender;

	@Override
	public int getBudgetByUserId(Long userId) {

		User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Current user not found!"));
		return user.getBudget();
	}

	@Override
	public int updateBudgetByUserId(Long userId, int budget) {

		User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Current user not found!"));
		user.setBudget(budget);
		User updatedUser = userRepository.save(user);

		return updatedUser.getBudget();
	}

	@Override
	public boolean changePassword(ChangePasswordRequest request, String email) {

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new BadRequestException("User Not Found with email : " + email));

		if (passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {

			user.setPassword(passwordEncoder.encode(request.getNewPassword()));
			userRepository.save(user);
			return true;
		}

		throw new IncorrectCredentialsException("Password incorrect!");
	}

	@Override
	public UserDto getCurrentUser(String email) {

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new BadRequestException("User not found  with email : " + email));
		return userMapper.mapToDto(user);
	}

	@Override
	public String changeName(String username, String email) {

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new BadRequestException("User not found  with email : " + email));
		user.setUsername(username);
		User savedUser = userRepository.save(user);

		return savedUser.getUsername();

	}

	@Override
	public boolean changeEmail(String email, String newEmail, String password) {

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("User Not Found with email : " + email));

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new BadRequestException("Password is incorrect. Try again!");
		}

		if (!userRepository.findByEmail(newEmail).isEmpty()) {
			throw new ConflictException("Email already exists. Try again!");
		}

		try {
			String otp = OtpGenerator.generateOtp();
			emailSender.send(newEmail, EmailSender.buildEmailForm(user.getUsername(), otp));
			user.setOtp(otp);
			user.setOtpExpirationTime(OtpGenerator.getOtpExpiredTime());
			userRepository.save(user);
			return true;
		} catch (Exception e) {
			throw new InternalServerErrorException("Error occured in email sending!");
		}
	}

}
