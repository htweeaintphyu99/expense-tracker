package com.nexcode.expensetracker.model.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "financial_transactions")
public class FinancialTransaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "created_date")
	private LocalDate createdDate;
	
	@Column(name = "amount")
	private int amount;
	
	@Column(name = "descripton")
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private Type type;
	
	@ManyToOne
	@JoinColumn(name = "user_category_id")
	private UserCategory userCategory;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
}
