package com.az.rest.dao;

import java.util.List;

import com.az.rest.model.User;

public interface UserDAO {

	public List<User> getAllUsers();
	public User getUserById(long id);
	public User createUser(User user);
	public boolean updateUser(User user);
	public boolean deleteUser(long id);
	public boolean checkUserNameAvailable(String userName);
}
