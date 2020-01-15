package com.santander.userregistration.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.santander.userregistration.dto.LogInInputDto;
import com.santander.userregistration.dto.UserRegistrationRequestDto;
import com.santander.userregistration.dto.UserRegistrationResponseDto;
import com.santander.userregistration.repository.UserRegistrationRepository;
import com.santander.userregistration.service.UserRegistrationService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RegistrationController.class)
class RegistrationControllerTest {

	private static final String EMAIL = "test@test.com";

	private static final String pwd = "12345678";

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserRegistrationService userRegistrationService;

	@MockBean
	private UserRegistrationRepository userRegistrationRepository;

	@Test // (expected = InvalidInputException.class)
	public void testUserRegistrationError() throws Exception {
		@SuppressWarnings("deprecation")
		Date d1 = new Date(2017, 12, 12);
		UserRegistrationRequestDto userRegistrationRequestDto = new UserRegistrationRequestDto();
		userRegistrationRequestDto.setEmail("abc@gsdf.com");
		userRegistrationRequestDto.setDateOfBirth(d1);
		userRegistrationRequestDto.setFirstName("ddd");
		userRegistrationRequestDto.setLastName("gghh");
		userRegistrationRequestDto.setForgetPasswordA("ghh");
		userRegistrationRequestDto.setForgetPasswordQ("jkbjhbj");
		userRegistrationRequestDto.setPassword("jke");

		String request = this.mapper(userRegistrationRequestDto);

		mvc.perform(MockMvcRequestBuilders.post("/users/register").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(request)).andExpect(status().isBadRequest());
	}

	@Test
	void testUserRegistration() throws Exception {

		UserRegistrationResponseDto userRegistrationResponseDto = new UserRegistrationResponseDto();
		userRegistrationResponseDto.setEmail(EMAIL);
		userRegistrationResponseDto.setUserId(1L);
		userRegistrationResponseDto.setMessage("Registered Successfully");

		Mockito.when(userRegistrationService.userRegister(Mockito.any(UserRegistrationRequestDto.class)))
				.thenReturn(userRegistrationResponseDto);

		@SuppressWarnings("deprecation")
		Date d1 = new Date(2017, 12, 12);
		UserRegistrationRequestDto userRegistrationRequestDto = new UserRegistrationRequestDto();
		userRegistrationRequestDto.setEmail("abc@gmail.com");
		userRegistrationRequestDto.setDateOfBirth(d1);
		userRegistrationRequestDto.setFirstName("ddd");
		userRegistrationRequestDto.setLastName("gghh");
		userRegistrationRequestDto.setForgetPasswordA("ghh");
		userRegistrationRequestDto.setForgetPasswordQ("jkbjhbj");
		userRegistrationRequestDto.setPassword("jkehdjkwhe");

		String request = this.mapper(userRegistrationRequestDto);

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/users/register")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(request)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

	}

	@Test
	void testUserLogIn() throws Exception {


		LogInInputDto log = new LogInInputDto();
		log.setEmail(EMAIL);
		log.setPwd(pwd);
		String request = this.mapper2(log);

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/users/logIn")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(request)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

	}

	private String mapper(UserRegistrationRequestDto userRegistrationRequestDto) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		String request = objectMapper.writeValueAsString(userRegistrationRequestDto);
		return request;
	}

	private String mapper2(LogInInputDto userRegistrationRequestDto) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		String request = objectMapper.writeValueAsString(userRegistrationRequestDto);
		return request;
	}

}
