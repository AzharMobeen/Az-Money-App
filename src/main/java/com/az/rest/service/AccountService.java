package com.az.rest.service;

import java.util.List;

import com.az.rest.model.Account;

public interface AccountService {

	List<Account> getAllAccounts();
	Account getAccountById(long id);
	Account createAccount(Account account);	
	List<Account> getUserAccountList(long userId);
}
