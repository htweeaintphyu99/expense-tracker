package com.nexcode.expensetracker.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {

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
	@Column(name = "type")
	private Type type;
}
