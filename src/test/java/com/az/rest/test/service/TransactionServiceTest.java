package com.az.rest.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;

import com.az.rest.dao.impl.DBManager;
import com.az.rest.model.Transaction;
import com.az.rest.service.TransactionService;
import com.az.rest.service.impl.TransactionServiceImpl;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class TransactionServiceTest {	
	
	private TransactionService transactionService;
	@Before
	public void initDBWithTableData() {
		DBManager.getInstance();
		transactionService = TransactionServiceImpl.getInstance();
	}		
	
	@Test
	public void getUserByIdSuccessTest() {
		Transaction transaction = transactionService.getTransactionById(1);		
		assertNotNull("Should return transaction object", transaction);
	}
	
	@Test
	public void getTransactionByIdFailedTest() {
		Transaction transaction = transactionService.getTransactionById(10000);
		assertNull("Should return null",transaction);
	}

	@Test
	public void transferMoneySuccessTest() {
		Transaction transaction= new Transaction("PK111222334455502","AE111222334455502", new BigDecimal(2000),new BigDecimal(20));
		Response response = transactionService.transferMoney(transaction);		
		assertEquals("Should return status 201", Status.CREATED.getStatusCode(), response.getStatus());
	}
	
	/*
	 * From IBAN doesn't have enough balance
	 */	
	@Test
	public void transferMoneyFailedTest1() {
		Transaction transaction= new Transaction("PK111222334455502","AE111222334455502", new BigDecimal(2000000),new BigDecimal(20));
		Response response = transactionService.transferMoney(transaction);		
		assertEquals("Should return status 406 because From IBAN doesn't have enough balance", Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
	}
	
	/*
	 * To IBAN Not found
	 */		
	@Test
	public void transferMoneyFailedTest2() {
		Transaction transaction= new Transaction("PK111222334455502","AE55502", new BigDecimal(2070),new BigDecimal(20));
		Response response = transactionService.transferMoney(transaction);		
		assertEquals("Should return status 404 because To IBAN Not found", Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}
	
	/*
	 * Both IBAN (from,to) should be different
	 * */
	@Test
	public void transferMoneyFailedTest3() {
		Transaction transaction= new Transaction("PK111222334455502","PK111222334455502", new BigDecimal(200),new BigDecimal(20));
		Response response = transactionService.transferMoney(transaction);		
		assertEquals("Should return status 406 because Both IBAN (from,to) are same", Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void getUserTransactionListSuccessTest() {		
		List<Transaction> transactionList = transactionService.getUserTransactionListByUserId(2);		
		assertEquals("Should return user transaction list", false,transactionList.isEmpty());
		assertNotEquals(0,transactionList.size());
	}
	
	/*
	 * User ID not exist
	 * */
	@Test
	public void getUserTransactionListFailedTest() throws JsonParseException, JsonMappingException, IOException{		
		List<Transaction> transactionList = transactionService.getUserTransactionListByUserId(2000); 
		assertEquals("Should return user transaction list", true,transactionList.isEmpty());
		assertEquals(0,transactionList.size());
	}
	
	@Test
	public void fetchAll() {
		List<Transaction> transactionList = transactionService.getAllTransactions();
		assertEquals("Should return transaction list", false,transactionList.isEmpty());
		assertNotEquals(0,transactionList.size());
	}
}