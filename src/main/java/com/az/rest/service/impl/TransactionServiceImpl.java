package com.az.rest.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.az.rest.dao.AccountDAO;
import com.az.rest.dao.TransactionDAO;
import com.az.rest.dao.impl.ManagerDAO;
import com.az.rest.model.Account;
import com.az.rest.model.Transaction;
import com.az.rest.service.TransactionService;

public class TransactionServiceImpl implements TransactionService{
	Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);
	
	private static TransactionServiceImpl transactionService;	
	private final ManagerDAO managerDAO;
	private final AccountDAO accountDAO;
	private final TransactionDAO transactionDAO;
	
	private TransactionServiceImpl() {
		managerDAO = ManagerDAO.getInstance();
		accountDAO = managerDAO.getAccountDAO();
		transactionDAO = managerDAO.getTransactionDAO();	
	}
	
	public static TransactionServiceImpl getInstance() {
		
		if(transactionService==null)
			transactionService = new TransactionServiceImpl();		
		return transactionService;
	}

	public Response moneyTransfer(Transaction transaction) {
		
		Account fromAccount = null;
		Account toAccount = null;
		Connection connection = null;		
		String output = null;
		boolean updateFlage = false;
		Response response = null;
		try {
			
			//Both IBAN (FromIBAN and ToIBAN) should be different
			if(!transaction.getFromIBAN().equals(transaction.getToIBAN())) {
				
				connection = ManagerDAO.getConnection();
				connection.setAutoCommit(false);
				fromAccount = accountDAO.getAccountByIBANForUpdateBalance(connection,transaction.getFromIBAN());
								
				// Account must have balance more than transaction amount  
				if(fromAccount!=null && fromAccount.getBalance().compareTo(transaction.getAmount())>0) {				
					
					toAccount = accountDAO.getAccountByIBANForUpdateBalance(connection,transaction.getToIBAN());
					if(toAccount!=null) {
						//Here we can also add transfer fee if we want or some times different currency account we need to charge
						BigDecimal transactionFee = BigDecimal.ZERO;
						
						BigDecimal fromAccountBalance = fromAccount.getBalance();
						fromAccountBalance = fromAccountBalance.subtract(transaction.getAmount());
						fromAccountBalance = fromAccountBalance.subtract(transactionFee);
						
						updateFlage = accountDAO.updateAccountBalance(connection,fromAccount.getAccountId(),fromAccountBalance);

						BigDecimal toAccountBalance = toAccount.getBalance();
						toAccountBalance = toAccountBalance.add(transaction.getAmount());												
						updateFlage = accountDAO.updateAccountBalance(connection,toAccount.getAccountId(),toAccountBalance);
						
						if(updateFlage) {
							transaction.setTransactionFee(transactionFee);
							transaction = transactionDAO.transferMoney(connection, transaction);
							output = "Transaction successfully done and Transaction Id is :: "+transaction.getTransactionId()+" ::";
							response = Response.status(Status.CREATED).entity(output).build();
							connection.commit();
						}
					}else {
						output = "To IBAN Not found";
						response = Response.status(Status.NOT_ACCEPTABLE).entity(output).build();
					}
				}else { 
					output = "From IBAN not valid for this transaction";
					response = Response.status(Status.NOT_ACCEPTABLE).entity(output).build();
				}
			}else {
				output = "Both IBAN (from,to) should be different";
				response = Response.status(Status.NOT_ACCEPTABLE).entity(output).build();
			}
		} catch (Exception e) {
			log.error(e.toString());
		}finally {
			try {
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				log.error(e.toString());
			}
		}				
		return response;
	}
	
	public List<Transaction> getAllTransactions() {
		return transactionDAO.getAllTransaction();
	}
	
	public Transaction getTransactionById(long id) {
		return transactionDAO.getTransactionById(id);
	}

	public List<Transaction> getTransactionByFromIBAN(String IBAN) {
		return transactionDAO.getTransactionByFromIBAN(IBAN);
	}
}
