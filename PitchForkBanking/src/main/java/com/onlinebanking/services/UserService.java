package com.onlinebanking.services;

import java.util.List;

import com.onlinebanking.helpers.Response;
import com.onlinebanking.models.User;

public interface UserService {

	public void addUser(User p);
	public void updateUser(User p);
	public List<User> listUsers();
	public User getUserById(String id);
	public void removeUser(String id);
	public User getUserByEmailId(String emailId);
	public Response isValidUserAccount(int accountNo, String userId);
	public Response isValidAccount(int accountNo);
	public String getUserRole(String emailId);
}
