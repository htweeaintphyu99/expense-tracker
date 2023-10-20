package com.nexcode.expensetracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexcode.expensetracker.model.entity.Icon;

public interface IconRepository extends JpaRepository<Icon, Long>{
		
	Optional<Icon> findByName(String iconName);
	
 	List<Icon> findByNameNotIgnoreCase(String iconName);
 	
 	List<Icon> findByNameIn(List<String> iconNames);
}
