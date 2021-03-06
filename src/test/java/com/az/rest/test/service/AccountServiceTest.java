package com.az.rest.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.az.rest.dao.impl.DBManager;
import com.az.rest.model.Account;
import com.az.rest.service.AccountService;
import com.az.rest.service.impl.AccountServiceImpl;

public class AccountServiceTest {		
	
	private AccountService accountService;
	
	@Before
	public void initDBWithTableData() {
		DBManager.getInstance();
		accountService = AccountServiceImpl.getInstance();
	}
	
	/*
	 * Dummy data inserted before test so it will be successful
	 * */
	@Test
	public void getAccountByIdSuccessTest() {		
		Account account = accountService.getAccountById(Long.valueOf(1));
		assertNotNull("Should return account object", account);		
	}
	
	/*
	 * Dummy data inserted before test but not up till accountId=100 so it will be successful
	 * */
	@Test
	public void getByIdFailedTest() {
		Account account = accountService.getAccountById(Long.valueOf(100));
		assertNull("Should return null object", account);		
	}

	/*
	 * IBAN = AE12345678954 not exist in Account Table so it will be successful
	 * */
	@Test
	public void createSuccessTest() {
		Account account = new Account("AE12345678954", Long.valueOf(1), "AED", new BigDecimal(200));
		account = accountService.createAccount(account);		
		assertNotNull("Should return account object", account);
	}
	
	/*
	 * IBAN = AE112233445501 already exist in DB so it will be successful
	 * */	
	@Test
	public void createFailedTest() {
		Account account = new Account("AE112233445501", Long.valueOf(1), "AED", new BigDecimal(200));
		account = accountService.createAccount(account);		
		assertNull("Should return null object", account);
	}

	/*
	 * Dummy data inserted before test so it will be successful
	 * */
	@Test
	public void userAccoutListSuccessTest() {		
		List<Account> accountList = accountService.getUserAccountList(1);		
		assertNotNull("Should return user account list", accountList.size());
		assertNotEquals(0, accountList.size());
	}
	
	/*
	 * Dummy data inserted before test but not up till userId=1000 so it will be successful
	 * */
	@Test
	public void userAccoutListFailedTest(){			
		List<Account> accountList = accountService.getUserAccountList(1000);
		assertEquals(0, accountList.size());
	}	

	/*
	 * Dummy data inserted before test so it will be successful
	 * */
	@Test
	public void getAllAccounts() {
		List<Account> accountList = accountService.getAllAccounts();		
		assertNotNull("Should return account list", accountList.size());
		assertNotEquals(0, accountList.size());
	}
}