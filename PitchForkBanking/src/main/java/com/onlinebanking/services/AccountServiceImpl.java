package com.onlinebanking.services;

import java.util.List;

import javax.transaction.Transactional;

import com.onlinebanking.dao.AccountHome;
import com.onlinebanking.dao.UserHome;
import com.onlinebanking.models.Account;
import com.onlinebanking.models.User;

public class AccountServiceImpl implements AccountService{

	private AccountHome accountHome;
	private UserHome userHome;
	
	public void setAccountHome(AccountHome accountHome) {
		this.accountHome = accountHome;
	}
	
	public void setUserHome(UserHome userHome) {
		this.userHome = userHome;
	}
	
	@Transactional
	public void addAccount(Account a) {
		this.accountHome.persist(a);
	}
	
	@Transactional
	public List<Account> getUserAccounts(String userId) {
		return this.accountHome.getUserAccounts(userId);
	}
	
	@Transactional
	public Account getAccountById(int Id) {
		return this.accountHome.findById(Id);
	}
	
	@Transactional
	public List<Account> getAllUserAccounts() {
		List<Account> accounts = accountHome.getAllUserAccounts();
		
		for(Account account:accounts)
		{
			User u = this.userHome.findById(account.getUser().getUserId());
			account.setUser(u);
		}
		
		return accounts;
	}
}
