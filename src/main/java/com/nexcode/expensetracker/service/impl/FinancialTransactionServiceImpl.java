package com.nexcode.expensetracker.service.impl;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexcode.expensetracker.mapper.FinancialTransactionMapper;
import com.nexcode.expensetracker.model.dto.FinancialTransactionDto;
import com.nexcode.expensetracker.model.entity.FinancialTransaction;
import com.nexcode.expensetracker.model.entity.Type;
import com.nexcode.expensetracker.model.entity.User;
import com.nexcode.expensetracker.model.entity.UserCategory;
import com.nexcode.expensetracker.model.exception.NotFoundException;
import com.nexcode.expensetracker.model.request.FinancialTransactionRequest;
import com.nexcode.expensetracker.repository.FinancialTransactionRepository;
import com.nexcode.expensetracker.repository.UserCategoryRepository;
import com.nexcode.expensetracker.repository.UserRepository;
import com.nexcode.expensetracker.service.FinancialTransactionService;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class FinancialTransactionServiceImpl implements FinancialTransactionService {

	private final UserRepository userRepository;
	private final UserCategoryRepository userCategoryRepository;
	private final FinancialTransactionRepository transactionRepository;
	private final FinancialTransactionMapper transactionMapper;

	@Override
	public FinancialTransactionDto createFinancialTransaction(Long userId,
			FinancialTransactionRequest transactionRequest) {

		User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User is not found!"));

		UserCategory userCategory = userCategoryRepository.findById(transactionRequest.getUserCategoryId())
				.orElseThrow(() -> new NotFoundException("User category is not found!"));

		FinancialTransaction transaction = new FinancialTransaction();
		transaction.setAmount(transactionRequest.getAmount());
		transaction.setCreatedDate(transactionRequest.getCreatedDate());
		transaction.setDescription(transactionRequest.getDescription());
		transaction.setType(transactionRequest.getType());
		transaction.setUser(user);
		transaction.setUserCategory(userCategory);

		if (transactionRequest.getType() == Type.EXPENSE) {
			user.setBalance(user.getBalance() - transactionRequest.getAmount());

		} else {
			user.setBalance(user.getBalance() + transactionRequest.getAmount());
		}

		FinancialTransaction createdTransaction = transactionRepository.save(transaction);

		return transactionMapper.mapToDto(createdTransaction);
	}

	@Override
	public FinancialTransactionDto updateFinancialTransaction(Long id, Long userId,
			FinancialTransactionRequest transactionRequest) {

		FinancialTransaction transaction = transactionRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Transaction with id " + id + " is not found!"));

		User user = userRepository.getById(userId);
		
		UserCategory userCategory = userCategoryRepository.findById(transactionRequest.getUserCategoryId())
				.orElseThrow(() -> new NotFoundException("User category is not found!"));

		
		if (transaction.getType() == Type.EXPENSE && transactionRequest.getType() == Type.EXPENSE) {
			user.setBalance(user.getBalance() + transaction.getAmount() - transactionRequest.getAmount());

		} else if (transaction.getType() == Type.EXPENSE && transactionRequest.getType() == Type.INCOME) {
			user.setBalance(user.getBalance() + transaction.getAmount() + transactionRequest.getAmount());

		} else if (transaction.getType() == Type.INCOME && transactionRequest.getType() == Type.EXPENSE) {
			user.setBalance(user.getBalance() - transaction.getAmount() - transactionRequest.getAmount());

		} else if (transaction.getType() == Type.INCOME && transactionRequest.getType() == Type.INCOME) {
			user.setBalance(user.getBalance() - transaction.getAmount() + transactionRequest.getAmount());

		}

		transaction.setAmount(transactionRequest.getAmount());
		transaction.setCreatedDate(transactionRequest.getCreatedDate());
		transaction.setDescription(transactionRequest.getDescription());
		transaction.setType(transactionRequest.getType());
		transaction.setUserCategory(userCategory);

		userRepository.save(user);

		FinancialTransaction updatedTransaction = transactionRepository.save(transaction);

		return transactionMapper.mapToDto(updatedTransaction);
	}


	@Override
	public void deleteFinancialTransactionById(Long id, Long userId) {

		FinancialTransaction transaction = transactionRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Transaction with id " + id + " is not found!"));

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException("User with id " + userId + " is not found!"));

		if(transaction.getType() == Type.EXPENSE) {
			user.setBalance(user.getBalance() + transaction.getAmount());
		}
		else {
			user.setBalance(user.getBalance() - transaction.getAmount());
		}
		userRepository.save(user);
		transactionRepository.delete(transaction);
	}


	@Override
	public List<FinancialTransactionDto> getFinancialTransactionsByFilterAndDateRange(Long userId,
			String startDate, String endDate, Type type) {
 

        LocalDate startDateInFormat = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDateInFormat = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
		Optional<Type> optionalType = Optional.ofNullable(type);

		List<FinancialTransaction> transactions = transactionRepository
				.findByUserIdAndFilterAndCreatedDateBetween(userId, startDateInFormat, endDateInFormat, optionalType);

		return transactionMapper.mapToDto(transactions);
	}

	@Override
	public List<FinancialTransactionDto> getFinancialTransactionsByMonth(Long userId, String selectedMonth) {

        YearMonth selectedMonthInFormat = YearMonth.parse(selectedMonth, DateTimeFormatter.ofPattern("yyyy-MM"));

		LocalDate startDate = selectedMonthInFormat.atDay(1);
		LocalDate endDate = selectedMonthInFormat.atEndOfMonth();

		Optional<Type> optionalType = Optional.ofNullable(Type.EXPENSE);

		List<FinancialTransaction> transactions = transactionRepository
				.findByUserIdAndFilterAndCreatedDateBetween(userId, startDate, endDate, optionalType);

		return transactionMapper.mapToDto(transactions);
	}

}
