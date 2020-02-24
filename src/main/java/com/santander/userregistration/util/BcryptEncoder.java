package com.santander.userregistration.util;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BcryptEncoder implements org.springframework.security.crypto.password.PasswordEncoder{

	private static final int LOG_ROUNDS = 10;
    

	@Override
	public String encode(CharSequence rawPassword) {
		return BCrypt.hashpw((String)rawPassword, BCrypt.gensalt(LOG_ROUNDS));
	} 

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return BCrypt.checkpw((String)rawPassword, encodedPassword);
	}

}