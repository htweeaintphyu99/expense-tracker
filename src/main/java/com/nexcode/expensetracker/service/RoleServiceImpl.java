package com.nexcode.expensetracker.service;

import org.springframework.stereotype.Service;

import com.nexcode.expensetracker.model.entity.Role;
import com.nexcode.expensetracker.model.entity.RoleName;
import com.nexcode.expensetracker.repository.RoleRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService{

	private final RoleRepository roleRepository;
	
	@Override
	public Role findRoleByName(RoleName roleName) {
		
		Role role = roleRepository.findByName(roleName).get();
		return role;
	}

}
