package com.onlinebanking.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import com.onlinebanking.dao.AccountHome;
import com.onlinebanking.dao.RequestsHome;
import com.onlinebanking.dao.TransactionHome;
import com.onlinebanking.dao.UserHome;
import com.onlinebanking.helpers.Constants.TransactionType;
import com.onlinebanking.helpers.Response;
import com.onlinebanking.helpers.ValidationHelper;
import com.onlinebanking.models.Account;
import com.onlinebanking.models.Requests;
import com.onlinebanking.models.Transaction;
import com.onlinebanking.models.User;
import com.onlinebanking.models.UserRequest;

public class TransactionServiceImpl implements TransactionService {

	private RequestsHome requestsHome;
	private TransactionHome transactionHome;
	private AccountHome accountHome;
	private UserHome userHome;

	public void setAccountHome(AccountHome accountHome) {
		this.accountHome = accountHome;
	}

	public void setRequestsHome(RequestsHome requestsDAO) {
		this.requestsHome = requestsDAO;
	}

	public void setTransactionHome(TransactionHome transactionDAO) {
		this.transactionHome = transactionDAO;
	}
	
	public void setUserHome(UserHome userDAO) {
		this.userHome = userDAO;
	}

	@Override
	@Transactional
	public Response addRequest(UserRequest userRequest) {
		try
		{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String fromUserId = userHome.getUserByEmailId(auth.getName()).getUserId();
			Response result = ValidationHelper.validateUserRequest(userRequest);
			if(result.getStatus().equalsIgnoreCase("error"))
			{
				return result;
			}
			User toUser = userHome.getUserByEmailId(userRequest.getEmailId());
			if(toUser == null)
			{
				return new Response("error", "user details not correct");
			}
			Requests request = new Requests();
			request.setFromUser(fromUserId);
			request.setToUser(toUser.getUserId());
			request.setType(userRequest.getRequestType());
			request.setStatus("pending");
			
			requestsHome.persist(request);
			
			return new Response("success", "Request added!!");
		}
		catch(Exception e)
		{
			return new Response("error", "Exception occurred. Could not complete request");
		}
		
	}

	@Override
	@Transactional
	public List<Requests> getAllRequestsFromUser(String userId) {
		return requestsHome.getAllRequestsFromUser(userId);
	}

	@Override
	@Transactional
	public List<UserRequest> getApprovedTransactionRequestsFromUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User u = userHome.getUserByEmailId(username);
		String userId = u.getUserId();
		List<Requests> transactionRequests = requestsHome
				.getApprovedTransactionRequestsForUser(userId);
		List<UserRequest> userRequests = new ArrayList<UserRequest>();
		for(Requests request : transactionRequests)
		{
			String toUserId = request.getToUser();
			User toUser = userHome.findById(toUserId);
			List<Account> userAccounts = accountHome.getUserAccounts(toUserId);
			
			for(Account acc : userAccounts)
			{
				UserRequest requestedUser = new UserRequest();
				requestedUser.setFname(toUser.getFname());
				requestedUser.setLname(toUser.getLname());
				requestedUser.setEmailId(toUser.getEmailId());
				requestedUser.setAccountId(acc.getAccountNum());
				userRequests.add(requestedUser);
			}
		}
		return userRequests;
	}
	
	@Override
	@Transactional
	public List<UserRequest> getAllPendingRequests() {
		try
		{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String username = auth.getName();
			User u = userHome.getUserByEmailId(username);
			String userId = u.getUserId();
			List<Requests> pendingRequest = requestsHome.getAllPendingRequests(userId);
			List<UserRequest> userRequests = new ArrayList<UserRequest>();
			for(Requests request : pendingRequest)
			{
				String toUserId = request.getToUser();
				User toUser = userHome.findById(toUserId);
				UserRequest requestedUser = new UserRequest();
				requestedUser.setFname(toUser.getFname());
				requestedUser.setLname(toUser.getLname());
				requestedUser.setEmailId(toUser.getEmailId());
				requestedUser.setRequestType(request.getType());
				userRequests.add(requestedUser);
			}
			return userRequests;
		}
		catch(Exception e)
		{
			return new ArrayList<UserRequest>();
		}
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
			t.setTransactionType("credit");
			toAcc.setAmount(toAcc.getAmount() + amount);
			break;
		case DEBIT:
			t.setTransactionType("debit");
			toAcc.setAmount(toAcc.getAmount() - amount);
		default:
			t.setTransactionType("t-debit");
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

	@Override
	@Transactional
	public List<Transaction> getAllTransactionsForAccountId(int id) {
		return this.transactionHome.getAllTransactionsForAccountId(id);
	}

	@Override
	@Transactional
	public Transaction getTransaction(String transactionId) {
		return transactionHome.findById(transactionId);
	}

}
