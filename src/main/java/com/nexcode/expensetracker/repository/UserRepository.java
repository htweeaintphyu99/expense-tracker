package com.nexcode.expensetracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nexcode.expensetracker.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.verified = FALSE")
	List<User> findAllByNotVerified();
}
