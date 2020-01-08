package com.santander.userregistration.service;

import com.santander.userregistration.dto.ForgetPasswordDto;
import com.santander.userregistration.dto.LogInDto;
import com.santander.userregistration.dto.LogInInputDto;
import com.santander.userregistration.dto.ResetPasswordInputDto;
import com.santander.userregistration.dto.UserRegistrationRequestDto;
import com.santander.userregistration.dto.UserRegistrationResponseDto;

public interface UserRegistrationService {

	public UserRegistrationResponseDto userRegister( UserRegistrationRequestDto userRegistrationRequestDto ) ;
	
	public ForgetPasswordDto forgetPassword(String email);
	
	public ForgetPasswordDto resetPassword(String email,ResetPasswordInputDto pwd);
	public LogInDto logIn(LogInInputDto loginDto);
	
}
