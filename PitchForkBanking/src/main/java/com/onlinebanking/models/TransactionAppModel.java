package com.onlinebanking.models;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;




public class TransactionAppModel {
	private String transactionId;
	@NotEmpty @Pattern(regexp="[0-9]*", message="the field can only contain numbers")
	private String fromAcountNum;
	@NotEmpty @Pattern(regexp="[0-9]*", message="the field can only contain numbers")
	private String toAccountNum;
	private String transactionType;
	@NotEmpty @Pattern(regexp="[0-9]*.[0-9]*", message="the field can only contain numbers") 
	private double transactionAmount;
	private String transactionTime;
	private String transactionStatus;

	public TransactionAppModel() {
		
	}

	public TransactionAppModel(Transaction t) {
		this.transactionId = t.getTransactionId();
		this.fromAcountNum = String.valueOf(t.getAccountByFromAcountNum().getAccountNum());
		this.toAccountNum = String.valueOf(t.getAccountByToAccountNum().getAccountNum());
		this.transactionType = t.getTransactionType();
		this.transactionAmount = t.getTransactionAmount();
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

	public double getTransactionAmount() {
		return this.transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
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
