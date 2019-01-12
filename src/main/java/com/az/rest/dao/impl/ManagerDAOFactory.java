package com.az.rest.dao.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.h2.tools.RunScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.az.rest.dao.AbstractDAOFactory;
import com.az.rest.dao.AccountDAO;
import com.az.rest.dao.TransactionDAO;
import com.az.rest.dao.UserDAO;

public class ManagerDAOFactory extends AbstractDAOFactory{
	Logger log = LoggerFactory.getLogger(ManagerDAOFactory.class);	
	
	/*private static final String JDBC_DRIVER = "org.h2.Driver";
	private static final String DB_URL = "jdbc:h2:~/restApi";
	private static final String USER = "sa"; 
	private static final String PASS = "";*/
	private ManagerDAOFactory() {
		this.initData();
	}	
	private static ManagerDAOFactory managerDAOFactory;
	public static ManagerDAOFactory getInstance() {
		if(managerDAOFactory==null)
			managerDAOFactory = new ManagerDAOFactory();
		return managerDAOFactory;
	}
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {				
		Class.forName(JDBC_DRIVER);
		return	DriverManager.getConnection(DB_URL, USER, PASS);		
	}
	private void initData() {
		Connection connection = null;
		try {
			connection = getConnection();
			RunScript.execute(connection, new FileReader("src/test/resources/initialData.sql"));
		} catch (SQLException e) {
			log.error("populateTestData(): Error populating user data: ", e);
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			log.error("populateTestData(): Error finding test script file ", e);
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			 
	         try { 
	            if(connection!=null) connection.close(); 
	         } catch(SQLException se){ 
	        	 this.log.error(se.toString()); 
	         }
		}
	}
		
	public UserDAO getUserDAO() {
		return UserDAOImpl.getInstance();
	}
		
	public AccountDAO getAccountDAO() {
		return AccountDAOImpl.getInstance();
	}
	
	public TransactionDAO getTransactionDAO() {
		return TransactionDAOImpl.getInstance();
	}
}
