package com.santander.userregistration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.santander.userregistration.dto.UserRegistrationRequestDto;
import com.santander.userregistration.dto.UserRegistrationResponseDto;
import com.santander.userregistration.model.UserRegistration;
import com.santander.userregistration.repository.UserRegistrationRepository;
import com.santander.userregistration.serviceImpl.UserRegistrationServiceImpl;
import com.santander.userregistration.util.BcryptEncoder;

@ExtendWith(SpringExtension.class)
class TestUserRegistrationServiceTest {

	private static final String EMAIL = "test@test.com";

	@InjectMocks
	private UserRegistrationServiceImpl userRegistrationService;

	@Mock
	private UserRegistrationRepository userRegistrationRepository;
	
	@Mock
	private BcryptEncoder bcryptEncoder;

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

}
