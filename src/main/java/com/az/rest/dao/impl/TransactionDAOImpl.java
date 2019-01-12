package com.az.rest.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.az.rest.dao.TransactionDAO;
import com.az.rest.model.Transaction;

public class TransactionDAOImpl implements TransactionDAO {
	Logger log = LoggerFactory.getLogger(TransactionDAOImpl.class);
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
			log.error(e.toString());
		}finally {
			try{ 
	            if(statement!=null) statement.close(); 
	         } catch(SQLException se2) { 
	        	 this.log.error(se2.toString());
	         } 
	         
		}		
		return transaction;
	}

	
	public List<Transaction> getTransactionByFromIBAN(String IBAN) {
	
		Connection connection = null;
		PreparedStatement  statement = null;
		ResultSet resultSet = null;
		List<Transaction> transactionList= new ArrayList<>();
		try {
			connection = ManagerDAOFactory.getConnection();
			String sql = "SELECT * FROM Transaction WHERE FROM_IBAN = ?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, IBAN);
			
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
			log.error(e.toString());
		}finally {
			try{ 
	            if(statement!=null) statement.close(); 
	         } catch(SQLException se2) { 
	        	 this.log.error(se2.toString());
	         } 
	         try { 
	            if(connection!=null) connection.close(); 
	         } catch(SQLException se){ 
	        	 this.log.error(se.toString()); 
	         }
		}
		return transactionList;
	}
	
	public List<Transaction> getAllTransaction() {
		Connection connection = null;
		Statement  statement = null;
		ResultSet resultSet = null;
		List<Transaction> transactionList= new ArrayList<>();
		try {
			connection = ManagerDAOFactory.getConnection();
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
			log.error(e.toString());
		}finally {
			try{ 
	            if(statement!=null) statement.close(); 
	         } catch(SQLException se2) { 
	        	 this.log.error(se2.toString());
	         } 
	         try { 
	            if(connection!=null) connection.close(); 
	         } catch(SQLException se){ 
	        	 this.log.error(se.toString()); 
	         }
		}
		return transactionList;
	}
}
