package com.onlinebanking.models;


public class UserAppModel {

	private String userId;
	private String emailId;
	private String fname;
	private String lname;
	private String address;
	private String city;
	private String state;
	private String zipcode;
	private String phoneno;

	public UserAppModel() {
	}

	public UserAppModel(User u) {
		this.userId = u.getUserId();
		this.emailId = u.getEmailId();
		this.fname = u.getFname();
		this.lname = u.getLname();
		this.address = u.getAddress();
		this.city = u.getCity();
		this.state = u.getState();
		this.zipcode = u.getZipcode();
		this.phoneno = u.getPhoneno();
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return this.lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getPhoneno() {
		return this.phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

}
