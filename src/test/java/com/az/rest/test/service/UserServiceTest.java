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
	
	/*
	 * Dummy data already inserted so we will get user by userID
	 * */
	@Test
	public void getUserByIdSuccessTest() {
		User user = userService.getUserById(2);		
		assertNotNull("Should return user object", user);
	}
	
	/*
	 * It will be failed because there is no userId = 1000
	 * */
	@Test
	public void getUserByIdFailedTest() {
		User user = userService.getUserById(1000);
		assertNull("Should return null object",user);		
	}

	/*
	 * This user with 'Bakkar' username not in DB so it will be successful
	 * */
	@Test
	public void createSuccessTest() {
		User user = new User("Bakkar", "Mustafa Radey","Bakkar@Gmail.com");
		user = userService.createUser(user);
		assertNotNull("Should return user object", user);		
	}
	
	/*
	 * UserName 'Shah' already exist in DB so it will not save
	 * */
	@Test
	public void createFailedTest() {
		User user = new User("Shah", "Shah Je","Malik@Gmail.com");
		user = userService.createUser(user);
		assertNull("Should return null object",user);
	}

	/*
	 * User with userId = 2 already in DB so it will be successful
	 * */
	@Test
	public void updateSuccessTest() {
		User user = new User("Mughal", "Zuhayr Azhar","Zuhayr@Gmail.com");		
		boolean outcome = userService.updateUser(2, user);
		assertEquals("Should return true", true, outcome);		
	}
	
	/*
	 * UserId=20 not exist in user table so it will be failed
	 * */
	@Test
	public void updateFailedTest() {
		User user = new User("Mughal-2", "Zuhayr Azhar","Zuhayr@Gmail.com");
		boolean outcome = userService.updateUser(20, user);
		assertEquals("Should return false", false, outcome);
	}

	/*
	 * UserId = 2 already in DB wo it will be successful
	 * */
	@Test
	public void deleteSuccessTest() {
		boolean outcome = userService.deleteUser(5);
		assertEquals("Should return true", true, outcome);
	}
	
	/*
	 * user id not exist in user table
	 * */
	@Test
	public void deleteFailedTest() {
		boolean outcome = userService.deleteUser(20);
		assertEquals("Should return false", false, outcome);
	}
	
	/*
	 * Dummy data inserted in DB before test so it will be successful
	 * */
	@Test
	public void getAllUsers() {
		List<User> userList = userService.getAllUsers();		
		assertNotNull("Should return account list", userList.size());
		assertNotEquals(0, userList.size());
	}
}