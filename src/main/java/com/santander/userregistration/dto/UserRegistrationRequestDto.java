package com.santander.userregistration.dto;

import java.sql.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegistrationRequestDto {

	@NotNull
	@Size(min=8)
	private String password;
	
	@NotNull
	@Size(min=2)
	private String firstName;
	
	@NotNull
	@Size(min=2)
	private String lastName;
	
	@NotNull
	private Date dateOfBirth;
	
	@NotNull
	@Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
	private String email;
	
	@NotNull
	private String forgetPasswordQ;
	
	@NotNull
	@Size(min=2)
	private String forgetPasswordA;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getForgetPasswordQ() {
		return forgetPasswordQ;
	}
	public void setForgetPasswordQ(String forgetPasswordQ) {
		this.forgetPasswordQ = forgetPasswordQ;
	}
	public String getForgetPasswordA() {
		return forgetPasswordA;
	}
	public void setForgetPasswordA(String forgetPasswordA) {
		this.forgetPasswordA = forgetPasswordA;
	}

}
