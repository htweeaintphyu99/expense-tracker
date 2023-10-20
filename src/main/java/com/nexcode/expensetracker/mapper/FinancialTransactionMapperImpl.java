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

		return transactions.stream().map(t -> {

			FinancialTransactionDto transactionDto = new FinancialTransactionDto();
			transactionDto.setId(t.getId());
			transactionDto.setAmount(t.getAmount());
			transactionDto.setCreatedDate(t.getCreatedDate());
			transactionDto.setDescription(t.getDescription());
			transactionDto.setUserCategoryDto(userCategoryMapper.mapToDto(t.getUserCategory()));
			transactionDto.setType(t.getType());

			return transactionDto;
		}).collect(Collectors.toList());
	}

	@Override
	public List<FinancialTransactionResponse> mapToRepsonse(List<FinancialTransactionDto> transactionDtos) {
		
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
