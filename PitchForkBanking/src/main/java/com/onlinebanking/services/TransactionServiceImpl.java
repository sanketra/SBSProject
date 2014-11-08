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
import com.onlinebanking.models.RequestStatus;
import com.onlinebanking.models.Requests;
import com.onlinebanking.models.Transaction;
import com.onlinebanking.models.TransactionAppModel;
import com.onlinebanking.models.TransactionStatus;
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
	public int getNumberOfPendingCreateAccountRequests(String userId) {
		int returnVal = 0;
		returnVal = this.requestsHome.getPendingAccountCreationRequests(userId);
		return returnVal;
	}
	
	@Override
	@Transactional
	public Response createAccountCreationRequest() {
		try {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			User user = userHome.getUserByEmailId(auth.getName());
			List<Account> accounts = this.accountHome.getUserAccounts(user.getUserId());
			
			if (accounts.size() >= 2) {
				return new Response("error", "Maxmimum number of accounts reached.");
			}
			
			if (getNumberOfPendingCreateAccountRequests(user.getUserId()) > 0) {
				return new Response("error", "Request already exist.");
			}
			
			User admin = this.userHome.getAdmin();
			
			if (admin == null) {
				return new Response("error", "Could not create the request.");
			}

			Requests request = new Requests();
			request.setFromUser(user.getUserId());
			request.setToUser(admin.getUserId());
			request.setType("createaccount");
			request.setStatus(RequestStatus.PENDING);
			requestsHome.persist(request);
			return new Response("success", "Request created successfully!!");
		} catch (Exception e) {
			return new Response("error",
					"Exception occurred. Could not complete request");
		}

	}

	@Override
	@Transactional
	public Response addRequest(UserRequest userRequest) {
		try {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			String fromUserId = userHome.getUserByEmailId(auth.getName())
					.getUserId();
			
			User toUser = userHome.getUserByEmailId(userRequest.getEmailId());
			Response result = ValidationHelper.validateUserRequest(userRequest, toUser);
			if (result.getStatus().equalsIgnoreCase("error")) {
				return result;
			}
			
			List<Requests> existingRequest = requestsHome.getRequestsFor(fromUserId, toUser.getUserId(), userRequest.getRequestType());
			if(existingRequest.size() > 0)
			{
				return new Response("error",
						"this request already exists");
			}
			Requests request = new Requests();
			request.setFromUser(fromUserId);
			request.setToUser(toUser.getUserId());
			request.setType(userRequest.getRequestType());
			request.setStatus(RequestStatus.PENDING);

			requestsHome.persist(request);

			return new Response("success", "Request added!!");
		} catch (Exception e) {
			System.out.println(e);
			return new Response("error",
					"Exception occurred. Could not complete request");
		}

	}

	@Override
	@Transactional
	public List<UserRequest> getPendingRequestsToUser(String userId) {
		List<Requests> transactionRequests = requestsHome
				.getAllRequestsToUser(userId);
		List<UserRequest> userRequests = new ArrayList<UserRequest>();
		User toUser = userHome.findById(userId);
		for (Requests request : transactionRequests) {
			if (!request.getStatus().equalsIgnoreCase(RequestStatus.PENDING)) {
				continue;
			}

			String fromUserId = request.getFromUser();
			User fromUser = userHome.findById(fromUserId);
			UserRequest requestedUser = new UserRequest();
			requestedUser.setRequestId(request.getRequestId());
			requestedUser.setFname(toUser.getFname());
			requestedUser.setLname(toUser.getLname());
			requestedUser.setEmployeeName(fromUser.getFname() + " "
					+ fromUser.getLname());
			requestedUser.setEmailId(toUser.getEmailId());
			requestedUser.setRequestType(request.getType());
			userRequests.add(requestedUser);
		}
		return userRequests;
	}

	@Override
	@Transactional
	public List<UserRequest> getPendingRequestsFromUser(String userId) {
		List<Requests> transactionRequests = requestsHome
				.getAllRequestsFromUser(userId);
		List<UserRequest> userRequests = new ArrayList<UserRequest>();
		User fromUser = userHome.findById(userId);
		for (Requests request : transactionRequests) {
			if (!request.getStatus().equalsIgnoreCase(RequestStatus.PENDING)) {
				continue;
			}

			String toUserId = request.getToUser();
			User toUser = userHome.findById(toUserId);
			UserRequest requestedUser = new UserRequest();
			requestedUser.setRequestId(request.getRequestId());
			requestedUser.setFname(toUser.getFname());
			requestedUser.setLname(toUser.getLname());
			requestedUser.setEmployeeName(fromUser.getFname() + " "
					+ fromUser.getLname());
			requestedUser.setEmailId(toUser.getEmailId());
			requestedUser.setRequestType(request.getType());
			userRequests.add(requestedUser);
		}
		return userRequests;
	}

	@Override
	@Transactional
	public List<UserRequest> getApprovedTransactionRequestsFromUser() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String username = auth.getName();
		User u = userHome.getUserByEmailId(username);
		String userId = u.getUserId();
		List<Requests> transactionRequests = requestsHome
				.getApprovedTransactionRequestsForUser(userId);
		List<UserRequest> userRequests = new ArrayList<UserRequest>();
		for (Requests request : transactionRequests) {
			String toUserId = request.getToUser();
			User toUser = userHome.findById(toUserId);
			List<Account> userAccounts = accountHome.getUserAccounts(toUserId);

			for (Account acc : userAccounts) {
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
	public List<UserRequest> getApprovedProfileRequestsFromUser() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String username = auth.getName();
		User u = userHome.getUserByEmailId(username);
		String userId = u.getUserId();
		List<Requests> profileRequests = requestsHome
				.getApprovedProfileRequestsForUser(userId);
		List<UserRequest> userRequests = new ArrayList<UserRequest>();
		for (Requests request : profileRequests) {
			String toUserId = request.getToUser();
			User toUser = userHome.findById(toUserId);
			UserRequest requestedUser = new UserRequest();

			requestedUser.setFname(toUser.getFname());
			requestedUser.setLname(toUser.getLname());
			requestedUser.setEmailId(toUser.getEmailId());
			userRequests.add(requestedUser);
		}
		return userRequests;
	}

	@Override
	@Transactional
	public List<UserRequest> getApprovedRequests() {
		try {
			List<Requests> approvedRequest = requestsHome
					.getAllApprovedRequests();

			List<UserRequest> userRequests = new ArrayList<UserRequest>();
			for (Requests request : approvedRequest) {
				String toUserId = request.getToUser();
				String fromUserId = request.getFromUser();

				User toUser = userHome.findById(toUserId);
				User fromUser = userHome.findById(fromUserId);

				UserRequest requestedUser = new UserRequest();

				requestedUser.setEmployeeName(fromUser.getFname() + " "
						+ fromUser.getLname());
				requestedUser.setFname(toUser.getFname());
				requestedUser.setLname(toUser.getLname());
				requestedUser.setRequestType(request.getType());
				requestedUser.setStatus(request.getStatus());
				requestedUser.setRequestId(request.getRequestId());

				userRequests.add(requestedUser);
			}
			return userRequests;
		} catch (Exception e) {
			return new ArrayList<UserRequest>();
		}
	}

	@Override
	@Transactional
	public List<UserRequest> getAllPendingRequests() {
		try {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			String username = auth.getName();
			System.out.println("username: " + username);
			User u = userHome.getUserByEmailId(username);
			String userId = u.getUserId();
			List<Requests> pendingRequest = requestsHome
					.getAllPendingRequests(userId);
			List<UserRequest> userRequests = new ArrayList<UserRequest>();
			for (Requests request : pendingRequest) {
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
		} catch (Exception e) {
			return new ArrayList<UserRequest>();
		}
	}

	@Override
	@Transactional
	public List<UserRequest> getAllPendingUserAccessRequests() {
		try {

			List<Requests> pendingRequest = requestsHome
					.getAllPendingUserAccessRequests();
			List<UserRequest> userRequests = new ArrayList<UserRequest>();
			for (Requests request : pendingRequest) {
				String toUserId = request.getToUser();
				String fromUserId = request.getFromUser();

				User toUser = userHome.findById(toUserId);
				User fromUser = userHome.findById(fromUserId);

				UserRequest requestedUser = new UserRequest();

				requestedUser.setEmployeeName(fromUser.getFname() + " "
						+ fromUser.getLname());
				requestedUser.setFname(toUser.getFname());
				requestedUser.setLname(toUser.getLname());
				requestedUser.setRequestType(request.getType());
				requestedUser.setStatus(request.getStatus());
				requestedUser.setRequestId(request.getRequestId());

				userRequests.add(requestedUser);
			}
			return userRequests;
		} catch (Exception e) {
			return new ArrayList<UserRequest>();
		}
	}
	
	@Override
	@Transactional
	public List<UserRequest> getAllPendingAdditionalAccountRequests() {
		try {

			List<Requests> pendingRequest = requestsHome.getAllPendingAdditionalAccountRequests();
			List<UserRequest> userRequests = new ArrayList<UserRequest>();
			for (Requests request : pendingRequest) {
				String fromUserId = request.getFromUser();
				User fromUser = userHome.findById(fromUserId);

				UserRequest requestedUser = new UserRequest();
				requestedUser.setFname(fromUser.getFname());
				requestedUser.setLname(fromUser.getLname());
				requestedUser.setRequestType(request.getType());
				requestedUser.setStatus(request.getStatus());
				requestedUser.setRequestId(request.getRequestId());

				userRequests.add(requestedUser);
			}
			return userRequests;
		} catch (Exception e) {
			return new ArrayList<UserRequest>();
		}
	}
	
	@Override
	@Transactional
	public List<UserRequest> getDeclinedRequests() {
		try {
			List<Requests> approvedRequest = requestsHome.getAllDeclinedRequests();

			List<UserRequest> userRequests = new ArrayList<UserRequest>();
			for (Requests request : approvedRequest) {
				String toUserId = request.getToUser();
				String fromUserId = request.getFromUser();

				User toUser = userHome.findById(toUserId);
				User fromUser = userHome.findById(fromUserId);

				UserRequest requestedUser = new UserRequest();

				requestedUser.setEmployeeName(fromUser.getFname() + " "
						+ fromUser.getLname());
				requestedUser.setFname(toUser.getFname());
				requestedUser.setLname(toUser.getLname());
				requestedUser.setRequestType(request.getType());
				requestedUser.setStatus(request.getStatus());
				requestedUser.setRequestId(request.getRequestId());

				userRequests.add(requestedUser);
			}
			return userRequests;
		} catch (Exception e) {
			return new ArrayList<UserRequest>();
		}
	}

	@Override
	@Transactional
	public Response requestPayment(String fromAccount, String toAccount,
			String amt) {
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
		t.setTransactionType("Payment");
		msg = "Transfer waiting user approval.";
		t.setTransactionStatus(TransactionStatus.USERPENDING);

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
	public List<Transaction> getPaymentRequestForAccountId(int id) {
		return this.transactionHome.getPaymentRequestForAccountId(id);
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

		if ((type == TransactionType.TRANSFER || type == TransactionType.DEBIT) && fromAcc.getAmount() < amount) {
			return new Response("error", "Insufficient funds!!");
		}

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
				t.setTransactionStatus(TransactionStatus.ADMINPENDING);
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
				t.setTransactionStatus(TransactionStatus.ADMINPENDING);
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
				t.setTransactionStatus(TransactionStatus.ADMINPENDING);
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
	public Response updateTransaction(TransactionAppModel transactionAppModel) throws Exception 
	{
		Transaction transaction = transactionHome.findById(transactionAppModel.getTransactionId());
		if(transaction == null)
		{
			return new Response("error", "invalid transaction details");
		}
		Response status = ValidationHelper.validateTransactionAppModel(transactionAppModel, accountHome);
		if (status.getStatus().equals("error")) 
		{
			return status;
		}
		status = deleteTransaction(transaction);
		if (status.getStatus().equals("error")) 
		{
			return status;
		}
		
		Double amount = Double.parseDouble(transactionAppModel.getTransactionAmount());
		transaction.setTransactionAmount(amount);
		transaction.setTransactionStatus(transactionAppModel.getTransactionStatus());
		Account toAcc = accountHome.findById(Integer.parseInt(transactionAppModel.getToAccountNum()));
		Account fromAcc = accountHome.findById(Integer.parseInt(transactionAppModel.getFromAcountNum()));
		String transactionType = transaction.getTransactionType();
		
		if(transaction.getTransactionStatus().equalsIgnoreCase("success") || transaction.getTransactionStatus().equalsIgnoreCase("approved") || (amount < Constants.CRITICALTRANSACTION))
		{
			if(transactionType.equalsIgnoreCase("Debit"))
			{
				if(toAcc.getAmount() < amount)
				{
					return new Response("error", "Amount insufficient. Cannot perform this change");
				}
				toAcc.setAmount(toAcc.getAmount() - amount);
			}
			else if(transactionType.equalsIgnoreCase("Credit"))
			{
				toAcc.setAmount(toAcc.getAmount() + amount);
			}
			else if(transactionType.equalsIgnoreCase("Transfer"))
			{
				if(fromAcc.getAmount() < amount)
				{
					return new Response("error", "Amount insufficient. Cannot perform this change");
				}
				fromAcc.setAmount(fromAcc.getAmount() - amount);
				toAcc.setAmount(toAcc.getAmount() + amount);
			}
			else if(transactionType.equalsIgnoreCase("Payment"))
			{
				if(fromAcc.getAmount() < amount)
				{
					return new Response("error", "Amount insufficient. Cannot perform this change");
				}
				fromAcc.setAmount(fromAcc.getAmount() - amount);
				toAcc.setAmount(toAcc.getAmount() + amount);
			}
		}
		
		try 
		{
			transactionHome.persist(transaction);
			this.accountHome.merge(fromAcc);
			this.accountHome.merge(toAcc);
		} 
		catch (Exception e) 
		{
			return new Response("error",
					"Transaction failed. Please try again!");
		}

		return new Response("success", "updated transaction");
		
	}

	@Override
	@Transactional
	public Response deleteTransaction(Transaction transaction) throws Exception
	{
		if(transaction == null)
		{
			return new Response("error", "invalid transaction details");
		}
		Account toAcc = transaction.getAccountByToAccountNum();
		toAcc = accountHome.findById(toAcc.getAccountNum());
		Account fromAcc = transaction.getAccountByFromAcountNum();
		fromAcc = accountHome.findById(fromAcc.getAccountNum());
		Double amount = transaction.getTransactionAmount();
		String transactionType = transaction.getTransactionType();
		
		if(transaction.getTransactionStatus().equalsIgnoreCase("Success"))
		{
			if(transactionType.equalsIgnoreCase("Credit"))
			{
				if(toAcc.getAmount() < amount)
				{
					return new Response("error", "Amount insufficient. Cannot perform this change");
				}
				toAcc.setAmount(toAcc.getAmount() - amount);
			}
			else if(transactionType.equalsIgnoreCase("Debit"))
			{
				toAcc.setAmount(toAcc.getAmount() + amount);
			}
			else if(transactionType.equalsIgnoreCase("Transfer"))
			{
				if(toAcc.getAmount() < amount)
				{
					return new Response("error", "Amount insufficient. Cannot perform this change");
				}
				fromAcc.setAmount(fromAcc.getAmount() + amount);
				toAcc.setAmount(toAcc.getAmount() - amount);
			}
			else if(transactionType.equalsIgnoreCase("Payment"))
			{
				if(toAcc.getAmount() < amount)
				{
					return new Response("error", "Amount insufficient. Cannot perform this change");
				}
				fromAcc.setAmount(fromAcc.getAmount() + amount);
				toAcc.setAmount(toAcc.getAmount() - amount);
			}	
		}	
		transactionHome.delete(transaction);
		return new Response("success", "transaction deleted");
	}

	// Please use this function only for view access.
	@Override
	@Transactional
	public List<Transaction> getAllTransactionsForAccountId(int id) {
		List<Transaction> list = this.transactionHome
				.getAllTransactionsForAccountId(id);

		return list;
	}
	
	@Override
	@Transactional
	public List<Transaction> getAllCriticalTransactionRequests() {
		return this.transactionHome.getAllCriticalTransactions();
	}
	
	@Override
	@Transactional
	public Response updateCriticalTransactionRequest(String id, String status) throws Exception {
		Transaction t = this.transactionHome.findById(id);
		TransactionAppModel tm = new TransactionAppModel(t);
		Response response; 
		
		if (status.equals("approve")) {
			tm.setTransactionStatus(TransactionStatus.SUCCESS);
			response = updateTransaction(tm);
			if (response.getStatus().contentEquals("success")) {
				t.setTransactionStatus(TransactionStatus.SUCCESS);
				this.transactionHome.merge(t);
				return new Response("success", "Request approved! - Transaction done");
			} else {
				t.setTransactionStatus(RequestStatus.PENDING);
				this.transactionHome.merge(t);
				return new Response("error", "Transaction Falied!");
			}
		} else {
			this.transactionHome.delete(t);
			return new Response("success", "Request declined!");
		}
	}

	@Override
	@Transactional
	public Response updateAccessRequest(String id, String status) {
		Requests t = this.requestsHome.findById(id);

		if (status.equals("approve")) {
			t.setStatus(RequestStatus.APPROVED);
			this.requestsHome.merge(t);
			return new Response("success", "Request approved!");
		} else {
			this.requestsHome.delete(t);
			return new Response("success", "Request declined!");
		}
	}

	@Override
	@Transactional
	public Response updatePaymentRequest(String id, String status) {
		Transaction t = this.transactionHome.findById(id);

		if (status.equals("accept")) {
			if (t.getAccountByFromAcountNum().getAmount() < t
					.getTransactionAmount()) {
				return new Response("error", "Insufficient funds!!");
			}

			if (t.getTransactionAmount() < Constants.CRITICALTRANSACTION) {
				t.getAccountByFromAcountNum().setAmount(
						t.getAccountByFromAcountNum().getAmount()
								- t.getTransactionAmount());
				t.getAccountByToAccountNum().setAmount(
						t.getAccountByToAccountNum().getAmount()
								+ t.getTransactionAmount());
				this.accountHome.persist(t.getAccountByFromAcountNum());
				this.accountHome.persist(t.getAccountByToAccountNum());
				t.setTransactionStatus(TransactionStatus.SUCCESS);
				this.transactionHome.updatePaymentRequests(t);
				return new Response("success", "Payment approved!");
			} else {
				t.setTransactionStatus(TransactionStatus.ADMINPENDING);
				this.transactionHome.updatePaymentRequests(t);
				return new Response("success",
						"Payment waiting Admin approval!");
			}
		} else {
			t.setTransactionStatus(TransactionStatus.DECLINED);
			this.transactionHome.updatePaymentRequests(t);
			return new Response("success", "Payment declined!");
		}
	}

	@Override
	@Transactional
	public Transaction getTransaction(String transactionId) {
		return transactionHome.findById(transactionId);
	}

	@Override
	@Transactional
	public void deleteTransactionRequest(int accountId) {
		Account toAcc = accountHome.findById(accountId);
		String toUserId = toAcc.getUser().getUserId();
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		User fromUser = userHome.getUserByEmailId(auth.getName());
		List<Requests> existingRequest = requestsHome.getRequestsFor(fromUser.getUserId(), toUserId, "Transaction");
		for(Requests request : existingRequest)
		{
			requestsHome.delete(request);
		}
	}

	@Override
	@Transactional
	public void deleteProfileRequest(User u) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		User fromUser = userHome.getUserByEmailId(auth.getName());
		List<Requests> existingRequest = requestsHome.getRequestsFor(fromUser.getUserId(), u.getUserId(), "Profile");
		for(Requests request : existingRequest)
		{
			requestsHome.delete(request);
		}
	}
}
