package com.santander.userregistration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santander.userregistration.model.UserRegistration;

import feign.Param;

@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Integer>{
	
	public UserRegistration findByEmail(@Param(value = "email") String email);
	public List<UserRegistrationRequestDto> findByUserId(int userId);

}
