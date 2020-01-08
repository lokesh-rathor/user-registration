package com.santander.userregistration.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santander.userregistration.dto.ForgetPasswordDto;
import com.santander.userregistration.dto.LogInDto;
import com.santander.userregistration.dto.LogInInputDto;
import com.santander.userregistration.dto.ResetPasswordInputDto;
import com.santander.userregistration.dto.UserRegistrationRequestDto;
import com.santander.userregistration.dto.UserRegistrationResponseDto;
import com.santander.userregistration.model.UserRegistration;
import com.santander.userregistration.repository.UserRegistrationRepository;
import com.santander.userregistration.service.UserRegistrationService;


@Service
public class UserRegistrationServiceImpl implements UserRegistrationService{

	@Autowired
	private UserRegistrationRepository userRegistrationRepository;
	
	@Override
	public UserRegistrationResponseDto userRegister(UserRegistrationRequestDto userRegistrationRequestDto) {
		
		UserRegistration userRegistration=new UserRegistration();
		userRegistration.setFirstName(userRegistrationRequestDto.getFirstName());
		userRegistration.setLastName(userRegistrationRequestDto.getLastName());
		userRegistration.setEmail(userRegistrationRequestDto.getEmail());
		userRegistration.setPassword(userRegistrationRequestDto.getPassword());
		userRegistration.setDateOfBirth(userRegistrationRequestDto.getDateOfBirth());
		userRegistration.setForgetPasswordA(userRegistrationRequestDto.getForgetPasswordA());
		userRegistration.setForgetPasswordQ(userRegistrationRequestDto.getForgetPasswordQ());
		userRegistration.setIsSingleOrJoint(userRegistrationRequestDto.getIsSingleOrJoint());
		
		userRegistration = userRegistrationRepository.save(userRegistration);
		
		UserRegistrationResponseDto userRegistrationResponseDto=new UserRegistrationResponseDto();
		userRegistrationResponseDto.setEmail(userRegistration.getEmail());
		userRegistrationResponseDto.setUserId(userRegistration.getUserId());
		userRegistrationResponseDto.setMessage("Registered Successfully");
		
		return userRegistrationResponseDto;
	}

	@Override
	public ForgetPasswordDto forgetPassword(String email) {
		
		UserRegistration userRegistrationRequestDto = userRegistrationRepository.findByEmail(email);
		
		ForgetPasswordDto forgetPasswordDto = new ForgetPasswordDto();
		forgetPasswordDto.setAnswer(userRegistrationRequestDto.getForgetPasswordA());
		forgetPasswordDto.setQuestions(userRegistrationRequestDto.getForgetPasswordQ());
		
		
				return forgetPasswordDto;
	}

	@Override
	public ForgetPasswordDto resetPassword(String email,ResetPasswordInputDto pwd) {
		
		UserRegistration userRegistrationRequestDto = userRegistrationRepository.findByEmail(email);
		
		userRegistrationRequestDto.setPassword(pwd.getPwd());
		userRegistrationRepository.save(userRegistrationRequestDto);
		return null;
	}

	@Override
	public LogInDto logIn(LogInInputDto loginDto) {
		UserRegistration logIn = userRegistrationRepository.findByEmail(loginDto.getEmail());
		
		if(logIn.getEmail().equals(loginDto.getEmail()) && logIn.getPassword().equals(loginDto.getPwwd()))
		{
			System.out.println("abcd");
		}
		else
		{
			System.out.println("hkjkhhhjhjkh");
		}
		return null;
	}

}
