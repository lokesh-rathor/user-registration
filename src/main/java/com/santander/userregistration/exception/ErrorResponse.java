package com.santander.userregistration.exception;

import java.util.List;

public class ErrorResponse {
	
	public ErrorResponse() {
	}
	
	public ErrorResponse(String message, List<String> details) {
        super();
        this.message = message;
    }
 
	private int status;
    private String message;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
