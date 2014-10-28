package com.onlinebanking.helpers;

public class ValidationStatus {
	private Boolean status;
	private String message;
	
	public ValidationStatus(Boolean status, String msg) {
		this.status = status;
		this.message = msg;
	}	
	
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
