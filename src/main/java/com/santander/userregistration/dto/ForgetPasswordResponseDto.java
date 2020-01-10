/**
 * 
 */
package com.santander.userregistration.dto;

/**
 * @author Harvindar.Raghav
 *
 */
public class ForgetPasswordResponseDto {
	
	String question;
	
	String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

}
