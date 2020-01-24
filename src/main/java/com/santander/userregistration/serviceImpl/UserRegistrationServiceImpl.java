package com.santander.userregistration.serviceImpl;

import java.util.NoSuchElementException;
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
import com.santander.userregistration.exception.UserNotFoundException;
import com.santander.userregistration.model.UserRegistration;
import com.santander.userregistration.repository.UserRegistrationRepository;
import com.santander.userregistration.service.UserRegistrationService;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

	private static final Logger logger = LoggerFactory.getLogger(UserRegistrationServiceImpl.class);
	
	@Autowired
	private UserRegistrationRepository userRegistrationRepository;

	@Override
	public UserRegistrationResponseDto userRegister(final UserRegistrationRequestDto userRegistrationRequestDto) {

		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setFirstName(userRegistrationRequestDto.getFirstName());
		userRegistration.setLastName(userRegistrationRequestDto.getLastName());
		userRegistration.setEmail(userRegistrationRequestDto.getEmail().toLowerCase());
		userRegistration.setPassword(userRegistrationRequestDto.getPassword());
		userRegistration.setDateOfBirth(userRegistrationRequestDto.getDateOfBirth());
		userRegistration.setForgetPasswordA(userRegistrationRequestDto.getForgetPasswordA());
		userRegistration.setForgetPasswordQ(userRegistrationRequestDto.getForgetPasswordQ());

		userRegistration = userRegistrationRepository.save(userRegistration);

		final UserRegistrationResponseDto userRegistrationResponseDto = new UserRegistrationResponseDto();
		userRegistrationResponseDto.setEmail(userRegistration.getEmail().toLowerCase());
		userRegistrationResponseDto.setUserId(userRegistration.getUserId());
		userRegistrationResponseDto.setMessage("Registered Successfully");

		return userRegistrationResponseDto;
	}

	@Override
	public ForgetPasswordResponseDto forgetPassword(final ForgetPasswordDto email) {

		ForgetPasswordResponseDto state = new ForgetPasswordResponseDto();
		final UserRegistration userRegistrationRequestDto = userRegistrationRepository.findByEmail(email.getEmail());
		if (userRegistrationRequestDto.getEmail().equals(email.getEmail())) {
			state.setQuestion(userRegistrationRequestDto.getForgetPasswordQ());
			state.setEmail(userRegistrationRequestDto.getEmail());
		} else {
			state = null;
		}
		return state;
	}

	@Override
	public ForgetPasswordDto resetPassword(final String email, final ResetPasswordInputDto pwd) {

		final UserRegistration userRegistrationRequestDto = userRegistrationRepository.findByEmail(email);
		final ForgetPasswordDto forgetPasswordDto = new ForgetPasswordDto();
		forgetPasswordDto.setEmail(userRegistrationRequestDto.getEmail());
		userRegistrationRequestDto.setPassword(pwd.getPwd());
		userRegistrationRepository.save(userRegistrationRequestDto);
		return forgetPasswordDto;
	}

	@Override
	public LogInDto logIn(final LogInInputDto loginDto) throws NoSuchElementException, InvalidInputException {
		
		final LogInDto loginResponse = new LogInDto();
		loginResponse.setMessage("An unknown error occured!");
		UserRegistration userRegistrationData = null;
		
		final Optional<UserRegistration> logIn = Optional.ofNullable(userRegistrationRepository.findByEmail(loginDto.getEmail().toLowerCase()));
		
		
		if(logIn.isPresent()) {
	      userRegistrationData = logIn.get();
	      
	      if(userRegistrationData.getEmail().equals(loginDto.getEmail()) && userRegistrationData.getPassword().equals(loginDto.getPwd()))
			{
				loginResponse.setUserId(userRegistrationData.getUserId());
				loginResponse.setEmail(userRegistrationData.getEmail().toLowerCase());
				loginResponse.setFirstName(userRegistrationData.getFirstName());
				loginResponse.setLastName(userRegistrationData.getLastName());
				loginResponse.setMessage("User is authenticated");
			}
		
		   if(userRegistrationData.getEmail().equals(loginDto.getEmail()) && !userRegistrationData.getPassword().equals(loginDto.getPwd())) {
				loginResponse.setMessage("Password incorrect!");
		   }
		}
		else {
			loginResponse.setMessage("Email id doesn't match!");
			//throw new InvalidInputException("Email id doesn't match!");
			logger.info("Email id doesn't match! : {}");
		}
	     
		
		
		return loginResponse;
	}

	@Override
	// @Cacheable annotation adds the caching behaviour. 
    // If multiple requests are received, then the method won't be repeatedly executed, instead, the results are shared from cached storage.
    @Cacheable(value="userRegistrationCache", key="#p0")
	public UserRegistration getUserRegistration(final Long userId) {
		final UserRegistration userRegistrationDetail = userRegistrationRepository.findByUserId(userId);
		if (userRegistrationDetail == null) {
			throw new UserNotFoundException("user not found");
		}
		return userRegistrationDetail;
	}

	public ForgetPasswordDto forgetPassword2(final ForgetPasswordInputDto forgetPasswordInputDto) {
		final UserRegistration userRegistrationRequestDto = userRegistrationRepository
				.findByEmail(forgetPasswordInputDto.getEmail());

		final ForgetPasswordDto forgetPasswordDto = new ForgetPasswordDto();
		if (forgetPasswordInputDto.getAnswer().equals(userRegistrationRequestDto.getForgetPasswordA())) {
			forgetPasswordDto.setEmail(userRegistrationRequestDto.getEmail());
		} else {
			forgetPasswordDto.setEmail(null);
		}
		return forgetPasswordDto;
	}

}
