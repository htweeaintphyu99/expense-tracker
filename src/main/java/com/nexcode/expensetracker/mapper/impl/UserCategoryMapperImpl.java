package com.nexcode.expensetracker.mapper.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.nexcode.expensetracker.mapper.UserCategoryMapper;
import com.nexcode.expensetracker.model.dto.UserCategoryDto;
import com.nexcode.expensetracker.model.entity.UserCategory;
import com.nexcode.expensetracker.model.response.UserCategoryResponse;

@Component
public class UserCategoryMapperImpl implements UserCategoryMapper {

	@Override
	public UserCategoryDto mapToDto(UserCategory userCategory) {

		if (userCategory == null) {
			return null;
		}
		UserCategoryDto userCategoryDto = new UserCategoryDto();
		userCategoryDto.setUserCategoryId(userCategory.getId());
		userCategoryDto.setUserCategoryName(userCategory.getName());
		userCategoryDto.setIconName(userCategory.getIconName());
		userCategoryDto.setIconBgColor(userCategory.getIconBgColor());
		userCategoryDto.setType(userCategory.getType());

		return userCategoryDto;
	}

	@Override
	public List<UserCategoryDto> mapToDto(List<UserCategory> userCategories) {

		if (userCategories == null) {
			return null;
		}
		return userCategories.stream().map(u -> mapToDto(u)).collect(Collectors.toList());
	}

	@Override
	public UserCategoryResponse mapToResponse(UserCategoryDto userCategoryDto) {

		if (userCategoryDto == null) {
			return null;
		}
		UserCategoryResponse userCategoryResponse = new UserCategoryResponse();

		userCategoryResponse.setId(userCategoryDto.getUserCategoryId());
		userCategoryResponse.setName(userCategoryDto.getUserCategoryName());
		userCategoryResponse.setIconName(userCategoryDto.getIconName());
		userCategoryResponse.setType(userCategoryDto.getType());
		userCategoryResponse.setIconBgColor(userCategoryDto.getIconBgColor());

		return userCategoryResponse;

	}

	@Override
	public List<UserCategoryResponse> mapToRepsonse(List<UserCategoryDto> userCategoryDtos) {

		if (userCategoryDtos == null) {
			return null;
		}

		return userCategoryDtos.stream().map(this::mapToResponse).collect(Collectors.toList());
	}

}
