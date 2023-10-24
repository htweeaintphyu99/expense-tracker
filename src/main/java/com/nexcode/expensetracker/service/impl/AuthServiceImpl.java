package com.nexcode.expensetracker.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexcode.expensetracker.emailsender.EmailSender;
import com.nexcode.expensetracker.model.dto.UserDto;
import com.nexcode.expensetracker.model.entity.Role;
import com.nexcode.expensetracker.model.entity.RoleName;
import com.nexcode.expensetracker.model.entity.User;
import com.nexcode.expensetracker.model.entity.UserCategory;
import com.nexcode.expensetracker.model.exception.BadRequestException;
import com.nexcode.expensetracker.model.exception.ConflictException;
import com.nexcode.expensetracker.model.exception.InternalServerErrorException;
import com.nexcode.expensetracker.model.exception.NotFoundException;
import com.nexcode.expensetracker.model.request.OtpRequest;
import com.nexcode.expensetracker.otpgenerator.OtpGenerator;
import com.nexcode.expensetracker.repository.CategoryRepository;
import com.nexcode.expensetracker.repository.UserCategoryRepository;
import com.nexcode.expensetracker.repository.UserRepository;
import com.nexcode.expensetracker.service.AuthService;
import com.nexcode.expensetracker.service.RoleService;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final UserCategoryRepository userCategoryRepository;
	private final CategoryRepository categoryRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmailSender emailSender;
	private final RoleService roleService;

	@Override
	public String register(UserDto userDto) {

		if (!userDto.getEmail().chars().noneMatch(Character::isUpperCase)) {
			throw new BadRequestException("Email must be in lowercase!");
		}

		User existingUser = userRepository.findByEmail(userDto.getEmail()).orElse(null);

		if (existingUser != null && existingUser.isVerified()) {
			throw new ConflictException("This email is already registered!");
		}

		String encodedPassword = passwordEncoder.encode(userDto.getPassword());

		Role role = roleService.findRoleByName(RoleName.ROLE_USER);
		Set<Role> roles = Stream.of(role).collect(Collectors.toSet());

		String otp = OtpGenerator.generateOtp();

		if (existingUser != null) {
			existingUser.setUsername(userDto.getUsername());
			existingUser.setPassword(encodedPassword);
			existingUser.setOtp(otp);
			existingUser.setOtpExpirationTime(OtpGenerator.getOtpExpiredTime());
			existingUser.setRoles(roles);
			userRepository.save(existingUser);

			try {
				emailSender.send(existingUser.getEmail(), EmailSender.buildEmailForm(existingUser.getUsername(), otp));
			} catch (Exception e) {
				throw new InternalServerErrorException("Error occured in email sending!");
			}
		} else {
			User user = new User();
			user.setUsername(userDto.getUsername());
			user.setEmail(userDto.getEmail());
			user.setPassword(encodedPassword);
			user.setOtp(otp);
			user.setOtpExpirationTime(OtpGenerator.getOtpExpiredTime());
			user.setRoles(roles);
			userRepository.save(user);

			try {
				emailSender.send(user.getEmail(), EmailSender.buildEmailForm(user.getUsername(), otp));
			} catch (Exception e) {
				throw new InternalServerErrorException("Error occured in email sending!");
			}
		}
		return otp;
	}

	@Override
	public boolean verifyOtp(OtpRequest request) {

		if (!request.getEmail().chars().noneMatch(Character::isUpperCase)) {
			throw new BadRequestException("Email must be in lowercase!");
		}

		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new NotFoundException("User not found with email : " + request.getEmail()));

		Instant currentTime = Instant.now();

		if (user != null && request.getOtp().equals(user.getOtp())
				&& user.getOtpExpirationTime().isAfter(currentTime)) {

			user.setOtp(null);
			user.setOtpExpirationTime(null);

			// check if user is already verified or not
			// if already verified, load default user categories to registered user
			if (!user.isVerified()) {
				user.setVerified(true);
				addDefaultUserCategories(user);
			}

			// verify otp in password change function
			if (request.getNewEmail() != null) {
				user.setEmail(request.getNewEmail());
			}
			userRepository.save(user);
			return true;
		}
		return false;
	}

	@Override
	public String resendOtp(OtpRequest otpRequest) {

		if (!otpRequest.getEmail().chars().noneMatch(Character::isUpperCase)) {
			throw new BadRequestException("Email must be in lowercase!");
		}

		User user = userRepository.findByEmail(otpRequest.getEmail())
				.orElseThrow(() -> new NotFoundException("User not found  with email : " + otpRequest.getEmail()));

		String otp = OtpGenerator.generateOtp();
		user.setOtp(otp);
		user.setOtpExpirationTime(OtpGenerator.getOtpExpiredTime());
		userRepository.save(user);

		String emailReceiver = otpRequest.getEmail();

		// resend OTP for changing new email
		if (otpRequest.getNewEmail() != null && otpRequest.getNewEmail().chars().noneMatch(Character::isUpperCase)) {

			emailReceiver = otpRequest.getNewEmail();
		}

		try {
			emailSender.send(emailReceiver, EmailSender.buildEmailForm(user.getUsername(), otp));
		} catch (Exception e) {
			throw new InternalServerErrorException("Error occured in email sending!");
		}

		return otp;
	}

	private void addDefaultUserCategories(User user) {

		List<UserCategory> userCategories = categoryRepository.findAll().stream().map(c -> {
			UserCategory userCategory = new UserCategory();
			userCategory.setUser(user);
			userCategory.setName(c.getName());
			userCategory.setIconName(c.getIconName());
			userCategory.setIconBgColor(c.getIconBgColor());
			userCategory.setType(c.getType());
			return userCategory;
		}).collect(Collectors.toList());

		userCategoryRepository.saveAll(userCategories);
	}

	@Override
	public boolean forgotPassword(String email, String password) {

		if (email.chars().noneMatch(Character::isUpperCase)) {
			throw new BadRequestException("Email must be in lowercase!");
		}

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("User not found with email : " + email));

		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);
		return true;
	}

}
