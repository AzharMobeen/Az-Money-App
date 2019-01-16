package com.az.rest.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import com.az.rest.dao.impl.DBManager;
import com.az.rest.model.User;
import com.az.rest.service.UserService;
import com.az.rest.service.impl.UserServiceImpl;

public class UserServiceTest {
	
	private UserService userService;
	
	@Before
	public void initDBWithTableData() {
		DBManager.getInstance();
		userService = UserServiceImpl.getInstance();
	}
	
	@Test
	public void getUserByIdSuccessTest() {
		User user = userService.getUserById(1);		
		assertNotNull("Should return user object", user);
	}
	
	@Test
	public void getUserByIdFailedTest() {
		User user = userService.getUserById(1000);
		assertNull("Should return null object",user);		
	}

	@Test
	public void createSuccessTest() {
		User user = new User("Cheeku", "Zuhayr Azhar","Zuhayr@Gmail.com");
		user = userService.createUser(user);
		assertNotNull("Should return user object", user);		
	}
	
	/*
	 * UserName 'Malik' already exist in DB so it will not save
	 * */
	@Test
	public void createFailedTest() {
		User user = new User("Malik", "Malik Adeel","Malik@Gmail.com");				
		assertNull("Should return null object",userService.createUser(user));
	}

	@Test
	public void updateSuccessTest() {
		User user = new User(Long.valueOf(3), "Mughal-2", "Zuhayr Azhar","Zuhayr@Gmail.com");
		boolean outcome = userService.updateUser(3, user);
		assertEquals("Should return true", true, outcome);		
	}
	
	/*
	 * user id not exist in user table
	 * */
	@Test
	public void updateFailedTest() {
		User user = new User("Mughal-2", "Zuhayr Azhar","Zuhayr@Gmail.com");
		boolean outcome = userService.updateUser(2000, user);
		assertEquals("Should return false", false, outcome);
	}

	@Test
	public void deleteSuccessTest() {
		boolean outcome = userService.deleteUser(2);
		assertEquals("Should return true", true, outcome);
	}
	
	/*
	 * user id not exist in user table
	 * */
	@Test
	public void deleteFailedTest() {
		boolean outcome = userService.deleteUser(2000);
		assertEquals("Should return false", false, outcome);
	}
	
	@Test
	public void getAllUsers() {
		List<User> userList = userService.getAllUsers();		
		assertNotNull("Should return account list", userList.size());
		assertNotEquals(0, userList.size());
	}
}