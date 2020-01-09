package com.santander.userregistration.service;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.santander.userregistration.dto.UserRegistrationRequestDto;
import com.santander.userregistration.dto.UserRegistrationResponseDto;
import com.santander.userregistration.model.UserRegistration;
import com.santander.userregistration.repository.UserRegistrationRepository;
import com.santander.userregistration.serviceImpl.UserRegistrationServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
class TestUserRegistrationServiceTest {
	
	private static final String EMAIL = "test@test.com";

	@InjectMocks
	private UserRegistrationServiceImpl userRegistrationService;
	
	@Mock
	private UserRegistrationRepository userRegistrationRepository;
	
	@Test
	void testUserRegister() {
		
		UserRegistrationRequestDto userRegistrationRequestDto=new UserRegistrationRequestDto();
		userRegistrationRequestDto.setEmail(EMAIL);
		
		
		UserRegistration userRegistration=new UserRegistration();
		userRegistration.setEmail(EMAIL);
		userRegistration.setUserId(1L);
		
		UserRegistrationResponseDto userRegistrationResponseDto = new UserRegistrationResponseDto();
		userRegistrationResponseDto.setEmail(EMAIL);
		userRegistrationResponseDto.setUserId(1L);
		userRegistrationResponseDto.setMessage("Registered Successfully");
		
		Mockito.when(userRegistrationRepository.save(Mockito.any(UserRegistration.class))).thenReturn(userRegistration);
		UserRegistrationResponseDto RegistrationResponseDto = userRegistrationService.userRegister(userRegistrationRequestDto);
		assertEquals(userRegistrationResponseDto.getEmail(),RegistrationResponseDto.getEmail());
	}

}
