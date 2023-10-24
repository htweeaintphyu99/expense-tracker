package com.nexcode.expensetracker.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nexcode.expensetracker.model.entity.FinancialTransaction;
import com.nexcode.expensetracker.model.entity.Type;
import com.nexcode.expensetracker.model.entity.User;
import com.nexcode.expensetracker.model.entity.UserCategory;

public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Long> {

	List<FinancialTransaction> findByUser(User user);

	List<FinancialTransaction> findAllByUserCategory(UserCategory userCategory);

	@Query("SELECT t FROM FinancialTransaction t WHERE t.user.id = :userId AND t.createdDate >= :startDate AND t.createdDate <= :endDate AND (:type IS NULL or t.type = :type)")
	List<FinancialTransaction> findByUserIdAndFilterAndCreatedDateBetween(@Param("userId") Long userId, @Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate, @Param("type") Optional<Type> type);
}
