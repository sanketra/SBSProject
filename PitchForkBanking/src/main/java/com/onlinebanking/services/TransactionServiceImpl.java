package com.onlinebanking.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.onlinebanking.dao.AccountHome;
import com.onlinebanking.dao.RequestsHome;
import com.onlinebanking.dao.TransactionHome;
import com.onlinebanking.helpers.Constants.TransactionType;
import com.onlinebanking.models.Account;
import com.onlinebanking.models.Requests;
import com.onlinebanking.models.Transaction;

public class TransactionServiceImpl implements TransactionService {

	private RequestsHome requestsHome;
	private TransactionHome transactionHome;
	private AccountHome accountHome;

	public void setAccountHome(AccountHome accountHome) {
		this.accountHome = accountHome;
	}

	public void setRequestsHome(RequestsHome requestsDAO) {
		this.requestsHome = requestsDAO;
	}

	public void setTransactionHome(TransactionHome transactionDAO) {
		this.transactionHome = transactionDAO;
	}

	@Override
	@Transactional
	public void addRequest(Requests request) {
		requestsHome.persist(request);
	}

	@Override
	@Transactional
	public List<Requests> getAllRequestsFromUser(String userId) {
		return requestsHome.getAllRequestsFromUser(userId);
	}

	@Override
	@Transactional
	public List<Transaction> getListOfApprovedTransactionRequests(String userId) {
		List<String> transactionId = requestsHome
				.getApprovedTransactionRequestsForUser(userId);
		List<Transaction> transactionList = new ArrayList<Transaction>();
		for (String id : transactionId) {
			Transaction transaction = transactionHome.findById(id);
			transactionList.add(transaction);
		}
		return transactionList;
	}

	@Override
	@Transactional
	public void createTransaction(String fromAccount, String toAccount,
			double amount, TransactionType type) {
		Transaction t = new Transaction();
		Account toAcc = this.accountHome.findById(Integer.parseInt(toAccount));
		Account fromAcc = this.accountHome.findById(Integer
				.parseInt(fromAccount));
		t.setAccountByFromAcountNum(fromAcc);
		t.setAccountByToAccountNum(toAcc);
		t.setTransactionAmount(amount);
		t.setTransactionStatus("success");
		t.setTransactionTime(new Date());

		switch (type) {
		case CREDIT:
			t.setTransactionType("Credit");
			toAcc.setAmount(toAcc.getAmount() + amount);
			break;
		case DEBIT:
			t.setTransactionType("Debit");
			toAcc.setAmount(toAcc.getAmount() - amount);
		default:
			t.setTransactionType("Transfer");
			fromAcc.setAmount(fromAcc.getAmount() - amount);
			toAcc.setAmount(toAcc.getAmount() + amount);
		}
		// All try & catch return ValidationStatus
		transactionHome.persist(t);
		this.accountHome.merge(fromAcc);
		this.accountHome.merge(toAcc);
	}

	@Override
	@Transactional
	public void updateTransaction(Transaction transaction) {
		transactionHome.merge(transaction);
	}

	@Override
	@Transactional
	public void deleteTransaction(Transaction transaction) {
		transactionHome.delete(transaction);
	}

	// Please use this function only for view access.
	@Override
	@Transactional
	public List<Transaction> getAllTransactionsForAccountId(int id) {
		List<Transaction> list = this.transactionHome.getAllTransactionsForAccountId(id);
		
		for (Transaction t : list) {
			if (t.getAccountByFromAcountNum().getAccountNum() != t.getAccountByToAccountNum().getAccountNum()) {
				String type = t.getAccountByFromAcountNum().getAccountNum() == id ? "Debit" : "Credit";
				t.setTransactionType(type);
			}
		}
		
		return list;
	}
}
