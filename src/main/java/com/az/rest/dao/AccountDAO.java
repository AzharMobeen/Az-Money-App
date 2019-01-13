package com.az.rest.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

import com.az.rest.model.Account;

public interface AccountDAO {

	List<Account> getAllAccounts();
	Account getAccountById(long id);
	Account createAccount(Account account);	
	boolean checkIBANAvailable(String IBAN);
	boolean updateAccountBalance(Connection connection,long accountId,BigDecimal balance);
	Account getAccountByIBANForUpdateBalance(Connection connection,String IBAN);
	List<Account> getUserAccountList(long userId);
}
