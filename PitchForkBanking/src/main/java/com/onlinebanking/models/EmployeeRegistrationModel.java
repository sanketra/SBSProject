package com.onlinebanking.models;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class EmployeeRegistrationModel {

	private String userId;
	@NotEmpty @Email
	private String emailId;
	@NotEmpty @Pattern(regexp="[a-zA-Z]+")
	private String fname;
	@NotEmpty @Pattern(regexp="[a-zA-Z]+")
	private String lname;
	@NotEmpty @Pattern(regexp="(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)")
	private String dob;
	@NotEmpty @Pattern(regexp= "[a-zA-Z0-9]+")
	private String address;
	@NotEmpty @Pattern(regexp="[a-zA-Z]+")
	private String city;
	@NotEmpty @Pattern(regexp="[a-zA-Z]+")
	private String state;
	@NotEmpty @Pattern(regexp= "^(?=.*[A-Z].*)(?=.*[!@#$&*].*)(?=.*[0-9].*)(?=.*[a-z].*).{8,15}$")
	private String password = "@1Aaaaaa";
	@Size(min=5, max=5) @NotEmpty @Pattern(regexp="[0-9]{5}")
	private String zipcode;
	@NotEmpty @Pattern(regexp = "[0-9]{9}")
	private String ssn;
	@NotEmpty @Pattern(regexp = "[0-9]{10}")
	private String phoneno;
	@NotEmpty
	private String role = "Employee";
	@NotEmpty
	private String ques1 = "q1";
	@NotEmpty  @Pattern(regexp="[a-zA-Z0-9]+")
	private String answer1 = "a1";
	@NotEmpty
	private String ques2 = "q2";
	@NotEmpty  @Pattern(regexp="[a-zA-Z0-9]+")
	private String answer2 = "a2";
	@NotEmpty
	private String ques3 = "q3";
	@NotEmpty  @Pattern(regexp="[a-zA-Z0-9]+")
	private String answer3 = "a3";
	@NotNull
	private int enabled = 1;

	public EmployeeRegistrationModel() {
	}

	public EmployeeRegistrationModel(User u) {
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
	

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPassword() {
		String pass, otp;
		SecureRandom random = new SecureRandom();
		otp = new BigInteger(130, random).toString(32);
		pass = otp.substring(0, 9);
		setPassword(pass);
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getQues1() {
		return ques1;
	}

	public void setQues1(String ques1) {
		this.ques1 = ques1;
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getQues2() {
		return ques2;
	}

	public void setQues2(String ques2) {
		this.ques2 = ques2;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public String getQues3() {
		return ques3;
	}

	public void setQues3(String ques3) {
		this.ques3 = ques3;
	}

	public String getAnswer3() {
		return answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}
	
	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
}
