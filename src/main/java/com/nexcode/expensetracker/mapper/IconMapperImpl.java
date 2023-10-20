package com.nexcode.expensetracker.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.nexcode.expensetracker.model.dto.IconDto;
import com.nexcode.expensetracker.model.entity.Icon;
import com.nexcode.expensetracker.model.request.IconRequest;
import com.nexcode.expensetracker.model.response.IconResponse;

@Component
public class IconMapperImpl implements IconMapper{

	@Override
	public IconDto mapToDto(IconRequest iconRequest) {
		
		IconDto iconDto = new IconDto();
		iconDto.setName(iconRequest.getIconName());
		iconDto.setIconBgColor(iconRequest.getIconBgColor());
		iconDto.setType(iconRequest.getType());
		return iconDto;
	}

	@Override
	public IconDto mapToDto(Icon icon) {

		IconDto iconDto = new IconDto();
		iconDto.setId(icon.getId());
		iconDto.setName(icon.getName());
		iconDto.setIconBgColor(icon.getIconBgColor());
		iconDto.setType(icon.getType());
		
		return iconDto;
	}

	@Override
	public List<IconDto> mapToDto(List<Icon> icons) {
		
		return icons.stream()
				.map(icon -> {
					IconDto iconDto = new IconDto();
					iconDto.setId(icon.getId());
					iconDto.setName(icon.getName());
					iconDto.setIconBgColor(icon.getIconBgColor());
					iconDto.setType(icon.getType());
					return iconDto;
				})
				.collect(Collectors.toList());
	}

	@Override
	public List<IconResponse> mapToRepsonse(List<IconDto> iconDtos) {
		return iconDtos.stream()
				.map(iconDto -> {
					IconResponse iconResponse = new IconResponse();
					iconResponse.setId(iconDto.getId());
					iconResponse.setName(iconDto.getName());
					iconResponse.setIconBgColor(iconDto.getIconBgColor());
					iconResponse.setType(iconDto.getType());
					
					return iconResponse;
				})
				.collect(Collectors.toList());
	}
	
}
