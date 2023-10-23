package com.nexcode.expensetracker.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.nexcode.expensetracker.model.dto.FinancialTransactionDto;
import com.nexcode.expensetracker.model.entity.FinancialTransaction;
import com.nexcode.expensetracker.model.response.FinancialTransactionResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class FinancialTransactionMapperImpl implements FinancialTransactionMapper {

	private final UserCategoryMapper userCategoryMapper;
	
	@Override
	public FinancialTransactionDto mapToDto(FinancialTransaction transaction) {

		if(transaction == null) {
			return null;
		}
		FinancialTransactionDto transactionDto = new FinancialTransactionDto();
		transactionDto.setId(transaction.getId());
		transactionDto.setCreatedDate(transaction.getCreatedDate());
		transactionDto.setAmount(transaction.getAmount());
		transactionDto.setDescription(transaction.getDescription());
		transactionDto.setType(transaction.getType());

		return transactionDto;
	}

	@Override
	public List<FinancialTransactionDto> mapToDto(List<FinancialTransaction> transactions) {

		if(transactions == null) {
			return null;
		}
		
		return transactions.stream().map(t -> mapToDto(t)).collect(Collectors.toList());
	}

	@Override
	public List<FinancialTransactionResponse> mapToRepsonse(List<FinancialTransactionDto> transactionDtos) {
		
		if(transactionDtos == null) {
			return null;
		}
		return transactionDtos.stream().map(t -> {

			FinancialTransactionResponse transactionResponse = new FinancialTransactionResponse();
			transactionResponse.setId(t.getId());
			transactionResponse.setAmount(t.getAmount());
			transactionResponse.setCreatedDate(t.getCreatedDate());
			transactionResponse.setDescription(t.getDescription());
			transactionResponse.setUserCategory(userCategoryMapper.mapToResponse(t.getUserCategoryDto()));
			transactionResponse.setType(t.getType());

			return transactionResponse;
		}).collect(Collectors.toList());
	}

}
