package com.onlinebanking.services;

import java.util.List;

import com.onlinebanking.helpers.Constants.TransactionType;
import com.onlinebanking.helpers.Response;
import com.onlinebanking.models.Requests;
import com.onlinebanking.models.Transaction;
import com.onlinebanking.models.UserRequest;

public interface TransactionService {

	public Response createTransaction(String fromAccount, String toAccount, String amount, TransactionType type);
	public Response addRequest(UserRequest userRequest);
	public List<Requests> getAllRequestsFromUser(String userId);
	public List<Transaction> getListOfApprovedTransactionRequests(String userId);
	public void updateTransaction(Transaction transaction);
	public void deleteTransaction(Transaction transaction);
	public List<Transaction> getAllTransactionsForAccountId(int Id);
}
