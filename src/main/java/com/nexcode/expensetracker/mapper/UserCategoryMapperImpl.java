package com.nexcode.expensetracker.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.nexcode.expensetracker.model.dto.UserCategoryDto;
import com.nexcode.expensetracker.model.entity.UserCategory;
import com.nexcode.expensetracker.model.response.UserCategoryResponse;

@Component
public class UserCategoryMapperImpl implements UserCategoryMapper{

	@Override
	public UserCategoryDto mapToDto(UserCategory userCategory) {

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
		
		return userCategories.stream()
				.map(userCategory -> {
					UserCategoryDto userCategoryDto = new UserCategoryDto();
					userCategoryDto.setUserCategoryId(userCategory.getId());
					userCategoryDto.setUserCategoryName(userCategory.getName());
					userCategoryDto.setIconName(userCategory.getIconName());
					userCategoryDto.setType(userCategory.getType());
					userCategoryDto.setIconBgColor(userCategory.getIconBgColor());
					
					return userCategoryDto;
				})
				.collect(Collectors.toList());
	}

	@Override
	public List<UserCategoryResponse> mapToRepsonse(List<UserCategoryDto> userCategoryDtos) {
		
		return userCategoryDtos.stream()
				.map(userCategoryDto -> {
					UserCategoryResponse userCategoryResponse = new UserCategoryResponse();
					userCategoryResponse.setId(userCategoryDto.getUserCategoryId());
					userCategoryResponse.setName(userCategoryDto.getUserCategoryName());
					userCategoryResponse.setIconName(userCategoryDto.getIconName());
					userCategoryResponse.setType(userCategoryDto.getType());
					userCategoryResponse.setIconBgColor(userCategoryDto.getIconBgColor());
					
					return userCategoryResponse;
				})
				.collect(Collectors.toList());
	}

	@Override
	public UserCategoryResponse mapToResponse(UserCategoryDto userCategoryDto) {
		
		UserCategoryResponse userCategoryResponse = new UserCategoryResponse();
		
		userCategoryResponse.setId(userCategoryDto.getUserCategoryId());
		userCategoryResponse.setName(userCategoryDto.getUserCategoryName());
		userCategoryResponse.setIconName(userCategoryDto.getIconName());
		userCategoryResponse.setType(userCategoryDto.getType());
		userCategoryResponse.setIconBgColor(userCategoryDto.getIconBgColor());
		
		return userCategoryResponse;
		
	}
	
}
