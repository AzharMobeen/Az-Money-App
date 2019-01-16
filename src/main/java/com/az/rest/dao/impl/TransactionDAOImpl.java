package com.az.rest.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.az.rest.dao.TransactionDAO;
import com.az.rest.model.Transaction;

public class TransactionDAOImpl implements TransactionDAO {
	private static final Logger LOGGER = Logger.getLogger( TransactionDAOImpl.class.getName() );
	private static TransactionDAOImpl transactionDAOImpl;
	
	private TransactionDAOImpl() {}
	
	public static TransactionDAOImpl getInstance() {
		if(transactionDAOImpl==null)
			transactionDAOImpl = new TransactionDAOImpl();
		return transactionDAOImpl;
	}

	/*
	 * I'm saving transaction but connection.commit still not done
	 */	
	public Transaction transferMoney(Connection connection, Transaction transaction) {		
		PreparedStatement statement = null;		
		ResultSet resultSet = null;		
		try {
				
			String sql = "INSERT INTO Transaction ( FROM_IBAN, TO_IBAN, AMOUNT, TRANSACTION_FEE) VALUES(?,?,?,?)";
			statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, transaction.getFromIBAN());
			statement.setString(2, transaction.getToIBAN());
			statement.setBigDecimal(3, transaction.getAmount());
			statement.setBigDecimal(4, transaction.getTransactionFee());
			int insertion = statement.executeUpdate();
			
			if(insertion==1) {
				resultSet = statement.getGeneratedKeys();
				if(resultSet.next())
					transaction.setTransactionId(resultSet.getLong(1));
				resultSet.close();
			}
			
			statement.close(); 	        
		}catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(),e);
		}finally {
			try{ 
	            if(statement!=null) statement.close(); 
	         } catch(SQLException e) { 
	        	 LOGGER.log(Level.SEVERE, e.toString(),e);
	         } 
	         
		}		
		return transaction;
	}
			
	public List<Transaction> getAllTransaction() {
		Connection connection = null;
		Statement  statement = null;
		ResultSet resultSet = null;
		List<Transaction> transactionList= new ArrayList<>();
		try {
			connection = DBManager.getConnection();
			statement = connection.createStatement();
			String sql = "SELECT * FROM Transaction";
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {
				Transaction transaction = new Transaction(resultSet.getLong("TRANSACTIONID"), resultSet.getString("FROM_IBAN"),
						resultSet.getString("TO_IBAN"), resultSet.getBigDecimal("AMOUNT"),resultSet.getBigDecimal("TRANSACTION_FEE"));
				transactionList.add(transaction);
			}
			
			resultSet.close();
			statement.close(); 
	        connection.close();
		}catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(),e);
		}finally {
			try{ 
	            if(statement!=null) statement.close(); 
	         } catch(SQLException e) { 
	        	 LOGGER.log(Level.SEVERE, e.toString(),e);
	         } 
	         try { 
	            if(connection!=null) connection.close(); 
	         } catch(SQLException e){ 
	        	 LOGGER.log(Level.SEVERE, e.toString(),e);
	         }
		}
		return transactionList;
	}
	
	public Transaction getTransactionById(long id) {
		Connection connection = null;
		PreparedStatement  statement = null;
		ResultSet resultSet = null;
		Transaction transaction= null;
		try {
			connection = DBManager.getConnection();
			String sql = "SELECT * FROM Transaction WHERE TRANSACTIONID = ?";
			statement = connection.prepareStatement(sql);
			statement.setLong(1, id);			
			resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				transaction = new Transaction(resultSet.getLong("TRANSACTIONID"), resultSet.getString("FROM_IBAN"),
						resultSet.getString("TO_IBAN"), resultSet.getBigDecimal("AMOUNT"),resultSet.getBigDecimal("TRANSACTION_FEE"));				
			}
			resultSet.close();
			statement.close(); 
			connection.close();
		}catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(),e);
		}finally {
			try{ 
	            if(statement!=null) statement.close(); 
	         } catch(SQLException e) { 
	        	 LOGGER.log(Level.SEVERE, e.toString(),e);
	         } 
	         try { 
	            if(connection!=null) connection.close(); 
	         } catch(SQLException e){ 
	        	 LOGGER.log(Level.SEVERE, e.toString(),e); 
	         }
		}
		return transaction;
	}

	public List<Transaction> getUserTrasactionByUserId(long userId) {

		Connection connection = null;
		PreparedStatement  statement = null;
		ResultSet resultSet = null;
		List<Transaction> transactionList= new ArrayList<>();
		try {
			connection = DBManager.getConnection();
			String sql = "SELECT transaction.* FROM Transaction transaction JOIN Account account on transaction.FROM_IBAN = account.IBAN "
					+ "	JOIN User user on account.USERID = user.USERID WHERE user.USERID = ?";
			statement = connection.prepareStatement(sql);
			statement.setLong(1, userId);
			
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				Transaction transaction = new Transaction(resultSet.getLong("TRANSACTIONID"), resultSet.getString("FROM_IBAN"),
						resultSet.getString("TO_IBAN"), resultSet.getBigDecimal("AMOUNT"),resultSet.getBigDecimal("TRANSACTION_FEE"));
				transactionList.add(transaction);
			}
			
			resultSet.close();
			statement.close(); 
	        connection.close();
		}catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(),e);
		}finally {
			try{ 
	            if(statement!=null) statement.close(); 
	         } catch(SQLException e) { 
	        	 LOGGER.log(Level.SEVERE, e.toString(),e);
	         } 
	         try { 
	            if(connection!=null) connection.close(); 
	         } catch(SQLException e){ 
	        	 LOGGER.log(Level.SEVERE, e.toString(),e); 
	         }
		}
		return transactionList;
	}
	
}
