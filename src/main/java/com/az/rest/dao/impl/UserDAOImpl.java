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

import com.az.rest.dao.UserDAO;
import com.az.rest.model.User;

public class UserDAOImpl implements UserDAO{

	Logger log = LoggerFactory.getLogger(UserDAOImpl.class);			
	
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
			connection = ManagerDAO.getConnection();
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
		return userList;
	}

	@Override
	public User getUserById(long id) {
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		User user = null;
		try {
			connection = ManagerDAO.getConnection();			
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
		return user;
	}

	@Override
	public User createUser(User user) {
		Connection connection = null;
		PreparedStatement statement = null;		
		ResultSet resultSet = null;
		try {
			connection = ManagerDAO.getConnection();			
			String sql = "INSERT INTO User (USERNAME,FULLNAME,EMAIL) VALUES(?,?,?)";
			statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getUserName());
			statement.setString(2, user.getFullName());
			statement.setString(3, user.getEmail());
			int insertion = statement.executeUpdate();	
			
			if(insertion==1) {
				resultSet = statement.getGeneratedKeys();
				user.setUserId(resultSet.getLong(1));
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
		return user;
	}
	
	public boolean updateUser(User user) {
		Connection connection = null;
		PreparedStatement statement = null;		
		boolean updateFlag = true;
		try {
			connection = ManagerDAO.getConnection();			
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
		return updateFlag;
	}
	
	public boolean deleteUser(long id) {
		
		Connection connection = null;
		PreparedStatement statement = null;		
		boolean deleteFlag = true;
		try {
			connection = ManagerDAO.getConnection();			
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
		return deleteFlag;
	}

	@Override
	public boolean checkUserNameAvailable(String userName) {
		boolean userNameAvailable = true;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;		
		try {
			connection = ManagerDAO.getConnection();			
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
		return userNameAvailable;
	}
}
