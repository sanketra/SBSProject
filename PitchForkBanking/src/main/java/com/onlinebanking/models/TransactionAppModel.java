package com.onlinebanking.models;
import javax.validation.constraints.Pattern;


import org.hibernate.validator.constraints.NotEmpty;




public class TransactionAppModel {
	private String transactionId;
	private String fromAcountNum;
	private String toAccountNum;
	private String transactionType;
	@NotEmpty @Pattern(regexp = "^\\d{0,6}\\.?\\d{0,2}$")
	private String transactionAmount;
	private String transactionTime;
	private String transactionStatus;

	public TransactionAppModel() {
		
	}

	public TransactionAppModel(Transaction t) {
		this.transactionId = t.getTransactionId();
		this.fromAcountNum = String.valueOf(t.getAccountByFromAcountNum().getAccountNum());
		this.toAccountNum = String.valueOf(t.getAccountByToAccountNum().getAccountNum());
		this.transactionType = t.getTransactionType();
		this.transactionAmount = String.valueOf(t.getTransactionAmount());
		this.transactionTime = t.getTransactionTime().toString();
		this.transactionStatus = t.getTransactionStatus();
	}

	public String getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getFromAcountNum() {
		return this.fromAcountNum;
	}

	public void setFromAcountNum(String fromAcountNum) {
		this.fromAcountNum = fromAcountNum;
	}

	public String getToAccountNum() {
		return this.toAccountNum;
	}

	public void setToAccountNum(String toAccountNum) {
		this.toAccountNum = toAccountNum;
	}

	public String getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionAmount() {
		return this.transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionTime() {
		return this.transactionTime;
	}

	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}

	public String getTransactionStatus() {
		return this.transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
}
