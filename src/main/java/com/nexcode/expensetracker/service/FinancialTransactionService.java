package com.nexcode.expensetracker.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import com.nexcode.expensetracker.model.dto.FinancialTransactionDto;
import com.nexcode.expensetracker.model.entity.Type;
import com.nexcode.expensetracker.model.request.FinancialTransactionRequest;

public interface FinancialTransactionService {

	FinancialTransactionDto createFinancialTransaction(Long userId, FinancialTransactionRequest request);

	FinancialTransactionDto updateFinancialTransaction(Long id, Long userId, FinancialTransactionRequest request);
	
	void deleteFinancialTransactionById(Long id, Long userId);

	List<FinancialTransactionDto> getFinancialTransactionsByFilterAndDateRange(Long userId, String startDate,
			String endDate, Type type);

	List<FinancialTransactionDto> getFinancialTransactionsByMonth(Long userId, 	String selectedMonth);
}