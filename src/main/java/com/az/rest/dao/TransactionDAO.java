package com.az.rest.dao;

import java.sql.Connection;
import java.util.List;

import com.az.rest.model.Transaction;

public interface TransactionDAO {

	Transaction transferMoney(Connection connection,Transaction transaction);
	List<Transaction> getTransactionByFromIBAN(String IBAN);
	List<Transaction> getAllTransaction();
	Transaction getTransactionById(long id);
}
