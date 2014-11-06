package com.onlinebanking.models;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;



public class UserRequest {
	private String requestId;
	@NotEmpty @Pattern(regexp="[a-zA-Z]+")
	private String fname;
	@NotEmpty @Pattern(regexp="[a-zA-Z]+")
	private String lname;
	@NotEmpty @Email
	private String emailId;
	private String employeeName;
	private int accountId;
	private String requestType;
	private String status;
	
	public UserRequest()
	{
		
	}

	public UserRequest(String fname, String lname, String emailId, int accountId, String requestType, String employeeName, String status)
	{
		this.fname = fname;
		this.lname = lname;
		this.emailId = emailId;
		this.accountId = accountId;
		this.requestType = requestType;
		this.employeeName = employeeName;
		this.status = status;
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
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
		
}
