package com.santander.userregistration.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.santander.userregistration.dto.ForgetPasswordDto;
import com.santander.userregistration.dto.LogInInputDto;
import com.santander.userregistration.dto.ResetPasswordInputDto;
import com.santander.userregistration.dto.UserRegistrationRequestDto;
import com.santander.userregistration.dto.UserRegistrationResponseDto;
import com.santander.userregistration.exception.InvalidInputException;
import com.santander.userregistration.model.UserRegistration;
import com.santander.userregistration.service.UserRegistrationService;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class RegistrationController {

	@Autowired
	Environment environment;

	@Autowired
	private UserRegistrationService userRegistrationService;

	private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

	@HystrixCommand(fallbackMethod = "fallback_hello", commandProperties = {

			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100") })
	@GetMapping("/say-hello")
	public String sayHello() throws InterruptedException {

		String port = environment.getProperty("local.server.port");

		return "Hello World...." + port;

	}

	public String fallback_hello() {

		return "Fallback";

	}

	@PostMapping("/register")
	public ResponseEntity<UserRegistrationResponseDto> userRegistration(
		@Valid	@RequestBody UserRegistrationRequestDto userRegistrationRequestDto, Errors errors) throws InvalidInputException {

		if (errors.hasErrors()) {
			throw new InvalidInputException("Invalid Input is missing");
		}

		logger.info("Inside User Registration Method");
		UserRegistrationResponseDto userRegistrationResponseDto = userRegistrationService
				.userRegister(userRegistrationRequestDto);
		logger.info("User Registration successfull");
		return new ResponseEntity<UserRegistrationResponseDto>(userRegistrationResponseDto, HttpStatus.OK);

	}

	@PostMapping("/forgetPassword")
	public Integer ForgetPassword(@RequestBody ForgetPasswordDto email) {

		int state = 0;

		System.out.println("abcddddddd");
		try {
			state = userRegistrationService.forgetPassword(email);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return state;

	}

	@PostMapping(value = "/logIn")
	public Integer logIn(@RequestBody LogInInputDto loginDto) {

		int state = 0;
		try {
			state = userRegistrationService.logIn(loginDto);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return state;

	}

	@PostMapping("/resetPassword/{email}")
	public ResponseEntity<ForgetPasswordDto> resetPassword(@PathVariable("email") String email,
			@RequestBody ResetPasswordInputDto pwd) {

		ForgetPasswordDto forgetPasswordDto = userRegistrationService.resetPassword(email, pwd);
		return new ResponseEntity<>(forgetPasswordDto, HttpStatus.OK);

	}

	@GetMapping("/details/{userId}")
	public ResponseEntity<UserRegistration> getUserDetails(@PathVariable("userId") Long userId)
			throws InvalidInputException {

		if (userId <= 0) {
			throw new InvalidInputException("Invalid Input is missing");
		}

		UserRegistration userRegistration = userRegistrationService.getUserRegistration(userId);
		return new ResponseEntity<>(userRegistration, HttpStatus.OK);
	}

}
