package com.santander.userregistration.service;

import com.santander.userregistration.dto.ForgetPasswordDto;
import com.santander.userregistration.dto.ForgetPasswordInputDto;
import com.santander.userregistration.dto.ForgetPasswordResponseDto;
import com.santander.userregistration.dto.LogInDto;
import com.santander.userregistration.dto.LogInInputDto;
import com.santander.userregistration.dto.ResetPasswordInputDto;
import com.santander.userregistration.dto.UserRegistrationRequestDto;
import com.santander.userregistration.dto.UserRegistrationResponseDto;
import com.santander.userregistration.exception.InvalidInputException;
import com.santander.userregistration.model.UserRegistration;

public interface UserRegistrationService {

	public UserRegistrationResponseDto userRegister(UserRegistrationRequestDto userRegistrationRequestDto);

	public ForgetPasswordResponseDto forgetPassword(ForgetPasswordDto email) throws InvalidInputException;

	public ForgetPasswordDto resetPassword(String email, ResetPasswordInputDto pwd);

	public LogInDto logIn(LogInInputDto loginDto);

	public UserRegistration getUserRegistration(Long userId);

	public ForgetPasswordDto forgetPassword2(ForgetPasswordInputDto forgetPasswordInputDto);

}
