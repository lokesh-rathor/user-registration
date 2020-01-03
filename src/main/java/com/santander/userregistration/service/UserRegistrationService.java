package com.santander.userregistration.service;

import com.santander.userregistration.dto.UserRegistrationRequestDto;
import com.santander.userregistration.dto.UserRegistrationResponseDto;

public interface UserRegistrationService {

	public UserRegistrationResponseDto userRegister( UserRegistrationRequestDto userRegistrationRequestDto ) ;
	
}
