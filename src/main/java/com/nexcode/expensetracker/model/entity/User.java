package com.nexcode.expensetracker.model.entity;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "email", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8_bin")
	private String email;

	@JsonIgnore
	@Column(name = "password")
	private String password;

	@Column(name = "otp")
	private String otp;

	@Column(name = "otp_expiration_time")
	private Instant otpExpirationTime;

	@JsonIgnore
	@Column(name = "verified", columnDefinition = "boolean default false")
	private boolean verified;

	@Column(name = "balance", columnDefinition = "int default 0")
	private int balance;

	@Column(name = "budget", columnDefinition = "int default 0")
	private int budget;

	@ManyToMany
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	List<UserCategory> userCategories;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	List<FinancialTransaction> financialTranscations;
}
