package com.santander.userregistration.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santander.userregistration.dto.ForgetPasswordDto;
import com.santander.userregistration.dto.ForgetPasswordInputDto;
import com.santander.userregistration.dto.ForgetPasswordResponseDto;
import com.santander.userregistration.dto.LogInInputDto;
import com.santander.userregistration.dto.ResetPasswordInputDto;
import com.santander.userregistration.dto.UserRegistrationRequestDto;
import com.santander.userregistration.dto.UserRegistrationResponseDto;
import com.santander.userregistration.exception.UserNotFoundException;
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
	public ForgetPasswordResponseDto forgetPassword(ForgetPasswordDto email) {
		ForgetPasswordResponseDto state = new ForgetPasswordResponseDto();
		UserRegistration userRegistrationRequestDto = userRegistrationRepository.findByEmail(email.getEmail());
	System.out.println(userRegistrationRequestDto.getEmail());
		if(userRegistrationRequestDto.getEmail().equals(email.getEmail()))
		{
			state.setQuestion(userRegistrationRequestDto.getForgetPasswordQ());
			state.setEmail(userRegistrationRequestDto.getEmail());
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
		ForgetPasswordDto forgetPasswordDto = new ForgetPasswordDto();
		forgetPasswordDto.setEmail(userRegistrationRequestDto.getEmail());
		userRegistrationRequestDto.setPassword(pwd.getPwd());
		userRegistrationRepository.save(userRegistrationRequestDto);
		return forgetPasswordDto;
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

	@Override
	public UserRegistration getUserRegistration(Long userId) {
		UserRegistration userRegistrationDetail=userRegistrationRepository.findByUserId(userId);
		if(userRegistrationDetail==null) {
			throw new UserNotFoundException("user not found"); 
		}
		return userRegistrationDetail;
	}
	public ForgetPasswordDto forgetPassword2(ForgetPasswordInputDto forgetPasswordInputDto) {
		UserRegistration userRegistrationRequestDto = userRegistrationRepository.findByEmail(forgetPasswordInputDto.getEmail());
		
		ForgetPasswordDto forgetPasswordDto = new ForgetPasswordDto();
		if(forgetPasswordInputDto.getAnswer().equals(userRegistrationRequestDto.getForgetPasswordA()))
		{
			System.out.println(forgetPasswordInputDto.getAnswer());
			System.out.println(userRegistrationRequestDto.getForgetPasswordA());
			forgetPasswordDto.setEmail(userRegistrationRequestDto.getEmail());
		}
		else
		{
			forgetPasswordDto.setEmail(null);
		}
		return forgetPasswordDto;
	}

}
