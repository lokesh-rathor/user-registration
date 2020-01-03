package com.santander.userregistration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.santander.userregistration.dto.UserRegistrationRequestDto;
import com.santander.userregistration.dto.UserRegistrationResponseDto;
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
		// Thread.sleep(150);

		String port = environment.getProperty("local.server.port");

		return "Hello World...." + port;

	}

	public String fallback_hello() {

		return "Fallback";

	}

	@PostMapping("/register")
	public ResponseEntity<UserRegistrationResponseDto> userRegistration(
			@RequestBody UserRegistrationRequestDto userRegistrationRequestDto) {

		logger.info("Inside User Registration Method");
		UserRegistrationResponseDto userRegistrationResponseDto = userRegistrationService.userRegister(userRegistrationRequestDto);
		logger.info("User Registration successfull");
		return new ResponseEntity<>(userRegistrationResponseDto, HttpStatus.OK);

	}

}
