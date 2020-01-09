package com.santander.userregistration.controller;

import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.santander.userregistration.dto.UserRegistrationRequestDto;
import com.santander.userregistration.dto.UserRegistrationResponseDto;
import com.santander.userregistration.repository.UserRegistrationRepository;
import com.santander.userregistration.service.UserRegistrationService;

@RunWith(SpringRunner.class)
@WebMvcTest(RegistrationController.class)
class RegistrationControllerTest {

	private static final String EMAIL = "test@test.com";

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserRegistrationService userRegistrationService;

	@MockBean
	private UserRegistrationRepository userRegistrationRepository;

	@Autowired
	private RegistrationController registrationController;

	@Test
	void testUserRegistration() throws Exception {

		UserRegistrationResponseDto userRegistrationResponseDto = new UserRegistrationResponseDto();
		userRegistrationResponseDto.setEmail(EMAIL);
		userRegistrationResponseDto.setUserId(1L);
		userRegistrationResponseDto.setMessage("Registered Successfully");

		Mockito.when(userRegistrationService.userRegister(Mockito.any(UserRegistrationRequestDto.class)))
				.thenReturn(userRegistrationResponseDto);

		Date d1 = new Date(2017, 12, 12);
		UserRegistrationRequestDto userRegistrationRequestDto = new UserRegistrationRequestDto();
		userRegistrationRequestDto.setEmail("abc@gmail.com");
		userRegistrationRequestDto.setDateOfBirth(d1);
		userRegistrationRequestDto.setFirstName("ddd");
		userRegistrationRequestDto.setLastName("gghh");
		userRegistrationRequestDto.setForgetPasswordA("ghh");
		userRegistrationRequestDto.setForgetPasswordQ("jkbjhbj");
		userRegistrationRequestDto.setPassword("jkehdjkwhe");

		ObjectMapper objectMapper = new ObjectMapper();
		String request = objectMapper.writeValueAsString(userRegistrationRequestDto);

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/users/register")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(request)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

	}

}
