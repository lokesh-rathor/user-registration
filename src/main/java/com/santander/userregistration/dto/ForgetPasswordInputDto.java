/**
 * 
 */
package com.santander.userregistration.dto;

/**
 * @author Harvindar.Raghav
 *
 */
public class ForgetPasswordInputDto {
	
	String email;
	String question;
	String answer;
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
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
