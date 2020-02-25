package com.santander.userregistration.serviceimpl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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
import com.santander.userregistration.repository.UserRegistrationRepository;
import com.santander.userregistration.service.UserRegistrationService;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

	private static final Logger logger = LoggerFactory.getLogger(UserRegistrationServiceImpl.class);

	@Autowired
	private UserRegistrationRepository userRegistrationRepository;

	@Override
	public UserRegistrationResponseDto userRegister(UserRegistrationRequestDto userRegistrationRequestDto) {

		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setFirstName(userRegistrationRequestDto.getFirstName());
		userRegistration.setLastName(userRegistrationRequestDto.getLastName());
		userRegistration.setEmail(userRegistrationRequestDto.getEmail());
		userRegistration.setPassword(userRegistrationRequestDto.getPassword());
		userRegistration.setDateOfBirth(userRegistrationRequestDto.getDateOfBirth());
		userRegistration.setForgetPasswordA(userRegistrationRequestDto.getForgetPasswordA());
		userRegistration.setForgetPasswordQ(userRegistrationRequestDto.getForgetPasswordQ());

		userRegistration = userRegistrationRepository.save(userRegistration);

		UserRegistrationResponseDto userRegistrationResponseDto = new UserRegistrationResponseDto();
		userRegistrationResponseDto.setEmail(userRegistration.getEmail());
		userRegistrationResponseDto.setUserId(userRegistration.getUserId());
		userRegistrationResponseDto.setMessage("Registered Successfully");

		return userRegistrationResponseDto;
	}

	public ForgetPasswordResponseDto forgetPassword(ForgetPasswordDto email) throws InvalidInputException {
		ForgetPasswordResponseDto state = new ForgetPasswordResponseDto();
		Optional<UserRegistration> userRegistrationRequestDto = Optional
				.ofNullable(userRegistrationRepository.findByEmail(email.getEmail()));

		if (!userRegistrationRequestDto.isPresent()) {
			logger.error("Invalid email");
			throw new InvalidInputException("Please check email");
		}
		UserRegistration userRegistrationData = userRegistrationRequestDto.get();
		state.setQuestion(userRegistrationData.getForgetPasswordQ());
		state.setEmail(userRegistrationData.getEmail());
		return state;
	}

	@Override
	public ForgetPasswordDto resetPassword(String email, ResetPasswordInputDto pwd) {

		UserRegistration userRegistrationRequestDto = userRegistrationRepository.findByEmail(email);
		ForgetPasswordDto forgetPasswordDto = new ForgetPasswordDto();
		forgetPasswordDto.setEmail(userRegistrationRequestDto.getEmail());
		userRegistrationRequestDto.setPassword(pwd.getPwd());
		userRegistrationRepository.save(userRegistrationRequestDto);
		return forgetPasswordDto;
	}

	public LogInDto logIn(LogInInputDto loginDto) {

		LogInDto loginResponse = new LogInDto();
		loginResponse.setMessage("An unknown error occured!");
		UserRegistration userRegistrationData = null;

		Optional<UserRegistration> logIn = Optional
				.ofNullable(userRegistrationRepository.findByEmail(loginDto.getEmail()));

		if (logIn.isPresent()) {
			userRegistrationData = logIn.get();

			if (userRegistrationData.getEmail().equals(loginDto.getEmail())
					&& userRegistrationData.getPassword().equals(loginDto.getPwd())) {
				loginResponse.setUserId(userRegistrationData.getUserId());
				loginResponse.setEmail(userRegistrationData.getEmail());
				loginResponse.setFirstName(userRegistrationData.getFirstName());
				loginResponse.setLastName(userRegistrationData.getLastName());
				loginResponse.setMessage("User is authenticated");
			}

			if (userRegistrationData.getEmail().equals(loginDto.getEmail())
					&& !userRegistrationData.getPassword().equals(loginDto.getPwd())) {
				loginResponse.setMessage("Password incorrect!");
			}
		} else {
			loginResponse.setMessage("Email id doesn't match!");
			logger.info("Email id doesn't match! : {}");
		}

		return loginResponse;
	}

	@Override
	@Cacheable(value = "userRegistrationCache")
	public UserRegistration getUserRegistration(Long userId) {
		UserRegistration userRegistrationDetail = userRegistrationRepository.findByUserId(userId);
		if (userRegistrationDetail == null) {
			logger.error("user not found");
			return null;
		}
		return userRegistrationDetail;
	}

	public ForgetPasswordDto forgetPassword2(ForgetPasswordInputDto forgetPasswordInputDto) {
		UserRegistration userRegistrationRequestDto = userRegistrationRepository
				.findByEmail(forgetPasswordInputDto.getEmail());

		ForgetPasswordDto forgetPasswordDto = new ForgetPasswordDto();
		if (forgetPasswordInputDto.getAnswer().equals(userRegistrationRequestDto.getForgetPasswordA())) {
			forgetPasswordDto.setEmail(userRegistrationRequestDto.getEmail());
		} else {
			forgetPasswordDto.setEmail(null);
		}
		return forgetPasswordDto;
	}

}
