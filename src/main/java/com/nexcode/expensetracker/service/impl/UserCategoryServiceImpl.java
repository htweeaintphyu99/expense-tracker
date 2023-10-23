package com.nexcode.expensetracker.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexcode.expensetracker.mapper.UserCategoryMapper;
import com.nexcode.expensetracker.model.dto.UserCategoryDto;
import com.nexcode.expensetracker.model.entity.FinancialTransaction;
import com.nexcode.expensetracker.model.entity.Icon;
import com.nexcode.expensetracker.model.entity.User;
import com.nexcode.expensetracker.model.entity.UserCategory;
import com.nexcode.expensetracker.model.exception.BadRequestException;
import com.nexcode.expensetracker.model.exception.NotFoundException;
import com.nexcode.expensetracker.model.request.UserCategoryRequest;
import com.nexcode.expensetracker.repository.FinancialTransactionRepository;
import com.nexcode.expensetracker.repository.IconRepository;
import com.nexcode.expensetracker.repository.UserCategoryRepository;
import com.nexcode.expensetracker.repository.UserRepository;
import com.nexcode.expensetracker.service.UserCategoryService;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class UserCategoryServiceImpl implements UserCategoryService {

	private final UserCategoryRepository userCategoryRepository;
	private final UserRepository userRepository;
	private final IconRepository iconRepository;
	private final FinancialTransactionRepository transactionRepository;
	private final UserCategoryMapper userCategoryMapper;

	@Override
	public UserCategoryDto createUserCategory(Long userId, UserCategoryRequest userCategoryRequest) {

		if (userCategoryRepository.findByUserCategoryNameIgnoreCase(userCategoryRequest.getUserCategoryName())
				.isPresent()) {
			throw new BadRequestException("User category name already exists!");
		}

		UserCategory userCategory = new UserCategory();

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new BadRequestException("User doesn't exist!"));

		Icon icon = iconRepository.findByName(userCategoryRequest.getIconName())
				.orElseThrow(() -> new NotFoundException("Icon : " + userCategoryRequest.getIconName() + " is not found!"));

		userCategory.setName(userCategoryRequest.getUserCategoryName());
		userCategory.setType(userCategoryRequest.getType());
		userCategory.setIconName(icon.getName());
		userCategory.setIconBgColor(icon.getIconBgColor());
		userCategory.setUser(user);

		UserCategory createdUserCategory = userCategoryRepository.save(userCategory);

		UserCategoryDto createdUserCategoryDto = userCategoryMapper.mapToDto(createdUserCategory);
		return createdUserCategoryDto;

	}

	@Override
	public UserCategoryDto updateUserCategory(Long id, UserCategoryRequest userCategoryRequest) {

		UserCategory userCategory = userCategoryRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("User category with id " + id + " is not found!"));

		if (userCategory.getType() == null) {
			throw new BadRequestException("System Category can't be modified!");
		}

		Icon icon = iconRepository.findByName(userCategoryRequest.getIconName())
				.orElseThrow(() -> new NotFoundException("Icon : " + userCategoryRequest.getIconName() + " is not found!"));

		userCategory.setName(userCategoryRequest.getUserCategoryName());
		userCategory.setType(userCategoryRequest.getType());
		userCategory.setIconName(icon.getName());
		userCategory.setIconBgColor(icon.getIconBgColor());

		UserCategory updatedUserCategory = userCategoryRepository.save(userCategory);

		UserCategoryDto updatedUserCategoryDto = userCategoryMapper.mapToDto(updatedUserCategory);

		return updatedUserCategoryDto;
	}

	@Override
	public List<UserCategoryDto> getAllUserCategories() {

		List<UserCategory> userCategories = userCategoryRepository.findAll();
		List<UserCategoryDto> userCategoryDtos = userCategoryMapper.mapToDto(userCategories);
		return userCategoryDtos;
	}

	@Override
	public boolean deleteUserCategoryById(Long userCategoryId) {

		UserCategory userCategory = userCategoryRepository.findById(userCategoryId)
				.orElseThrow(() -> new NotFoundException("User category with id " + userCategoryId + " is not found!"));

		if (userCategory.getType() == null) {
			throw new BadRequestException("System Category can't be deleted!");
		}

		List<FinancialTransaction> transactions = transactionRepository.findAllByUserCategory(userCategory);
		
		UserCategory defaultCategory = userCategoryRepository.findByUserCategoryNameIgnoreCase("Others")
				.orElseThrow(() -> new BadRequestException("An error occurred in user category deletion!"));
		
		transactions = transactions.stream().map(t -> {
			t.setUserCategory(defaultCategory);
			return t;
		}).collect(Collectors.toList());

		transactionRepository.saveAll(transactions);
		userCategoryRepository.delete(userCategory);

		return true;

	}

	@Override
	public List<UserCategoryDto> getUserCategoriesByUserId(Long userId) {

		List<UserCategory> userCategories = userCategoryRepository.findAllByUserIdWithDefault(userId);

		return userCategoryMapper.mapToDto(userCategories);
	}
}
