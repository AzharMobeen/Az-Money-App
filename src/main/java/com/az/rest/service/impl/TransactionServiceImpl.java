package com.az.rest.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.az.rest.dao.AccountDAO;
import com.az.rest.dao.TransactionDAO;
import com.az.rest.dao.impl.AccountDAOImpl;
import com.az.rest.dao.impl.DBManager;
import com.az.rest.dao.impl.TransactionDAOImpl;
import com.az.rest.model.Account;
import com.az.rest.model.Transaction;
import com.az.rest.service.TransactionService;

public class TransactionServiceImpl implements TransactionService{
	private static final Logger LOGGER = Logger.getLogger( TransactionServiceImpl.class.getName() );
	
	private static TransactionServiceImpl transactionService;		
	private final AccountDAO accountDAO;
	private final TransactionDAO transactionDAO;
	
	private TransactionServiceImpl() {		
		accountDAO = AccountDAOImpl.getInstance();
		transactionDAO = TransactionDAOImpl.getInstance();	
	}
	
	public static TransactionServiceImpl getInstance() {
		
		if(transactionService==null)
			transactionService = new TransactionServiceImpl();		
		return transactionService;
	}
	
	public Response transferMoney(Transaction transaction) {
		
		Account fromAccount = null;
		Account toAccount = null;
		Connection connection = null;		
		String output = null;
		boolean updateFlage = false;
		Response response = null;
		try {
			
			//Both IBAN (FromIBAN and ToIBAN) should be different
			if(!transaction.getFromIBAN().equals(transaction.getToIBAN())) {
				
				connection = DBManager.getConnection();
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
						response = Response.status(Status.NOT_FOUND).entity(output).build();
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
				LOGGER.log(Level.SEVERE, e.toString(),e);
		}finally {
			try {
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, e.toString(),e);
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
	
	public List<Transaction> getUserTransactionListByUserId(long userId) {
		List<Transaction> transactionList = transactionDAO.getUserTrasactionByUserId(userId);
		return transactionList;
	}
}
