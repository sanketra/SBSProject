package com.onlinebanking.models;

import com.onlinebanking.helpers.Constants;
import com.onlinebanking.helpers.ValidationHelper;

public class AccountAppModel {

	private int accountNum;
	private User user;
	private String accountType;
	private double amount;

	public AccountAppModel() {
		this.accountNum = ValidationHelper.generateRandomNumber(Constants.MIN_ACCOUNTNUMBER, Constants.MAX_ACCOUNTNUMBER);
	}

	public AccountAppModel(Account a) {
		this.accountNum = a.getAccountNum();
		this.user = a.getUser();
		this.accountType = a.getAccountType();
		this.amount = a.getAmount();
	}
	
	public AccountAppModel(int accountNum, User user, String accountType, double amount) {
		this.accountNum = accountNum;
		this.user = user;
		this.accountType = accountType;
		this.amount = amount;
	}

	public int getAccountNum() {
		return this.accountNum;
	}

	public void setAccountNum(int accountNum) {
		this.accountNum = accountNum;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAccountType() {
		return this.accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
