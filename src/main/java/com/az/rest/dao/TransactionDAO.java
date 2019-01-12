package com.az.rest.dao;

import java.sql.Connection;
import java.util.List;

import com.az.rest.model.Transaction;

public interface TransactionDAO {

	public Transaction transferMoney(Connection connection,Transaction transaction);
	public List<Transaction> getTransactionByFromIBAN(String IBAN);
	public List<Transaction> getAllTransaction();
}
