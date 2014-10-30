package com.onlinebanking.models;

public class UserRequest {
	private String requestId;
	private String fname;
	private String lname;
	private String emailId;
	private String employeeName;
	private int accountId;
	private String requestType;
	
	public UserRequest()
	{
		
	}

	public UserRequest(String fname, String lname, String emailId, int accountId, String requestType, String employeeName)
	{
		this.fname = fname;
		this.lname = lname;
		this.emailId = emailId;
		this.accountId = accountId;
		this.requestType = requestType;
		this.employeeName = employeeName;
	}
	
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
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
	
	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
		
}
