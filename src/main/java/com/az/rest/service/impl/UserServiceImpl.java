package com.az.rest.service.impl;

import java.util.List;

import com.az.rest.dao.UserDAO;
import com.az.rest.dao.impl.ManagerDAOFactory;
import com.az.rest.model.User;
import com.az.rest.service.UserService;

public class UserServiceImpl implements UserService{

	private static UserServiceImpl userService;
	private final ManagerDAOFactory managerDAOFactory;
	private final UserDAO userDAO;

	private UserServiceImpl() {
		managerDAOFactory = ManagerDAOFactory.getInstance();
		userDAO = managerDAOFactory.getUserDAO();	
	}

	public static UserServiceImpl getInstance() {

		if (userService == null)
			userService = new UserServiceImpl();

		return userService;
	}

	public List<User> getAllUsers() {
		return userDAO.getAllUsers();
	}

	public User getUserById(long id) {		
		return userDAO.getUserById(id);
	}

	public User createUser(User user) {		
		if (userDAO.checkUserNameAvailable(user.getUserName())) {			
			return userDAO.createUser(user);
		} else
			return null;
	}

	public boolean updateUser(long userId,User user) {
		if (userDAO.checkUserNameAvailable(user.getUserName())) { 
			user.setUserId(userId);
			return userDAO.updateUser(user);
		}else 
			return false;
	}

	public boolean deleteUser(long id) {
		return userDAO.deleteUser(id);
	}	
}
