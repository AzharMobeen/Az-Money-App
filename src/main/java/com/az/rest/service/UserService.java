package com.az.rest.service;

import java.util.List;

import com.az.rest.model.User;

public interface UserService {

	public List<User> getAllUsers();
	public User getUserById(long id);
	public User createUser(User user);
	public boolean updateUser(long userId,User user);
	public boolean deleteUser(long id);
}
