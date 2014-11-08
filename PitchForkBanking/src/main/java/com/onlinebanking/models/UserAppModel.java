package com.onlinebanking.models;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserAppModel {

	private String userId;
	@NotEmpty @Email
	private String emailId;
	@NotEmpty  @Pattern(regexp="[a-zA-Z]+")
	private String fname;
	@NotEmpty @Pattern(regexp="[a-zA-Z]+")
	private String lname;
	@NotEmpty @Pattern(regexp="[A-Za-z0-9'\\.\\-\\,\\s]{1,100}")
	private String address;
	@NotEmpty @Pattern(regexp="[a-zA-Z]+")
	private String city;
	@NotEmpty @Pattern(regexp="[a-zA-Z]+")
	private String state;
	@Size(min=5, max=5) @NotEmpty @Pattern(regexp="[0-9]{5}")
	private String zipcode;
	@Size(min=10,max=10) @NotEmpty @Pattern(regexp="[0-9]{10}")
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
