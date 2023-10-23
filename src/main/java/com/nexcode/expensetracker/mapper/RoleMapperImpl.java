package com.nexcode.expensetracker.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.nexcode.expensetracker.model.dto.RoleDto;
import com.nexcode.expensetracker.model.entity.Role;

@Component
public class RoleMapperImpl implements RoleMapper{

	@Override
	public RoleDto mapToDto(Role role) {
		
		if(role == null) {
			return null;
		}
		RoleDto roleDto = new RoleDto();
		roleDto.setId(role.getId());
		roleDto.setName(role.getName());
		
		return roleDto;
	}

	@Override
	public Role mapToEntity(RoleDto roleDto) {
		
		if(roleDto == null) {
			return null;
		}
		Role role = new Role();
		role.setId(roleDto.getId());
		role.setName(roleDto.getName());
		
		return role;
	}
	
	@Override
	public Set<Role> mapToEntity(Set<RoleDto> roleDtos) {
		
		if(roleDtos == null) {
			return null;
		}
		return roleDtos.stream().map(roleDto->mapToEntity(roleDto)).collect(Collectors.toSet());
	}
	
	@Override
	public Set<RoleDto> mapToDto(Set<Role> roles) {
		
		if(roles == null) {
			return null;
		}
		return roles.stream().map(role->mapToDto(role)).collect(Collectors.toSet());
	}
}
