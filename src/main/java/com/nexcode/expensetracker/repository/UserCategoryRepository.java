package com.nexcode.expensetracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nexcode.expensetracker.model.entity.User;
import com.nexcode.expensetracker.model.entity.UserCategory;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long>{
	
	@Query("SELECT uc FROM UserCategory uc WHERE LOWER(uc.name) = LOWER(:name) AND (uc.user = :user OR uc.user IS NULL)")
	Optional<UserCategory> findByNameIgnoreCaseAndUser(@Param("name") String userCategoryName, @Param("user") User user);
	
	
	@Query("SELECT uc FROM UserCategory uc WHERE uc.user IS NULL OR uc.user.id = :userId")
    List<UserCategory> findAllByUserIdWithDefault(@Param("userId") Long userId);
}
