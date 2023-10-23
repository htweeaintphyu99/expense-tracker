package com.nexcode.expensetracker.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexcode.expensetracker.model.entity.Role;
import com.nexcode.expensetracker.model.entity.RoleName;
import com.nexcode.expensetracker.repository.RoleRepository;
import com.nexcode.expensetracker.service.RoleService;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService{

	private final RoleRepository roleRepository;
	
	@Override
	public Role findRoleByName(RoleName roleName) {
		
		Role role = roleRepository.findByName(roleName).get();
		return role;
	}

}
