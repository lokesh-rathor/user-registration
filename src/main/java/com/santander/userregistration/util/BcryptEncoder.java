package com.santander.userregistration.util;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BcryptEncoder implements org.springframework.security.crypto.password.PasswordEncoder{

	private static final int logRounds = 10;
    

	@Override
	public String encode(CharSequence rawPassword) {
		String hash = BCrypt.hashpw((String)rawPassword, BCrypt.gensalt(logRounds));
		return hash;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return BCrypt.checkpw((String)rawPassword, encodedPassword);
	}

}