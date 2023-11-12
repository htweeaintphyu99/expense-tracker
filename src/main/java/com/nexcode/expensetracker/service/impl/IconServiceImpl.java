package com.nexcode.expensetracker.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexcode.expensetracker.mapper.IconMapper;
import com.nexcode.expensetracker.model.dto.IconDto;
import com.nexcode.expensetracker.model.entity.Icon;
import com.nexcode.expensetracker.model.exception.NotFoundException;
import com.nexcode.expensetracker.repository.IconRepository;
import com.nexcode.expensetracker.service.IconService;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class IconServiceImpl implements IconService{
	
	private final IconRepository iconRepository;
	private final IconMapper iconMapper;

	@Override
	public IconDto createIcon(IconDto iconDto) {
		
		Icon icon = new Icon();
		icon.setName(iconDto.getName());
		icon.setIconBgColor(iconDto.getIconBgColor());
		icon.setType(iconDto.getType());
		Icon createdIcon = iconRepository.save(icon);
		IconDto createdIconDto = iconMapper.mapToDto(createdIcon);

		return createdIconDto;
	}

	@Override
	public IconDto updateIcon(IconDto iconDto, Long iconId) {
		
		Icon icon = iconRepository.findById(iconId).orElseThrow(() -> new NotFoundException("Icon id " + iconId + " is not found!"));
	
		icon.setName(iconDto.getName());
		icon.setIconBgColor(iconDto.getIconBgColor());
		icon.setType(iconDto.getType());
		Icon updatedIcon = iconRepository.save(icon);
		
		IconDto updatedIconDto = iconMapper.mapToDto(updatedIcon);
		return updatedIconDto;
	}

	@Override
	public List<IconDto> getAllIcons() {
		
		List<Icon> icons = iconRepository.findAll();
		List<IconDto> iconDtos = iconMapper.mapToDto(icons);
		return iconDtos;
	}

	@Override
	public void deleteIconById(Long iconId) {
		
		Icon icon = iconRepository.findById(iconId).orElseThrow(() -> new NotFoundException("Icon with id " + iconId + " is not found!"));
		iconRepository.delete(icon);
	}

	@Override
	public List<IconDto> getIconsExceptDefault() {
		
		List<Icon> icons = iconRepository.findByNameNotIgnoreCase("Others");
		List<IconDto> iconDtos = iconMapper.mapToDto(icons);
		
		return iconDtos;
	}

}
