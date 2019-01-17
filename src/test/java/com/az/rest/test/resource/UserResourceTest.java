package com.az.rest.test.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.BeforeClass;
import org.junit.Test;

import com.az.rest.dao.impl.DBManager;
import com.az.rest.model.User;
import com.az.rest.resource.UserResource;

public class UserResourceTest extends JerseyTest {
	
	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(UserResource.class);
	}
	
	@BeforeClass
	public static void initDBWithTableData() {
		DBManager.getInstance();
	}
	
	/*
	 * Dummy data already inserted so we will get user by userID
	 * */
	@Test
	public void getByIdSuccessTest() {
		Response output = target("/users/user/1").request().get();
		assertEquals("Should return status 200", Status.OK.getStatusCode(), output.getStatus());
		assertNotNull("Should return user object as json", output.getEntity());
		System.out.println(output.getStatus());
		System.out.println(output.readEntity(String.class));
	}
	
	/*
	 * It will be failed because there is no userId = 10
	 * */
	@Test
	public void getByIdFailedTest() {
		Response output = target("/users/user/10").request().get();
		assertEquals("Should return status 404", Status.NOT_FOUND.getStatusCode(), output.getStatus());		
		System.out.println(output.getStatus());		
	}

	/*
	 * This user with 'Cheeku' username not in DB so it will be successful
	 * */
	@Test
	public void createSuccessTest() {
		User user = new User("Cheeku", "Cheeku Radey","Cheeku@Gmail.com");
		Response output = target("/users").request().post(Entity.entity(user, MediaType.APPLICATION_JSON));
		System.out.println(output.getStatus());
		assertEquals("Should return status 201", Status.CREATED.getStatusCode(), output.getStatus());
	}
	
	/*
	 * UserName 'Shah' already exist in DB so it will not save
	 * */
	@Test
	public void createFailedTest() {
		User user = new User("Shah", "Shah Je","Shah@Gmail.com");
		Response output = target("/users").request().post(Entity.entity(user, MediaType.APPLICATION_JSON));
		System.out.println(output.getStatus());
		assertEquals("Should return status 406 because UserName already exist", Status.NOT_ACCEPTABLE.getStatusCode(), output.getStatus());
	}
	
	/*
	 * User with userId = 3 already in DB so it will be successful
	 * */
	@Test
	public void updateSuccessTest() {
		User user = new User(Long.valueOf(3), "Mughal-2", "Zuhayr Azhar","Zuhayr@Gmail.com");
		Response output = target("/users/user/3").request().put(Entity.entity(user, MediaType.APPLICATION_JSON));
		assertEquals("Should return status 202", Status.ACCEPTED.getStatusCode(), output.getStatus());
		System.out.println(output.getStatus());
	}
	
	/*
	 * UserId=10 not exist in user table so it will be failed
	 * */
	@Test
	public void updateFailedTest() {
		User user = new User("Mughal-2", "Zuhayr Azhar","Zuhayr@Gmail.com");
		Response output = target("/users/user/10").request().put(Entity.entity(user, MediaType.APPLICATION_JSON));
		assertEquals("Should return status 304 because id not exist", Status.NOT_MODIFIED.getStatusCode(), output.getStatus());
		System.out.println(output.getStatus());
	}

	/*
	 * UserId = 1 already in DB wo it will be successful
	 * */
	@Test
	public void deleteSuccessTest() {
		Response output = target("/users/user/4").request().delete();
		assertEquals("Should return status 202", Status.ACCEPTED.getStatusCode(), output.getStatus());
	}
	
	/*
	 * user id not exist in user table so it will be successful
	 * */
	@Test
	public void deleteFailedTest() {
		Response output = target("/users/user/-1").request().delete();
		assertEquals("Should return status 404 because -1 not found", Status.NOT_FOUND.getStatusCode(), output.getStatus());
	}
	
	/*
	 * Dummy data inserted in DB before test so it will be successful
	 * */
	@Test
	public void getAllUsers() {
		Response response = target("/users").request().get();
		assertEquals("should return status 200", Status.OK.getStatusCode(), response.getStatus());
		assertNotNull("Should return user list", response.getEntity().toString());
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
	}
}