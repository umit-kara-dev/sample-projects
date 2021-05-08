package com.delivery.service.book.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import com.delivery.service.book.config.security.RigPasswordEncoder;
import com.delivery.service.book.service.RigUserDetailService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private RigUserDetailService rigUserDetailService;

	@Autowired
	private RigPasswordEncoder passwordEncoder;

	@Bean
	public UserDetailsService userDetailsService() {
		return rigUserDetailService;
	};

	@Bean
	public RigPasswordEncoder passwordEncoder() {
		return passwordEncoder;
	};

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}

	/**
	 * permit customer/ endpoint for all
	 * 
	 * authenticate order endpoint and basic authentication is required
	 * 
	 * 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().headers()
				.addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy", "script-src 'self'")).and()
				.authorizeRequests().mvcMatchers(HttpMethod.POST, "/customer/**").permitAll()
				.mvcMatchers(HttpMethod.POST, "/order/**").authenticated().and().csrf().disable().formLogin().disable()
				.headers().frameOptions().disable();
	}
}