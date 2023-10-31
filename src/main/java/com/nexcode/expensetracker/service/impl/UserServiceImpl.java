package com.nexcode.expensetracker.service.impl;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexcode.expensetracker.component.EmailSender;
import com.nexcode.expensetracker.component.OtpGenerator;
import com.nexcode.expensetracker.mapper.UserMapper;
import com.nexcode.expensetracker.model.dto.UserDto;
import com.nexcode.expensetracker.model.entity.User;
import com.nexcode.expensetracker.model.exception.BadRequestException;
import com.nexcode.expensetracker.model.exception.ConflictException;
import com.nexcode.expensetracker.model.exception.IncorrectCredentialsException;
import com.nexcode.expensetracker.model.exception.InternalServerErrorException;
import com.nexcode.expensetracker.model.exception.NotFoundException;
import com.nexcode.expensetracker.model.request.ChangePasswordRequest;
import com.nexcode.expensetracker.repository.UserRepository;
import com.nexcode.expensetracker.service.UserService;

import lombok.RequiredArgsConstructor;

@Transactional
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
				.orElseThrow(() -> new NotFoundException("User not found with email : " + email));

		if (passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
			if (request.getOldPassword().equals(request.getNewPassword())) {
				throw new BadRequestException("Your new password cannot be the same as your old password!");
			}
			user.setPassword(passwordEncoder.encode(request.getNewPassword()));
			userRepository.save(user);
			return true;
		}

		throw new IncorrectCredentialsException("Password is incorrect. Try again!");
	}

	@Override
	public UserDto getCurrentUser(String email) {

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("User not found  with email : " + email));
		return userMapper.mapToDto(user);
	}

	@Override
	public String changeName(String username, String email) {

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("User not found  with email : " + email));
		user.setUsername(username);
		User savedUser = userRepository.save(user);

		return savedUser.getUsername();

	}

	@Override
	public boolean changeEmail(String email, String newEmail, String password) {

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("User not found with email : " + email));

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new BadRequestException("Password is incorrect. Try again!");
		}

		if (!userRepository.findByEmail(newEmail).isEmpty()) {
			throw new ConflictException("Email already exists. Try again!");
		}
		
		if (!newEmail.chars().noneMatch(Character::isUpperCase)) {
			throw new BadRequestException("Email must be in lowercase!");
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

	@Override
	public void deleteUserAcc(String email) {
		
		User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found with email : " + email));
		userRepository.delete(user);
	}

	@Scheduled(cron = "0 0 0 * * ?") // Run at 12:00 AM(Midnight) every day
	public void deleteNotVerifiedUsers() {
		
	    List<User> users = userRepository.findAllByNotVerified();
	    userRepository.deleteAll(users);
	}
}
