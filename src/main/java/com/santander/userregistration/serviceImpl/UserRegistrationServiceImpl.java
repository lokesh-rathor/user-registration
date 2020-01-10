package com.santander.userregistration.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santander.userregistration.dto.ForgetPasswordDto;
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
		
		userRegistration = userRegistrationRepository.save(userRegistration);
		
		UserRegistrationResponseDto userRegistrationResponseDto=new UserRegistrationResponseDto();
		userRegistrationResponseDto.setEmail(userRegistration.getEmail());
		userRegistrationResponseDto.setUserId(userRegistration.getUserId());
		userRegistrationResponseDto.setMessage("Registered Successfully");
		
		return userRegistrationResponseDto;
	}

	@Override
	public String forgetPassword(ForgetPasswordDto email) {
		String state;
		UserRegistration userRegistrationRequestDto = userRegistrationRepository.findByEmail(email.getEmail());
	System.out.println(userRegistrationRequestDto.getEmail());
		if(userRegistrationRequestDto.getEmail().equals(email.getEmail()))
		{
			state = email.getEmail();
		}
		else
		{
			state = null;
		}
		
		
	    return state;
	}

	@Override
	public ForgetPasswordDto resetPassword(String email,ResetPasswordInputDto pwd) {
		
		UserRegistration userRegistrationRequestDto = userRegistrationRepository.findByEmail(email);
		
		userRegistrationRequestDto.setPassword(pwd.getPwd());
		userRegistrationRepository.save(userRegistrationRequestDto);
		return null;
	}

	@Override
	public Integer logIn(LogInInputDto loginDto) {
		UserRegistration logIn = userRegistrationRepository.findByEmail(loginDto.getEmail());
	    int result;
		if(logIn.getEmail().equals(loginDto.getEmail()) && logIn.getPassword().equals(loginDto.getPwd()))
		{
			result = 1;
		}
		else
		{
			result=0;
		}
		return result;
	}

}
