package com.az.rest.dao;

public abstract class AbstractDAOFactory {

	public static final String JDBC_DRIVER = "org.h2.Driver";
	public static final String DB_URL = "jdbc:h2:mem:restApi;DB_CLOSE_DELAY=-1";
	public static final String USER = "sa"; 
	public static final String PASS = "sa";
	
	public abstract UserDAO getUserDAO();
	public abstract AccountDAO getAccountDAO();
	public abstract TransactionDAO getTransactionDAO();

}
