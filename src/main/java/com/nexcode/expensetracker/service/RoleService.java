package com.nexcode.expensetracker.service;

import com.nexcode.expensetracker.model.entity.Role;
import com.nexcode.expensetracker.model.entity.RoleName;

public interface RoleService {
	Role findRoleByName(RoleName roleName);
}
