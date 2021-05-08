package com.delivery.service.book.config.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.delivery.service.book.exception.RigException;
import com.delivery.service.book.service.EncryptService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RigPasswordEncoder implements PasswordEncoder {

	@Autowired
	EncryptService encryptService;

	/**
	 * for checking password, rawpassword is encyrpted and hashed same as customer passwords hold in table
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		String hashed = null;
		try {
			hashed = encryptService.encryptAndHash(rawPassword.toString());
		} catch (RigException e) {
			log.error("encode error = {} ", e);
		}
		return hashed;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		String hashed = encode(rawPassword);
		if (hashed != null) {
			return hashed.equals(encodedPassword);
		} else {
			return false;
		}
	}
}