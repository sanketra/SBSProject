package com.onlinebanking.models;

// Generated Oct 19, 2014 4:55:42 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Transaction generated by hbm2java
 */
@Entity
@Table(name = "transaction", catalog = "pitchforkbank")
public class Transaction implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8843535678923298591L;
	private String transactionId;
	private Account accountByFromAcountNum;
	private Account accountByToAccountNum;
	private String transactionType;
	private int transactionAmount;
	private Date transactionTime;
	private String transactionStatus;

	public Transaction() {
		this.transactionId = UUID.randomUUID().toString();
	}

	public Transaction(String transactionId, Account accountByFromAcountNum,
			Account accountByToAccountNum, String transactionType,
			int transactionAmount, Date transactionTime,
			String transactionStatus) {
		this.transactionId = transactionId;
		this.accountByFromAcountNum = accountByFromAcountNum;
		this.accountByToAccountNum = accountByToAccountNum;
		this.transactionType = transactionType;
		this.transactionAmount = transactionAmount;
		this.transactionTime = transactionTime;
		this.transactionStatus = transactionStatus;
	}

	@Id
	@Column(name = "transactionId", unique = true, nullable = false, length = 36)
	public String getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fromAcountNum", nullable = false)
	public Account getAccountByFromAcountNum() {
		return this.accountByFromAcountNum;
	}

	public void setAccountByFromAcountNum(Account accountByFromAcountNum) {
		this.accountByFromAcountNum = accountByFromAcountNum;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "toAccountNum", nullable = false)
	public Account getAccountByToAccountNum() {
		return this.accountByToAccountNum;
	}

	public void setAccountByToAccountNum(Account accountByToAccountNum) {
		this.accountByToAccountNum = accountByToAccountNum;
	}

	@Column(name = "transactionType", nullable = false, length = 45)
	public String getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Column(name = "transactionAmount", nullable = false)
	public int getTransactionAmount() {
		return this.transactionAmount;
	}

	public void setTransactionAmount(int transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "transactionTime", nullable = false, length = 0)
	public Date getTransactionTime() {
		return this.transactionTime;
	}

	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}

	@Column(name = "transactionStatus", nullable = false, length = 45)
	public String getTransactionStatus() {
		return this.transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	
}
