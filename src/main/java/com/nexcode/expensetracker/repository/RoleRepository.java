package com.nexcode.expensetracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexcode.expensetracker.model.entity.Role;
import com.nexcode.expensetracker.model.entity.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(RoleName roleName);
}
