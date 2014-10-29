package com.onlinebanking.models;


import java.util.Date;

public class UserAppModel {

	private String userId;
	private String emailId;
	private String fname;
	private String lname;
	private Date dob;
	private String address;
	private String city;
	private String state;
	private String zipcode;
	private String ssn;
	private String phoneno;
	private String ques1;
	private String answer1;
	private String ques2;
	private String answer2;
	private String ques3;
	private String answer3;

	public UserAppModel() {
	}

	public UserAppModel(User u) {
		this.userId = u.getUserId();
		this.emailId = u.getEmailId();
		this.fname = u.getFname();
		this.lname = u.getLname();
		this.dob = u.getDob();
		this.address = u.getAddress();
		this.city = u.getCity();
		this.state = u.getState();
		this.zipcode = u.getZipcode();
		this.ssn = u.getSsn();
		this.phoneno = u.getPhoneno();
		this.ques1 = u.getQues1();
		this.answer1 = u.getAnswer1();
		this.ques2 = u.getQues2();
		this.answer2 = u.getAnswer2();
		this.ques3 = u.getQues3();
		this.answer3 = u.getAnswer3();
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

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
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

	public String getSsn() {
		return this.ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getPhoneno() {
		return this.phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getQues1() {
		return this.ques1;
	}

	public void setQues1(String ques1) {
		this.ques1 = ques1;
	}

	public String getAnswer1() {
		return this.answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getQues2() {
		return this.ques2;
	}

	public void setQues2(String ques2) {
		this.ques2 = ques2;
	}

	public String getAnswer2() {
		return this.answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public String getQues3() {
		return this.ques3;
	}

	public void setQues3(String ques3) {
		this.ques3 = ques3;
	}

	public String getAnswer3() {
		return this.answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}


}
