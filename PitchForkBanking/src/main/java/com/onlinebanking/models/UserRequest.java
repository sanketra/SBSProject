package com.onlinebanking.models;

public class UserRequest {
	private String fname;
	private String lname;
	private String emailId;
	private String requestType;
	
	public UserRequest()
	{
		
	}
	
	public UserRequest(String fname, String lname, String emailId, String requestType)
	{
		this.fname = fname;
		this.lname = lname;
		this.emailId = emailId;
		this.requestType = requestType;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
		
}
