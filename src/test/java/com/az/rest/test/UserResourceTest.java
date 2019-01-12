package com.az.rest.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import com.az.rest.model.User;
import com.az.rest.resource.UserResource;

public class UserResourceTest extends JerseyTest {
	
	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(UserResource.class);
	}
	
	@Test
	public void getByIdSuccessTest() {
		Response output = target("/users/user/3").request().get();
		assertEquals("Should return status 200", 200, output.getStatus());
		assertNotNull("Should return user object as json", output.getEntity());
		System.out.println(output.getStatus());
		System.out.println(output.readEntity(String.class));
	}
	
	@Test
	public void getByIdFailedTest() {
		Response output = target("/users/user/10").request().get();
		assertEquals("Should return status 404", 404, output.getStatus());		
		System.out.println(output.getStatus());		
	}

	@Test
	public void createSuccessTest() {
		User user = new User("Cheeku", "Zuhayr Azhar","Zuhayr@Gmail.com");
		Response output = target("/users").request().post(Entity.entity(user, MediaType.APPLICATION_JSON));
		System.out.println(output.getStatus());
		assertEquals("Should return status 201", 201, output.getStatus());
	}
	
	@Test
	public void createFailedTest() {
		User user = new User("Malik", "Malik Adeel","Malik@Gmail.com");
		Response output = target("/users").request().post(Entity.entity(user, MediaType.APPLICATION_JSON));
		System.out.println(output.getStatus());
		assertEquals("Should return status 406 because id already exist", 406, output.getStatus());
	}

	@Test
	public void updateSuccessTest() {
		User user = new User(Long.valueOf(3), "Mughal-2", "Zuhayr Azhar","Zuhayr@Gmail.com");
		Response output = target("/users/user/3").request().put(Entity.entity(user, MediaType.APPLICATION_JSON));
		assertEquals("Should return status 202", 202, output.getStatus());
		System.out.println(output.getStatus());
	}
	
	@Test
	public void updateFailedTest() {
		User user = new User(Long.valueOf(20), "Mughal-2", "Zuhayr Azhar","Zuhayr@Gmail.com");
		Response output = target("/users/user/10").request().put(Entity.entity(user, MediaType.APPLICATION_JSON));
		assertEquals("Should return status 304 because id not exist", 304, output.getStatus());
		System.out.println(output.getStatus());
	}

	@Test
	public void deleteSuccessTest() {
		Response output = target("/users/user/1").request().delete();
		assertEquals("Should return status 202", 202, output.getStatus());
	}
	
	@Test
	public void deleteFailedTest() {
		Response output = target("/users/user/-1").request().delete();
		assertEquals("Should return status 404 because -1 not found", 404, output.getStatus());
	}
	
	@Test
	public void tesFetchAll() {
		Response response = target("/users").request().get();
		assertEquals("should return status 200", 200, response.getStatus());
		assertNotNull("Should return user list", response.getEntity().toString());
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
	}
}