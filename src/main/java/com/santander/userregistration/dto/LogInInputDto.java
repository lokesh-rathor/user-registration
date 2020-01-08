/**
 * 
 */
package com.santander.userregistration.dto;

import com.sun.istack.NotNull;

/**
 * @author Harvindar.Raghav
 *
 */
public class LogInInputDto {
	
	@NotNull
	String email;
	@NotNull
	String pwd;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
