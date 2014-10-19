package com.onlinebanking.services;

import java.util.List;

import com.onlinebanking.models.User;

public interface UserService {

	public void addUser(User p);
	public void updateUser(User p);
	public List<User> listUsers();
	public User getUserById(String id);
	public void removeUser(String id);
	
}
