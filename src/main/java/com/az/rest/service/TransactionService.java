package com.az.rest.service;

import java.util.List;

import javax.ws.rs.core.Response;

import com.az.rest.model.Transaction;

public interface TransactionService {

	public List<Transaction> getAllTransactions();
	public List<Transaction> getTransactionByFromIBAN(String IBAN);
	/*public Transaction getTransactionById(long id);
	public Transaction createTransaction(Transaction transaction);
	public boolean updateTransaction(Transaction transaction);
	public boolean deleteTransaction(long id);*/
	public Response moneyTransfer(Transaction transaction); 
}
