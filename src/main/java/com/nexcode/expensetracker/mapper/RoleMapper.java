package com.nexcode.expensetracker.mapper;

import java.util.Set;

import com.nexcode.expensetracker.model.dto.RoleDto;
import com.nexcode.expensetracker.model.entity.Role;

public interface RoleMapper {
	
	RoleDto mapToDto(Role role);
	Role mapToEntity(RoleDto roleDto);
	Set<Role> mapToEntity(Set<RoleDto> roleDtos);
	Set<RoleDto> mapToDto(Set<Role> roles);
}
