package com.santander.userregistration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.santander.userregistration.dto.ForgetPasswordDto;
import com.santander.userregistration.dto.ForgetPasswordInputDto;
import com.santander.userregistration.dto.ForgetPasswordResponseDto;
import com.santander.userregistration.dto.LogInDto;
import com.santander.userregistration.dto.LogInInputDto;
import com.santander.userregistration.dto.ResetPasswordInputDto;
import com.santander.userregistration.dto.UserRegistrationRequestDto;
import com.santander.userregistration.dto.UserRegistrationResponseDto;
import com.santander.userregistration.exception.InvalidInputException;
import com.santander.userregistration.exception.UserNotFoundException;
import com.santander.userregistration.model.UserRegistration;
import com.santander.userregistration.repository.UserRegistrationRepository;
import com.santander.userregistration.serviceimpl.UserRegistrationServiceImpl;
import com.santander.userregistration.util.BcryptEncoder;
import com.santander.userregistration.util.CaptchaUtil;

@ExtendWith(SpringExtension.class)
class TestUserRegistrationServiceTest {

	private static final String EMAIL = "test@test.com";

	@InjectMocks
	private UserRegistrationServiceImpl userRegistrationService;

	@Mock
	private UserRegistrationRepository userRegistrationRepository;

	@Mock
	private BcryptEncoder bcryptEncoder;

	@Mock
	private CaptchaUtil captchaUtil;

	@Test
	void testUserRegister() {

		UserRegistrationRequestDto userRegistrationRequestDto = new UserRegistrationRequestDto();
		userRegistrationRequestDto.setEmail(EMAIL);

		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setEmail(EMAIL);
		userRegistration.setUserId(1L);
		userRegistration.setPassword("pwd@123");

		UserRegistrationResponseDto userRegistrationResponseDto = new UserRegistrationResponseDto();
		userRegistrationResponseDto.setEmail(EMAIL);
		userRegistrationResponseDto.setUserId(1L);
		userRegistrationResponseDto.setMessage("Registered Successfully");

		Mockito.when(userRegistrationRepository.save(Mockito.any(UserRegistration.class))).thenReturn(userRegistration);

		Mockito.when(bcryptEncoder.encode(Mockito.any(String.class))).thenReturn("xORHBHnXlWwlvyKi3Oq9vYfajg");

		UserRegistrationResponseDto RegistrationResponseDto = userRegistrationService
				.userRegister(userRegistrationRequestDto);
		assertEquals(userRegistrationResponseDto.getEmail(), RegistrationResponseDto.getEmail());
	}

	@Test
	void testForgetPassword() {
		ForgetPasswordDto forgetPasswordDto = new ForgetPasswordDto();
		forgetPasswordDto.setEmail(EMAIL);

		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setEmail(EMAIL);
		userRegistration.setForgetPasswordQ("school");

		ForgetPasswordResponseDto expectedForgetPasswordResponse = new ForgetPasswordResponseDto();
		expectedForgetPasswordResponse.setEmail(EMAIL);

		Mockito.when(userRegistrationRepository.findByEmail(Mockito.any(String.class))).thenReturn(userRegistration);

		ForgetPasswordResponseDto forgetPasswordResponseDto = userRegistrationService.forgetPassword(forgetPasswordDto);

		assertEquals(expectedForgetPasswordResponse.getEmail(), forgetPasswordResponseDto.getEmail());
	}

	@Test
	void testForgetPasswordWithWrongEmail() {
		ForgetPasswordDto forgetPasswordDto = new ForgetPasswordDto();
		forgetPasswordDto.setEmail(EMAIL);

		Mockito.when(userRegistrationRepository.findByEmail(Mockito.any(String.class))).thenReturn(null);

		Assertions.assertThrows(UserNotFoundException.class,
				() -> userRegistrationService.forgetPassword(forgetPasswordDto));
	}

	@Test
	void testResetPassword() {
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setEmail(EMAIL);
		userRegistration.setUserId(1L);
		userRegistration.setPassword("pwd@123");

		ResetPasswordInputDto pwd = new ResetPasswordInputDto();
		pwd.setPwd(EMAIL);

		Mockito.when(userRegistrationRepository.findByEmail(Mockito.any(String.class))).thenReturn(userRegistration);

		ForgetPasswordDto forgetPasswordDto = new ForgetPasswordDto();
		forgetPasswordDto.setEmail(userRegistration.getEmail());

		Mockito.when(userRegistrationRepository.save(Mockito.any(UserRegistration.class))).thenReturn(userRegistration);

		ForgetPasswordDto forgetPasswordResponse = userRegistrationService.resetPassword(EMAIL, pwd);

		assertEquals(EMAIL, forgetPasswordResponse.getEmail());
	}

	@Test
	void testLogInSuccess() {
		LogInInputDto loginRequest = new LogInInputDto();
		loginRequest.setRecaptchaResponse("some dummy string");
		loginRequest.setEmail(EMAIL);
		loginRequest.setPwd("pwd@123");

		Mockito.when(captchaUtil.verify(Mockito.anyString())).thenReturn(true);

		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setEmail(EMAIL);
		userRegistration.setUserId(1L);
		userRegistration.setPassword("pwd@123");

		Mockito.when(userRegistrationRepository.findByEmail(Mockito.any(String.class))).thenReturn(userRegistration);

		Mockito.when(bcryptEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true);

		LogInDto loginResponse = userRegistrationService.logIn(loginRequest);

		assertEquals("User is authenticated", loginResponse.getMessage());
	}

	@Test
	void testLogInFailedPasswordIncorrect() {
		LogInInputDto loginRequest = new LogInInputDto();
		loginRequest.setRecaptchaResponse("some dummy string");
		loginRequest.setEmail(EMAIL);
		loginRequest.setPwd("pwd@1235");

		Mockito.when(captchaUtil.verify(Mockito.anyString())).thenReturn(true);

		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setEmail(EMAIL);
		userRegistration.setUserId(1L);
		userRegistration.setPassword("pwd@123");

		Mockito.when(userRegistrationRepository.findByEmail(Mockito.any(String.class))).thenReturn(userRegistration);

		Mockito.when(bcryptEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(false);

		LogInDto loginResponse = userRegistrationService.logIn(loginRequest);

		assertEquals("Password incorrect!", loginResponse.getMessage());
	}

	@Test
	void testLogInFailedEmailNotMatch() {
		LogInInputDto loginRequest = new LogInInputDto();
		loginRequest.setRecaptchaResponse("some dummy string");
		loginRequest.setEmail(EMAIL);
		loginRequest.setPwd("pwd@1235");

		Mockito.when(captchaUtil.verify(Mockito.anyString())).thenReturn(true);

		Mockito.when(userRegistrationRepository.findByEmail(Mockito.any(String.class))).thenReturn(null);

		LogInDto loginResponse = userRegistrationService.logIn(loginRequest);

		assertEquals("Email id doesn't match!", loginResponse.getMessage());
	}

	@Test
	void testInvalidcaptcha() {
		LogInInputDto loginRequest = new LogInInputDto();
		loginRequest.setRecaptchaResponse("some dummy string");
		loginRequest.setEmail(EMAIL);
		loginRequest.setPwd("pwd@1235");

		Mockito.when(captchaUtil.verify(Mockito.anyString())).thenReturn(false);

		Assertions.assertThrows(InvalidInputException.class, () -> userRegistrationService.logIn(loginRequest));
	}
	
	@Test
	void testGetUserRegistration() {
		
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setEmail(EMAIL);
		
		Mockito.when(userRegistrationRepository.findByUserId(Mockito.anyLong())).thenReturn(userRegistration);
		//assertNotNull(userRegistrationService.getUserRegistration(1l));
		assertEquals(userRegistration.getEmail(),userRegistrationService.getUserRegistration(1l).getEmail());
	}
	
	@Test
	void testGetUserUserNotFoundException() {
		Mockito.when(userRegistrationRepository.findByUserId(Mockito.anyLong())).thenReturn(null);
		Assertions.assertThrows(UserNotFoundException.class, () -> userRegistrationService.getUserRegistration(1l));
	}

	
	@Test
	void testForgotPassword2() {
		
		ForgetPasswordInputDto forgetPasswordInputDto = new ForgetPasswordInputDto();
		forgetPasswordInputDto.setAnswer("Answer");
		forgetPasswordInputDto.setEmail(EMAIL);
				
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setForgetPasswordA("Answer");
		userRegistration.setEmail(EMAIL);
		
		Mockito.when(userRegistrationRepository.findByEmail(Mockito.anyString())).thenReturn(userRegistration);
		 
		assertEquals(EMAIL, userRegistrationService.forgetPassword2(forgetPasswordInputDto).getEmail());
	}
	
	@Test
	void testForgotPassword2NotEqual() {
		ForgetPasswordInputDto forgetPasswordInputDto = new ForgetPasswordInputDto();
		forgetPasswordInputDto.setAnswer("Answer");
		forgetPasswordInputDto.setEmail(EMAIL);
				
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setForgetPasswordA("Answers");
		userRegistration.setEmail(EMAIL);
		
		Mockito.when(userRegistrationRepository.findByEmail(Mockito.anyString())).thenReturn(userRegistration);
		 
		assertEquals(null, userRegistrationService.forgetPassword2(forgetPasswordInputDto).getEmail());
	}
	
}
