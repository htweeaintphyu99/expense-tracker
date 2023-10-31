package com.nexcode.expensetracker;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexcode.expensetracker.model.entity.Category;
import com.nexcode.expensetracker.model.entity.Icon;
import com.nexcode.expensetracker.model.entity.Role;
import com.nexcode.expensetracker.model.entity.RoleName;
import com.nexcode.expensetracker.model.entity.UserCategory;
import com.nexcode.expensetracker.repository.CategoryRepository;
import com.nexcode.expensetracker.repository.IconRepository;
import com.nexcode.expensetracker.repository.RoleRepository;
import com.nexcode.expensetracker.repository.UserCategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MyApplicationRunner implements CommandLineRunner {

	private final UserCategoryRepository userCategoryRepository;
	private final IconRepository iconRepository;
	private final CategoryRepository categoryRepository;
	private final RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {

		if(roleRepository.findAll().isEmpty()) {
			
			List<Role> roles = List.of(new Role(RoleName.ROLE_ADMIN), new Role(RoleName.ROLE_USER));
			roleRepository.saveAll(roles);
		}
		
		addIcons();

		addCategories();

		Optional<UserCategory> optionalCategory = userCategoryRepository.findByNameIgnoreCaseAndUser("Others", null);
		if (optionalCategory.isEmpty()) {
			UserCategory systemCategory = new UserCategory();
			systemCategory.setName("Others");
			systemCategory.setIconName("help");
			systemCategory.setIconBgColor("#FF1F2B");
			userCategoryRepository.save(systemCategory);

		}

	}

	public void addIcons() throws IOException {

		if (iconRepository.findAll().isEmpty()) {

			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<Icon>> typeReference = new TypeReference<List<Icon>>() {
			};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/json/icons.json");
			try {
				List<Icon> icons = mapper.readValue(inputStream, typeReference);
				iconRepository.saveAll(icons);

			} catch (IOException e) {
				throw new IOException("IO Exception occurred!");
			}
		}

	}

	public void addCategories() throws IOException {
		if (categoryRepository.findAll().isEmpty()) {

			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<Category>> typeReference = new TypeReference<List<Category>>() {
			};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/json/categories.json");
			try {
				List<Category> categories = mapper.readValue(inputStream, typeReference);
				categoryRepository.saveAll(categories);

			} catch (IOException e) {
				throw new IOException("IO Exception occurred!");
			}
		}
	}
}
