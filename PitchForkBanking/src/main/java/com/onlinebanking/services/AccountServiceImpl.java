package com.onlinebanking.services;

import java.util.List;

import javax.transaction.Transactional;

import com.onlinebanking.dao.AccountHome;
import com.onlinebanking.models.Account;

public class AccountServiceImpl implements AccountService{

	private AccountHome accountHome;
	
	
	public void setAccountHome(AccountHome accountHome) {
		this.accountHome = accountHome;
	}
	
	@Transactional
	public void addAccount(Account a) {
		this.accountHome.persist(a);
	}
	
	@Transactional
	public List<Account> getUserAccounts(String userId) {
		return this.accountHome.getUserAccounts(userId);
	}
}
