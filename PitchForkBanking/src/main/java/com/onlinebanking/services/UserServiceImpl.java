package com.onlinebanking.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlinebanking.dao.AccountHome;
import com.onlinebanking.dao.RequestsHome;
import com.onlinebanking.dao.UserHome;
import com.onlinebanking.helpers.CryptoHelper;
import com.onlinebanking.helpers.Response;
import com.onlinebanking.models.Account;
import com.onlinebanking.models.RequestStatus;
import com.onlinebanking.models.Requests;
import com.onlinebanking.models.User;

@Service
public class UserServiceImpl implements UserService {
	
	private UserHome userHome;
	private AccountHome accountHome;
	private RequestsHome requestsHome;
	
	public void setAccountHome(AccountHome accountHome) {
		this.accountHome = accountHome;
	}

	public void setUserHome(UserHome userDAO) {
		this.userHome = userDAO;
	}

	@Override
	@Transactional
	public void addUser(User p) {
		p.setPassword(CryptoHelper.getEncryptedString(p.getPassword()));
		this.userHome.persist(p);
		Account a = new Account();
		a.setAccountType("Checking");
		a.setAmount(1000);
		a.setUser(p);
		this.accountHome.persist(a);
		
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
	public List<User> listNewUsers() {
		return this.userHome.findAllNewRegistrations();
	}
	
	@Override
	@Transactional
	public List<User> listCustomers() {
		return this.userHome.findAllCustomers();
	}
	
	@Override
	@Transactional
	public List<User> listEmployees() {
		return this.userHome.findAllEmployees();
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
	
	@Override
	@Transactional
	public User getUserByEmailId(String emailId) {
		return this.userHome.getUserByEmailId(emailId);
	}

	@Override
	@Transactional
	public Response isValidUserAccount(int accountNo, String userId) {
		Response s = this.isValidAccount(accountNo);
		
		if (s.getStatus().equals("success")) {
			List<Account> list = this.accountHome.getUserAccounts(userId);
			for (Account account : list) {
				if (accountNo == account.getAccountNum()) {
					return new Response("success", "Account is own by current user");
				}
			}
			
			return new Response("error", "Permission denied. Please select an account to proceed!!");
		} else {
			return s;
		}
	}
	
	@Override
	@Transactional
	public Response isValidAccount(int accountNo) {
		try {
			Account a = this.accountHome.findById(accountNo);
			if (a == null) {
				return new Response("error", "Invalid account selection");
			}
		} catch (Exception e) {
			return new Response("error", "Account is Invalid.");
		}
		
		return new Response("success", "Account is valid");
	}

	@Override
	@Transactional
	public String getUserRole(String emailId) {
		// TODO Auto-generated method stub
		User p = userHome.getUserByEmailId(emailId);
		return p.getRole();
	}
	
	@Override
	@Transactional
	public Response updateUserRegistrationFlag(String id, String status) {
		User u = this.userHome.findById(id);

		if (status.equals("approve")) {
			u.setEnabled(1);
			this.userHome.merge(u);
			return new Response("success", "User Registered!");
		} else {
			removeUser(id);
			return new Response("success", "User Declined!");
		}
	}
}
