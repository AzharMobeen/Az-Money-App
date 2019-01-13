package com.az.rest.dao.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.RunScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.az.rest.dao.AccountDAO;
import com.az.rest.dao.TransactionDAO;
import com.az.rest.dao.UserDAO;

public class ManagerDAO {
	static Logger log = LoggerFactory.getLogger(ManagerDAO.class);

	private static final String JDBC_DRIVER = "org.h2.Driver";
	private static final String DB_URL = "jdbc:h2:mem:restApi;DB_CLOSE_DELAY=-1";
	private static final String USER = "sa";
	private static final String PASS = "sa";

	private ManagerDAO() {
		this.initData();
	}

	private static ManagerDAO managerDAOFactory;

	public static ManagerDAO getInstance() {
		if (managerDAOFactory == null)
			managerDAOFactory = new ManagerDAO();
		return managerDAOFactory;
	}

	public static Connection getConnection() throws SQLException {
		return getJdbcConnectionPool().getConnection();
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
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException se) {
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

	private static JdbcConnectionPool getJdbcConnectionPool() {

		try {
			Class.forName(JDBC_DRIVER);
		} catch (Exception e) {
			log.error(e.toString());
		}
		return JdbcConnectionPool.create(DB_URL, USER, PASS);
	}
}
