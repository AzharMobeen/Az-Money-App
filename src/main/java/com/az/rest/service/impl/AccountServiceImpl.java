package com.az.rest.service.impl;

import java.util.List;

import com.az.rest.dao.AccountDAO;
import com.az.rest.dao.impl.ManagerDAO;
import com.az.rest.model.Account;
import com.az.rest.service.AccountService;

public class AccountServiceImpl implements AccountService{
	
	private static AccountServiceImpl accountService;
	private final ManagerDAO managerDAOFactory;
	private final AccountDAO accountDAO;
	private AccountServiceImpl() {
		managerDAOFactory = ManagerDAO.getInstance();
		accountDAO = managerDAOFactory.getAccountDAO();		
	}		

	public static AccountServiceImpl getInstance() {
		if(accountService==null)
			accountService = new AccountServiceImpl();		
		return accountService;
	}
	
	public List<Account> getAllAccounts() {
		return accountDAO.getAllAccounts();
	}

	public Account getAccountById(long id) {				
		return accountDAO.getAccountById(id);
	}

	public Account createAccount(Account account) {
				
		if(accountDAO.checkIBANAvailable(account.getIBAN()))
			return accountDAO.createAccount(account);
		else
			return null;
	}

	public List<Account> getUserAccountList(long userId) {
		return accountDAO.getUserAccountList(userId);
	}
}
