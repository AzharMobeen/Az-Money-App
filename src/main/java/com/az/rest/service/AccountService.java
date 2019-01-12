package com.az.rest.service;

import java.util.List;

import com.az.rest.model.Account;

public interface AccountService {

	public List<Account> getAllAccounts();
	public Account getAccountById(long id);
	public Account createAccount(Account account);
	public boolean updateAccount(long accountId,Account account);
	public boolean deleteAccount(long id);
}
