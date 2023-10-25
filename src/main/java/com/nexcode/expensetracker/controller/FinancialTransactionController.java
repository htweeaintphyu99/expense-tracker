package com.nexcode.expensetracker.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexcode.expensetracker.mapper.FinancialTransactionMapper;
import com.nexcode.expensetracker.model.dto.FinancialTransactionDto;
import com.nexcode.expensetracker.model.entity.Type;
import com.nexcode.expensetracker.model.exception.BadRequestException;
import com.nexcode.expensetracker.model.request.FinancialTransactionRequest;
import com.nexcode.expensetracker.model.response.ApiResponse;
import com.nexcode.expensetracker.model.response.FinancialTransactionResponse;
import com.nexcode.expensetracker.security.CurrentUser;
import com.nexcode.expensetracker.security.UserPrincipal;
import com.nexcode.expensetracker.service.FinancialTransactionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transactions")
public class FinancialTransactionController {

	private final FinancialTransactionService transactionService;
	private final FinancialTransactionMapper transactionMapper;

	@PostMapping
	public ApiResponse createFinancialTransaction(@Valid @RequestBody FinancialTransactionRequest transactionRequest,
			@CurrentUser UserPrincipal currentUser) {

		FinancialTransactionDto createdtransaction = transactionService.createFinancialTransaction(currentUser.getId(),
				transactionRequest);
		if (createdtransaction != null) {
			return new ApiResponse(true, "Transaction creation successful");
		}
		throw new BadRequestException("An error occurred in transaction creation!");

	}

	@PutMapping("/{id}")
	public ApiResponse updateFinancialTransaction(@Valid @PathVariable Long id, @CurrentUser UserPrincipal currentUser,
			@Valid @RequestBody FinancialTransactionRequest transactionRequest) {

		FinancialTransactionDto updatedtransaction = transactionService.updateFinancialTransaction(id,
				currentUser.getId(), transactionRequest);
		if (updatedtransaction != null) {
			return new ApiResponse(true, "Transaction updation successful");
		}
		throw new BadRequestException("An error occurred in transaction updation!");

	}

	@DeleteMapping("/{id}")
	public ApiResponse deleteFinancialTransactionById(@PathVariable Long id, @CurrentUser UserPrincipal currentUser) {

		transactionService.deleteFinancialTransactionById(id, currentUser.getId());
		return new ApiResponse(true, "Transaction deletion successful");

	}

	@GetMapping
	public List<FinancialTransactionResponse> getTransactions(@CurrentUser UserPrincipal currentUser,
			@RequestParam(name = "startDate", required = false) String startDate,
			@RequestParam(name = "endDate", required = false) String endDate,
			@RequestParam(name = "selectedMonth", required = false) String selectedMonth,
			@RequestParam(name = "filter", required = false) String filter) {

		List<FinancialTransactionDto> transactionDtos = null;
		
	    if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) { 
	        if (filter.equals("EXPENSE")) {
				transactionDtos = transactionService.getFinancialTransactionsByFilterAndDateRange(currentUser.getId(),
						startDate, endDate, Type.EXPENSE);

			} else if (filter.equals("INCOME")) {

				transactionDtos = transactionService.getFinancialTransactionsByFilterAndDateRange(currentUser.getId(),
						startDate, endDate, Type.INCOME);

			} else if (filter.equals("ALL")) {

				transactionDtos = transactionService.getFinancialTransactionsByFilterAndDateRange(currentUser.getId(),
						startDate, endDate, null);

			}
	    }
	    
	    if (selectedMonth != null && !selectedMonth.isEmpty()) {
			transactionDtos = transactionService.getFinancialTransactionsByMonth(currentUser.getId(), selectedMonth);
	    }
		return transactionMapper.mapToRepsonse(transactionDtos);		
	}

}
