package com.onlinebanking.services;

import java.util.List;

import com.onlinebanking.helpers.Constants.TransactionType;
import com.onlinebanking.helpers.Response;
import com.onlinebanking.models.Transaction;
import com.onlinebanking.models.TransactionAppModel;
import com.onlinebanking.models.User;
import com.onlinebanking.models.UserRequest;

public interface TransactionService {

	public int getNumberOfPendingCreateAccountRequests(String userId);
	public Response createAccountCreationRequest();
	public Response requestPayment(String fromAccount, String toAccount, String amount);
	public Response createTransaction(String fromAccount, String toAccount, String amount, TransactionType type);
	public Response addRequest(UserRequest userRequest);
	public List<UserRequest> getPendingRequestsToUser(String userId);
	public List<UserRequest> getPendingRequestsFromUser(String userId);
	public List<UserRequest> getApprovedTransactionRequestsFromUser();
	public Response updateTransaction(TransactionAppModel transactionAppModel) throws Exception;
	public Response deleteTransaction(Transaction transaction) throws Exception;
	public List<Transaction> getAllTransactionsForAccountId(int Id);
	public List<UserRequest> getAllPendingRequests();
	public List<UserRequest> getApprovedRequests();
	public Transaction getTransaction(String transactionId);
	public List<Transaction> getPaymentRequestForAccountId(int id);
	public Response updatePaymentRequest(String id, String status);
	public List<UserRequest> getApprovedProfileRequestsFromUser();
	public Response updateAccessRequest(String id, String status);
	public List<UserRequest> getDeclinedRequests();

	public List<UserRequest> getAllPendingAdditionalAccountRequests();
	public List<UserRequest> getAllPendingUserAccessRequests();

	public void deleteTransactionRequest(int accountId);
	public void deleteProfileRequest(User u);
	public List<Transaction> getAllCriticalTransactionRequests();
	public Response updateCriticalTransactionRequest(String id, String status) throws Exception;
}
