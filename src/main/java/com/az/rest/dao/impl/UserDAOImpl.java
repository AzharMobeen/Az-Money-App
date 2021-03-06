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

import com.az.rest.dao.UserDAO;
import com.az.rest.model.User;

public class UserDAOImpl implements UserDAO{

	private static final Logger LOGGER = Logger.getLogger( UserDAOImpl.class.getName() );		
	
	private static UserDAO userDAO;
	
	private UserDAOImpl() {}
	
	public static UserDAO getInstance() {
		if(userDAO==null)
			userDAO = new UserDAOImpl();
		return userDAO;
	}
	
	
	public List<User> getAllUsers() {
		Connection connection = null;
		Statement  statement = null;
		ResultSet resultSet = null;
		List<User> userList= new ArrayList<>();
		try {
			connection = DBManager.getConnection();
			statement = connection.createStatement();
			String sql = "SELECT * FROM User";
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {
				User user = new User(resultSet.getLong("USERID"), resultSet.getString("USERNAME"),
						resultSet.getString("FULLNAME"), resultSet.getString("EMAIL"));
				userList.add(user);
			}
			
			resultSet.close();
			statement.close(); 
	        connection.close();
		}catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(),e);
		}finally {
			try{ 
	            if(statement!=null) statement.close(); 
	         } catch(SQLException se2) { 
	        	 LOGGER.log(Level.SEVERE, se2.toString(),se2);
	         } 
	         try { 
	            if(connection!=null) connection.close(); 
	         } catch(SQLException se){ 
	        	 LOGGER.log(Level.SEVERE, se.toString(),se);
	         }
		}
		return userList;
	}

	@Override
	public User getUserById(long id) {
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		User user = null;
		try {
			connection = DBManager.getConnection();			
			String sql = "SELECT * FROM User WHERE USERID =?";
			statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();			
			if(resultSet.next()) {
				user = new User(resultSet.getLong("USERID"), resultSet.getString("USERNAME"),
						resultSet.getString("FULLNAME"), resultSet.getString("EMAIL"));				
			}
			
			resultSet.close();
			statement.close(); 
	        connection.close();
		}catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(),e);
		}finally {
			try{ 
	            if(statement!=null) statement.close(); 
	         } catch(SQLException se2) { 
	        	 LOGGER.log(Level.SEVERE, se2.toString(),se2);
	         } 
	         try { 
	            if(connection!=null) connection.close(); 
	         } catch(SQLException se){ 
	        	 LOGGER.log(Level.SEVERE, se.toString(),se);
	         }
		}
		return user;
	}

	@Override
	public User createUser(User user) {
		Connection connection = null;
		PreparedStatement statement = null;		
		ResultSet resultSet = null;
		try {
			connection = DBManager.getConnection();			
			String sql = "INSERT INTO User (USERNAME,FULLNAME,EMAIL) VALUES(?,?,?)";
			statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getUserName());
			statement.setString(2, user.getFullName());
			statement.setString(3, user.getEmail());
			int insertion = statement.executeUpdate();
			
			if(insertion==1) {
				resultSet = statement.getGeneratedKeys();
				if(resultSet.next())
					user.setUserId(resultSet.getLong(1));
				resultSet.close();
			}
			
			statement.close(); 
	        connection.close();
		}catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(),e);
		}finally {
			try{ 
	            if(statement!=null) statement.close(); 
	         } catch(SQLException se2) { 
	        	 LOGGER.log(Level.SEVERE, se2.toString(),se2);
	         } 
	         try { 
	            if(connection!=null) connection.close(); 
	         } catch(SQLException se){ 
	        	 LOGGER.log(Level.SEVERE, se.toString(),se);
	         }
		}		
		return user;
	}
	
	public boolean updateUser(User user) {
		Connection connection = null;
		PreparedStatement statement = null;		
		boolean updateFlag = true;
		try {
			connection = DBManager.getConnection();			
			String sql = "UPDATE User SET USERNAME=? , FULLNAME = ? , EMAIL = ? WHERE USERID = ?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, user.getUserName());
			statement.setString(2, user.getFullName());
			statement.setString(3, user.getEmail());
			statement.setLong(4, user.getUserId());
			int insertion = statement.executeUpdate();	
			
			if(insertion!=1) {
				updateFlag = false;
			}
			
			statement.close(); 
	        connection.close();
		}catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(),e);
		}finally {
			try{ 
	            if(statement!=null) statement.close(); 
	         } catch(SQLException se2) { 
	        	 LOGGER.log(Level.SEVERE, se2.toString(),se2);
	         } 
	         try { 
	            if(connection!=null) connection.close(); 
	         } catch(SQLException se){ 
	        	 LOGGER.log(Level.SEVERE, se.toString(),se);
	         }
		}		
		return updateFlag;
	}
	
	public boolean deleteUser(long id) {
		
		Connection connection = null;
		PreparedStatement statement = null;		
		boolean deleteFlag = true;
		try {
			connection = DBManager.getConnection();			
			String sql = "DELETE FROM User WHERE USERID = ?";
			statement = connection.prepareStatement(sql);
			statement.setLong(1, id);			
			int effectedRow = statement.executeUpdate();	
			
			if(effectedRow!=1) {
				deleteFlag = false;
			}
			
			statement.close(); 
	        connection.close();
		}catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(),e);
		}finally {
			try{ 
	            if(statement!=null) statement.close(); 
	         } catch(SQLException se2) { 
	        	 LOGGER.log(Level.SEVERE, se2.toString(),se2);
	         } 
	         try { 
	            if(connection!=null) connection.close(); 
	         } catch(SQLException se){ 
	        	 LOGGER.log(Level.SEVERE, se.toString(),se);
	         }
		}		
		return deleteFlag;
	}

	@Override
	public boolean checkUserNameAvailable(String userName) {
		boolean userNameAvailable = true;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;		
		try {
			connection = DBManager.getConnection();			
			String sql = "SELECT * FROM User WHERE USERNAME =?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, userName);
			resultSet = statement.executeQuery();			
			if(resultSet.next()) {
				userNameAvailable = false;				
			}
			
			resultSet.close();
			statement.close(); 
	        connection.close();
		}catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(),e);
		}finally {
			try{ 
	            if(statement!=null) statement.close(); 
	         } catch(SQLException se2) { 
	        	 LOGGER.log(Level.SEVERE, se2.toString(),se2);
	         } 
	         try { 
	            if(connection!=null) connection.close(); 
	         } catch(SQLException se){ 
	        	 LOGGER.log(Level.SEVERE, se.toString(),se);
	         }
		}
		return userNameAvailable;
	}
}
