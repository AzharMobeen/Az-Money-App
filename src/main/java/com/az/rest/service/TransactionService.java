package com.az.rest.service;

import java.util.List;

import javax.ws.rs.core.Response;

import com.az.rest.model.Transaction;

public interface TransactionService {

	List<Transaction> getAllTransactions();
	Transaction getTransactionById(long id);	
	Response transferMoney(Transaction transaction); 
	List<Transaction> getUserTransactionListByUserId(long userId);
}
