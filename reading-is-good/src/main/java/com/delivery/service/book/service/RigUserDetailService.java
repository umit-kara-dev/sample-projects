package com.delivery.service.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.delivery.service.book.model.entity.Customer;

@Component
public class RigUserDetailService implements UserDetailsService {

	private static final String DEFAULT_ROLE = "CUSTOMER";
	
	@Autowired
	private CustomerService customerService;

	/**
	 * use customerService for checking email and password. customer table is also used as user table
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer user = customerService.findByEmail(username);
		UserBuilder builder = null;
		if (user != null) {
			builder = org.springframework.security.core.userdetails.User.withUsername(username);
			builder.password(user.getPassword());
			builder.roles(DEFAULT_ROLE);
		} else {
			throw new UsernameNotFoundException("User not found.");
		}

		return builder.build();
	}

}