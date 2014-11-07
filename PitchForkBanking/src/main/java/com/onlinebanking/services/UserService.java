package com.onlinebanking.services;

import java.util.List;

import com.onlinebanking.helpers.Response;
import com.onlinebanking.models.User;

public interface UserService {

	public User getAdmin();
	public Response addUser(User p);
	public Response updateUser(User p);
	public List<User> listUsers();
	public List<User> listCustomers();
	public List<User> listEmployees();
	public User getUserById(String id);
	public Response removeUser(String id);
	public User getUserByEmailId(String emailId);
	public Response isValidUserAccount(int accountNo, String userId);
	public Response isValidAccount(int accountNo);
	public String getUserRole(String emailId);
	public List<User> listNewUsers();
	public Response updateUserRegistrationFlag(String id, String status);
	public Response sendUniquePassword(String otp, String emailId);
	public boolean verifyByDecrypting(String plainText, String encrypted)
			throws Exception;
	public Response createAccount(User p);
	public Response updateNewAccountRequest(String id, String status);
	public int verifyUserUniquness(User p);
}
