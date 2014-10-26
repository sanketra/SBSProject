package com.onlinebanking.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.onlinebanking.dao.RequestsHome;
import com.onlinebanking.dao.TransactionHome;
import com.onlinebanking.models.Requests;
import com.onlinebanking.models.Transaction;

public class TransactionServiceImpl implements TransactionService {

	private RequestsHome requestsHome;
	private TransactionHome transactionHome;

	public void setRequestsHome(RequestsHome requestsDAO) {
		this.requestsHome = requestsDAO;
	}
	
	public void setTransactionHome(TransactionHome transactionDAO) {
		this.transactionHome = transactionDAO;
	}
	
	@Override
	@Transactional
	public void addRequest(Requests request)
	{
		requestsHome.persist(request);
	}
	
	@Override
	@Transactional
	public List<Requests> getAllRequestsFromUser(String userId)
	{
		return requestsHome.getAllRequestsFromUser(userId);
	}
	
	@Override
	@Transactional
	public List<Transaction> getListOfApprovedTransactionRequests(String userId)
	{
		List<String> transactionId = requestsHome.getApprovedTransactionRequestsForUser(userId);
		List<Transaction> transactionList = new ArrayList<Transaction>();
		for(String id : transactionId)
		{
			Transaction transaction = transactionHome.findById(id);
			transactionList.add(transaction);
		}
		return transactionList;
	}
	
	@Override
	@Transactional
	public void updateTransaction(Transaction transaction)
	{
		transactionHome.merge(transaction);
	}
	
	@Override
	@Transactional
	public void deleteTransaction(Transaction transaction)
	{
		transactionHome.delete(transaction);
	}
}
