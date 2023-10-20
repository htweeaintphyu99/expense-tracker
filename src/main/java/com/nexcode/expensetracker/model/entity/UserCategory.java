package com.nexcode.expensetracker.model.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_category")
public class UserCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "icon_name", nullable = false)
	private String iconName;
	
	@Column(name = "icon_bg_color", nullable = false)
	private String iconBgColor;
	
	@Enumerated(EnumType.STRING)
	private Type type;
	
	@ManyToOne
	@JoinColumn(name = "fk_user_id")
	private User user;
	
	@OneToMany(mappedBy = "userCategory")
	List<FinancialTransaction> financialTranscations;
}
