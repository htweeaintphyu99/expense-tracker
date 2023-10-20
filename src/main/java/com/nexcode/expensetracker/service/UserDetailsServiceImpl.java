package com.nexcode.expensetracker.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nexcode.expensetracker.model.entity.User;
import com.nexcode.expensetracker.repository.UserRepository;
import com.nexcode.expensetracker.security.UserPrincipal;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(email).get();
		if (user != null) {
			UserDetails userDetails = UserPrincipal.build(user);
			return userDetails;
		}
		throw new UsernameNotFoundException("User not found with email " + email);
	}

}
