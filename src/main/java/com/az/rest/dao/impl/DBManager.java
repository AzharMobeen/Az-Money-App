package com.az.rest.dao.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.RunScript;

public class DBManager {
	private static final Logger LOGGER = Logger.getLogger( DBManager.class.getName() );

	private static final String JDBC_DRIVER = "org.h2.Driver";
	private static final String DB_URL = "jdbc:h2:mem:restApi;DB_CLOSE_DELAY=-1";
	private static final String USER = "sa";
	private static final String PASS = "sa";

	private DBManager() {
		this.initData();
	}

	private static DBManager dbManager;

	public static DBManager getInstance() {
		if (dbManager == null)
			dbManager = new DBManager();
		return dbManager;
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
			LOGGER.log(Level.SEVERE, e.toString(),e);
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, e.toString(),e);
			throw new RuntimeException(e);
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, e.toString(),e);
			}
		}
	}

	private static JdbcConnectionPool getJdbcConnectionPool() {

		try {
			Class.forName(JDBC_DRIVER);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(),e);
		}
		return JdbcConnectionPool.create(DB_URL, USER, PASS);
	}
}
