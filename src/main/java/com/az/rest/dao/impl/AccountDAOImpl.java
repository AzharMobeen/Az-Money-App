package com.az.rest.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.az.rest.dao.AccountDAO;
import com.az.rest.model.Account;

public class AccountDAOImpl implements AccountDAO {

	Logger log = LoggerFactory.getLogger(AccountDAOImpl.class);
	
	private AccountDAOImpl() {}
	private static AccountDAOImpl accountDAOImpl;	
	public static AccountDAOImpl getInstance() {
		if(accountDAOImpl==null)
			accountDAOImpl = new AccountDAOImpl();		
		return accountDAOImpl;
	}
	
	public List<Account> getAllAccounts() {
		Connection connection = null;
		Statement  statement = null;
		ResultSet resultSet = null;
		List<Account> accountList= new ArrayList<>();
		try {
			connection = ManagerDAO.getConnection();
			statement = connection.createStatement();
			String sql = "SELECT * FROM Account";
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {
				Account account = new Account(resultSet.getLong("ACCOUNTID"), resultSet.getString("IBAN"),
						resultSet.getLong("USERID"), resultSet.getString("ACCOUNT_TYPE"),resultSet.getBigDecimal("BALANCE"));
				accountList.add(account);
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
		return accountList;
	}
	
	public Account getAccountById(long id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Account account = null;
		try {
			connection = ManagerDAO.getConnection();			
			String sql = "SELECT * FROM Account WHERE ACCOUNTID =?";
			statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();			
			if(resultSet.next()) {
				account = new Account(resultSet.getLong("ACCOUNTID"), resultSet.getString("IBAN"),
						resultSet.getLong("USERID"), resultSet.getString("ACCOUNT_TYPE"),resultSet.getBigDecimal("BALANCE"));				
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
		return account;
	}

	public Account createAccount(Account account) {
		Connection connection = null;
		PreparedStatement statement = null;		
		ResultSet resultSet = null;
		try {
			connection = ManagerDAO.getConnection();			
			String sql = "INSERT INTO Account (IBAN, USERID, ACCOUNT_TYPE, BALANCE) VALUES(?,?,?,?)";
			statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, account.getIBAN());
			statement.setLong(2, account.getUserId());
			statement.setString(3, account.getAccountType());
			statement.setBigDecimal(4, account.getBalance());
			int insertion = statement.executeUpdate();	
			
			if(insertion==1) {
				resultSet = statement.getGeneratedKeys();
				account.setAccountId(resultSet.getLong(1));
				resultSet.close();
			}
			
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
		return account;
	}	
	
	public boolean checkIBANAvailable(String IBAN) {
		boolean checkIBAN = true;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;		
		try {
			connection = ManagerDAO.getConnection();			
			String sql = " SELECT * FROM Account WHERE IBAN = ? ";
			statement = connection.prepareStatement(sql);
			statement.setString(1, IBAN);
			resultSet = statement.executeQuery();			
			if(resultSet.next()) {
				checkIBAN = false;		
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
		return checkIBAN;
	}

	/*
	 * Account will be lock for balance update
	 * 
	 * */
	public Account getAccountByIBANForUpdateBalance(Connection connection,String IBAN) {
		String sql = "SELECT * FROM Account WHERE IBAN = ? FOR UPDATE";
		Account account= null;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, IBAN);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				account = new Account(resultSet.getLong("ACCOUNTID"), resultSet.getString("IBAN"), 
						resultSet.getLong("USERID"), resultSet.getString("ACCOUNT_TYPE"), 
						resultSet.getBigDecimal("BALANCE"));
			}
			resultSet.close();				
		} catch (SQLException e) {
			log.error(e.toString());
		}
		return account;
	}

	public boolean updateAccountBalance(Connection connection,long accountId,BigDecimal balance) {

		PreparedStatement preparedStatement = null;
		boolean updateFlag = true;
		try {			
			String sql = "UPDATE Account SET BALANCE = ? WHERE ACCOUNTID = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setBigDecimal(1, balance);
			preparedStatement.setLong(2, accountId);
			int insertion = preparedStatement.executeUpdate();				
			if(insertion!=1) {
				updateFlag = false;
			}
			
			preparedStatement.close(); 
	        
		}catch (Exception e) {
			log.error(e.toString());
		}finally {
			try{ 
	            if(preparedStatement!=null) preparedStatement.close(); 
	         } catch(SQLException se2) { 
	        	 this.log.error(se2.toString());
	         } 	         
		}		
		return updateFlag;
	}

	public List<Account> getUserAccountList(long userId) {
		Connection connection = null;
		PreparedStatement  statement = null;
		ResultSet resultSet = null;
		List<Account> accountList= new ArrayList<>();
		try {
			connection = ManagerDAO.getConnection();			
			String sql = "SELECT * FROM Account WHERE USERID = ?";
			statement = connection.prepareStatement(sql);
			statement.setLong(1, userId);
			
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {
				Account account = new Account(resultSet.getLong("ACCOUNTID"), resultSet.getString("IBAN"),
						resultSet.getLong("USERID"), resultSet.getString("ACCOUNT_TYPE"),resultSet.getBigDecimal("BALANCE"));
				accountList.add(account);
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
		return accountList;
	}
}
