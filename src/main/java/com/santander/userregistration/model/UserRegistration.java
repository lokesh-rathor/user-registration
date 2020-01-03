package com.santander.userregistration.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="userregistration")
@Entity
public class UserRegistration {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="UserId")
	private Long userId;
	
	@Column(name="Password")
	private String password;
	
	@Column(name="FirstName")
	private String firstName;
	
	@Column(name="LastName")
	private String lastName;
	
	@Column(name="DateOfBirth")
	private Date dateOfBirth;
	
	@Column(name="Email", unique=true)
	private String email;
	
	@Column(name="IsSingleORJointAcc")
	private String isSingleOrJoint;
	
	@Column(name="ForgetPasswordQ")
	private String forgetPasswordQ;
	
	@Column(name="ForgetPasswordA")
	private String forgetPasswordA;


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

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

	public String getIsSingleOrJoint() {
		return isSingleOrJoint;
	}

	public void setIsSingleOrJoint(String isSingleOrJoint) {
		this.isSingleOrJoint = isSingleOrJoint;
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
