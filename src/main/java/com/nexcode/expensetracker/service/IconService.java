package com.nexcode.expensetracker.service;

import java.util.List;

import com.nexcode.expensetracker.model.dto.IconDto;

public interface IconService {
	
	IconDto createIcon(IconDto iconDto);

	IconDto updateIcon(IconDto iconDto, Long iconId);
	
	List<IconDto> getAllIcons();

	void deleteIconById(Long iconId);

	List<IconDto> getIconsExceptDefault();
}
