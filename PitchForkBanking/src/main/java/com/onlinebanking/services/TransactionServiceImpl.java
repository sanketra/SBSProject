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
import com.onlinebanking.helpers.Constants;
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
		try {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			String fromUserId = userHome.getUserByEmailId(auth.getName())
					.getUserId();
			Response result = ValidationHelper.validateUserRequest(userRequest);
			if (result.getStatus().equalsIgnoreCase("error")) {
				return result;
			}
			User toUser = userHome.getUserByEmailId(userRequest.getEmailId());
			if (toUser == null) {
				return new Response("error", "user details not correct");
			}
			Requests request = new Requests();
			request.setFromUser(fromUserId);
			request.setToUser(toUser.getUserId());
			request.setType(userRequest.getRequestType());
			request.setStatus("pending");

			requestsHome.persist(request);

			return new Response("success", "Request added!!");
		} catch (Exception e) {
			return new Response("error",
					"Exception occurred. Could not complete request");
		}

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
	public Response createTransaction(String fromAccount, String toAccount,
			String amt, TransactionType type) {
		// Validating amount
		Response status = ValidationHelper.validateAmount(amt);
		if (status.getStatus().equals("error")) {
			return status;
		}

		Double amount = Double.parseDouble(amt);
		String msg = "";
		Transaction t = new Transaction();
		Account toAcc = this.accountHome.findById(Integer.parseInt(toAccount));
		Account fromAcc = this.accountHome.findById(Integer
				.parseInt(fromAccount));
		t.setAccountByFromAcountNum(fromAcc);
		t.setAccountByToAccountNum(toAcc);
		t.setTransactionAmount(amount);
		t.setTransactionTime(new Date());

		switch (type) {
		case CREDIT:
			t.setTransactionType("Credit");
			if (amount < Constants.CRITICALTRANSACTION) {
				msg = "Account credited successfully.";
				t.setTransactionStatus("success");
				toAcc.setAmount(toAcc.getAmount() + amount);
			} else {
				msg = "Transfer waiting admin approval.";
				t.setTransactionStatus("pending");
			}
			break;
		case DEBIT:
			t.setTransactionType("Debit");
			if (amount < Constants.CRITICALTRANSACTION) {
				msg = "Account debited successfully.";
				t.setTransactionStatus("success");
				toAcc.setAmount(toAcc.getAmount() - amount);
			} else {
				msg = "Transfer waiting admin approval.";
				t.setTransactionStatus("pending");
			}
			break;
		default:
			t.setTransactionType("Transfer");
			if (amount < Constants.CRITICALTRANSACTION) {
				msg = "Transfer successfully.";
				t.setTransactionStatus("success");
				fromAcc.setAmount(fromAcc.getAmount() - amount);
				toAcc.setAmount(toAcc.getAmount() + amount);
			} else {
				msg = "Transfer waiting admin approval.";
				t.setTransactionStatus("pending");
			}
		}

		try {
			transactionHome.persist(t);
			this.accountHome.merge(fromAcc);
			this.accountHome.merge(toAcc);
		} catch (Exception e) {
			return new Response("error",
					"Transaction failed. Please try again!");
		}

		return new Response("success", msg);
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
		List<Transaction> list = this.transactionHome
				.getAllTransactionsForAccountId(id);

		for (Transaction t : list) {
			if (t.getAccountByFromAcountNum().getAccountNum() != t
					.getAccountByToAccountNum().getAccountNum()) {
				String type = t.getAccountByFromAcountNum().getAccountNum() == id ? "Debit"
						: "Credit";
				t.setTransactionType(type);
			}
		}

		return list;
	}
}
