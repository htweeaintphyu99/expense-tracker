package com.nexcode.expensetracker.mapper;

import java.util.List;

import com.nexcode.expensetracker.model.dto.IconDto;
import com.nexcode.expensetracker.model.entity.Icon;
import com.nexcode.expensetracker.model.request.IconRequest;
import com.nexcode.expensetracker.model.response.IconResponse;

public interface IconMapper {

	IconDto mapToDto(IconRequest iconRequest);
	IconDto mapToDto(Icon icon);
	List<IconDto> mapToDto(List<Icon> icons);
	List<IconResponse> mapToRepsonse(List<IconDto> iconDtos);
}
