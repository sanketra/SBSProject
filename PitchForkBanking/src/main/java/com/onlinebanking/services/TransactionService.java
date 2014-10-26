package com.onlinebanking.services;

import java.util.List;

import com.onlinebanking.models.Requests;
import com.onlinebanking.models.Transaction;

public interface TransactionService {

	public void createTransaction(String fromAccount, String toAccount, int amount);
	public void addRequest(Requests request);
	public List<Requests> getAllRequestsFromUser(String userId);
	public List<Transaction> getListOfApprovedTransactionRequests(String userId);
	public void updateTransaction(Transaction transaction);
	public void deleteTransaction(Transaction transaction);
	public List<Transaction> getAllTransactionsForAccountId(int Id);
}
