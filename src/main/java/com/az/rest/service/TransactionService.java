package com.az.rest.service;

import java.util.List;

import javax.ws.rs.core.Response;

import com.az.rest.model.Transaction;

public interface TransactionService {

	List<Transaction> getAllTransactions();
	List<Transaction> getTransactionByFromIBAN(String IBAN);
	Transaction getTransactionById(long id);	
	Response moneyTransfer(Transaction transaction); 
}
