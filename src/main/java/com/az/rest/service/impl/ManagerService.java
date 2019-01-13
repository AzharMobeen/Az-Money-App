package com.az.rest.service.impl;

import com.az.rest.service.AccountService;
import com.az.rest.service.TransactionService;
import com.az.rest.service.UserService;

public class ManagerService {

	private ManagerService() {}
	private static ManagerService managerServiceFactory;	
	public static ManagerService getInstance() {
		if(managerServiceFactory==null)
			managerServiceFactory = new ManagerService();
		return managerServiceFactory;
	}
	
	public UserService getUserService() {
		return UserServiceImpl.getInstance();
	}
	
	public AccountService getAccountService() {
		return AccountServiceImpl.getInstance();
	}
	
	public TransactionService getTransactionService() {
		return TransactionServiceImpl.getInstance();
	}
}
