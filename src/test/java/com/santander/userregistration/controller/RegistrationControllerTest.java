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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.santander.userregistration.dto.ForgetPasswordDto;
import com.santander.userregistration.dto.ForgetPasswordInputDto;
import com.santander.userregistration.dto.LogInInputDto;
import com.santander.userregistration.dto.ResetPasswordInputDto;
import com.santander.userregistration.dto.UserRegistrationRequestDto;
import com.santander.userregistration.dto.UserRegistrationResponseDto;
import com.santander.userregistration.model.UserRegistration;
import com.santander.userregistration.service.UserRegistrationService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RegistrationController.class)
class RegistrationControllerTest {

	private static final String EMAIL = "test@test.com";

	private static final String PWD = "12345678";

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserRegistrationService userRegistrationService;

	@Test
	public void testUserRegistrationError() throws Exception {
		@SuppressWarnings("deprecation")
		Date d1 = new Date(2017, 12, 12);
		UserRegistrationRequestDto userRegistrationRequestDto = new UserRegistrationRequestDto();
		userRegistrationRequestDto.setEmail("alex@gmail.com");
		userRegistrationRequestDto.setDateOfBirth(d1);
		userRegistrationRequestDto.setFirstName("alex");
		userRegistrationRequestDto.setLastName("kumar");
		userRegistrationRequestDto.setForgetPasswordA("harward");
		userRegistrationRequestDto.setForgetPasswordQ("school");
		userRegistrationRequestDto.setPassword("Ame");

		mvc.perform(MockMvcRequestBuilders.post("/users/register").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(this.objectToJsonMapper(userRegistrationRequestDto))).andExpect(status().isBadRequest());
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
		userRegistrationRequestDto.setEmail("alex@gmail.com");
		userRegistrationRequestDto.setDateOfBirth(d1);
		userRegistrationRequestDto.setFirstName("alex");
		userRegistrationRequestDto.setLastName("kumar");
		userRegistrationRequestDto.setForgetPasswordA("harward");
		userRegistrationRequestDto.setForgetPasswordQ("school");
		userRegistrationRequestDto.setPassword("Password@123");

		String request = this.objectToJsonMapper(userRegistrationRequestDto);

		mvc.perform(MockMvcRequestBuilders.post("/users/register").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(request)).andExpect(status().isOk());
	}

	@Test
	void testGetUserDetails() throws Exception {

		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setEmail(EMAIL);
		userRegistration.setUserId(1L);

		Mockito.when(userRegistrationService.getUserRegistration(Mockito.any(Long.class))).thenReturn(userRegistration);

		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.get("/users/details/1").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	void testGetUserDetailsError() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/users/details/-1").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testUserLogIn() throws Exception {

		LogInInputDto log = new LogInInputDto();
		log.setEmail(EMAIL);
		log.setPwd(PWD);
		String request = this.objectToJsonMapper(log);

		mvc.perform(MockMvcRequestBuilders.post("/users/logIn").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(request)).andExpect(status().isOk());
	}
	
	@Test
	void testResetPassword() throws Exception {
		
		ResetPasswordInputDto resetPasswordDto = new ResetPasswordInputDto();
		resetPasswordDto.setPwd(PWD);
		String request = this.objectToJsonMapper(resetPasswordDto);
		
		mvc.perform(MockMvcRequestBuilders.post("/users/resetPassword/"+EMAIL).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(request)).andExpect(status().isOk());
	}
	
	@Test
	void testForgetPassword() throws Exception {
		ForgetPasswordDto forgetPasswordDto = new ForgetPasswordDto();
		forgetPasswordDto.setEmail(EMAIL);
		String request = this.objectToJsonMapper(forgetPasswordDto);
		
		mvc.perform(MockMvcRequestBuilders.post("/users/forgetPassword").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(request)).andExpect(status().isOk());
	}
	
	@Test
	void testForgetPassword2() throws Exception {
		ForgetPasswordInputDto forgetPasswordDto = new ForgetPasswordInputDto();
		forgetPasswordDto.setEmail(EMAIL);
		String request = this.objectToJsonMapper(forgetPasswordDto);
		
		mvc.perform(MockMvcRequestBuilders.post("/users/forgetPassword/reset").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(request)).andExpect(status().isOk());
	}

	private String objectToJsonMapper(Object request) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(request);
	}
 
}
