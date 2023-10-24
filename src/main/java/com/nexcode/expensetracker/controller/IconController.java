package com.nexcode.expensetracker.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexcode.expensetracker.mapper.IconMapper;
import com.nexcode.expensetracker.model.dto.IconDto;
import com.nexcode.expensetracker.model.exception.BadRequestException;
import com.nexcode.expensetracker.model.request.IconRequest;
import com.nexcode.expensetracker.model.response.ApiResponse;
import com.nexcode.expensetracker.model.response.IconResponse;
import com.nexcode.expensetracker.service.IconService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/icons")
public class IconController {

	private final IconService iconService;
	private final IconMapper iconMapper;

	@PostMapping
	public ResponseEntity<Object> createIcon(@Valid @RequestBody IconRequest iconRequest) {

		IconDto createdIcon = iconService.createIcon(iconMapper.mapToDto(iconRequest));
		if (createdIcon != null) {
			return new ResponseEntity<>(new ApiResponse(true, "Icon creation successful"), HttpStatus.CREATED);
		}
		throw new BadRequestException("An error occurred in icon creation!");
	}

	@PutMapping("/{iconId}")
	public ResponseEntity<Object> updateIcon(@Valid @RequestBody IconRequest iconRequest, @PathVariable Long iconId) {

		IconDto updatedIcon = iconService.updateIcon(iconMapper.mapToDto(iconRequest), iconId);
		if (updatedIcon != null) {
			return new ResponseEntity<>(new ApiResponse(true, "Icon updation successful"), HttpStatus.OK);
		}
		throw new BadRequestException("An error occurred in icon updation!");

	}

	@GetMapping
	public List<IconResponse> getAllIcons() {

		List<IconDto> iconDtos = iconService.getAllIcons();

		return iconMapper.mapToRepsonse(iconDtos);

	}

	@DeleteMapping("/{iconId}")
	public ResponseEntity<Object> deleteIconById(@PathVariable Long iconId) {

		iconService.deleteIconById(iconId);
		return new ResponseEntity<>(new ApiResponse(true, "Icon deletion successful"), HttpStatus.NO_CONTENT);

	}

	@GetMapping("/except-default")
	public List<IconResponse> getIconsExceptDefault() {

		List<IconDto> iconDtos = iconService.getIconsExceptDefault();

		return iconMapper.mapToRepsonse(iconDtos);

	}
}
