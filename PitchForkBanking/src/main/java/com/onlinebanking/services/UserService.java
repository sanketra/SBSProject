package com.onlinebanking.services;

import java.util.List;

import com.onlinebanking.helpers.Response;
import com.onlinebanking.models.User;

public interface UserService {

	public User getAdmin();
	public void addUser(User p);
	public void updateUser(User p);
	public List<User> listUsers();
	public List<User> listCustomers();
	public List<User> listEmployees();
	public User getUserById(String id);
	public void removeUser(String id);
	public User getUserByEmailId(String emailId);
	public Response isValidUserAccount(int accountNo, String userId);
	public Response isValidAccount(int accountNo);
	public String getUserRole(String emailId);
	public List<User> listNewUsers();
	public Response updateUserRegistrationFlag(String id, String status);
	public void sendUniquePassword(String otp, String emailId);
}
