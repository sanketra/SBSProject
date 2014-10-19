package com.onlinebanking.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlinebanking.dao.UserHome;
import com.onlinebanking.helpers.CryptoHelper;
import com.onlinebanking.models.User;

@Service
public class UserServiceImpl implements UserService {
	
	private UserHome userHome;

	public void setUserHome(UserHome userDAO) {
		this.userHome = userDAO;
	}

	@Override
	@Transactional
	public void addUser(User p) {
		p.setPassword(CryptoHelper.getEncryptedString(p.getPassword()));
		this.userHome.persist(p);
	}

	@Override
	@Transactional
	public void updateUser(User p) {
		this.userHome.merge(p);
	}

	@Override
	@Transactional
	public List<User> listUsers() {
		return this.userHome.findAll();
	}

	@Override
	@Transactional
	public User getUserById(String id) {
		return this.userHome.findById(id);
	}

	@Override
	@Transactional
	public void removeUser(String id) {
		this.userHome.delete(this.userHome.findById(id));
	}

}
