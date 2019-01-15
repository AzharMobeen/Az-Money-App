package com.az.rest.service;

import java.util.List;

import com.az.rest.model.User;

public interface UserService {

	List<User> getAllUsers();
	User getUserById(long id);
	User createUser(User user);
	boolean updateUser(long userId,User user);
	boolean deleteUser(long id);	
}
