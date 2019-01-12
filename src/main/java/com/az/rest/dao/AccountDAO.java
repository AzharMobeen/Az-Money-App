package com.az.rest.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

import com.az.rest.model.Account;

public interface AccountDAO {

	public List<Account> getAllAccounts();
	public Account getAccountById(long id);
	public Account createAccount(Account account);
	public boolean updateAccount(Account account);
	public boolean deleteAccount(long id);
	public boolean checkIBANAvailable(String IBAN);
	public boolean updateAccountBalance(Connection connection,long accountId,BigDecimal balance);
	public Account getAccountByIBANForUpdateBalance(Connection connection,String IBAN);
}
