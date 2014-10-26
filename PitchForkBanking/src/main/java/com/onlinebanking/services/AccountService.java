package com.onlinebanking.services;

import java.util.List;

import com.onlinebanking.models.Account;

public interface AccountService {
	public void addAccount(Account a);
	public List<Account> getUserAccounts(String userId);
	
}
