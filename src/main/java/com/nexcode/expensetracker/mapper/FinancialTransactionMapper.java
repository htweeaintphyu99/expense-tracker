package com.nexcode.expensetracker.mapper;

import java.util.List;

import com.nexcode.expensetracker.model.dto.FinancialTransactionDto;
import com.nexcode.expensetracker.model.entity.FinancialTransaction;
import com.nexcode.expensetracker.model.response.FinancialTransactionResponse;

public interface FinancialTransactionMapper {
	
	FinancialTransactionDto mapToDto(FinancialTransaction transaction);
	List<FinancialTransactionDto> mapToDto(List<FinancialTransaction> trancsactions);
	List<FinancialTransactionResponse> mapToRepsonse(List<FinancialTransactionDto> transactionDtos);
}
