package com.az.rest.test.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

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
import com.az.rest.model.Account;
import com.az.rest.resource.AccountResource;

public class AccountResourceTest extends JerseyTest {
	
	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(AccountResource.class);
	}
	
	@BeforeClass
	public static void initDBWithTableData() {
		DBManager.getInstance();
	}
	
	/*
	 * Dummy data inserted before test so it will be successful
	 * */
	@Test
	public void getByIdSuccessTest() {
		Response output = target("/accounts/account/1").request().get();
		assertEquals("Should return status 200", Status.OK.getStatusCode(), output.getStatus());
		assertNotNull("Should return user object as json", output.getEntity());
		System.out.println(output.getStatus());
		System.out.println(output.readEntity(String.class));
	}
	
	/*
	 * Dummy data inserted before test but not up till accountId=100 so it will be successful
	 * */
	@Test
	public void getByIdFailedTest() {
		Response output = target("/accounts/account/100").request().get();
		assertEquals("Should return status 404", Status.NOT_FOUND.getStatusCode(), output.getStatus());		
		System.out.println(output.getStatus());		
	}

	/*
	 * IBAN = AE123456789 not exist in Account Table so it will be successful
	 * */
	@Test
	public void createSuccessTest() {
		Account account = new Account("AE123456789", Long.valueOf(1), "AED", new BigDecimal(200));
		Response output = target("/accounts").request().post(Entity.entity(account, MediaType.APPLICATION_JSON));
		System.out.println(output.getStatus());
		assertEquals("Should return status 201", Status.CREATED.getStatusCode(), output.getStatus());
	}
	
	/*
	 * IBAN = AE112233445501 already exist in DB so it will be successful
	 * */
	@Test
	public void createFailedTest() {
		Account account = new Account("AE112233445501", Long.valueOf(1), "AED", new BigDecimal(200));
		Response output = target("/accounts").request().post(Entity.entity(account, MediaType.APPLICATION_JSON));
		System.out.println(output.getStatus());
		assertEquals("Should return status 406 because IBAN already exist", Status.NOT_ACCEPTABLE.getStatusCode(), output.getStatus());
	}

	/*
	 * Dummy data inserted before test so it will be successful
	 * */
	@Test
	public void userAccoutListSuccessTest() {		
		Response response = target("/accounts/user/1").request().get();
		assertEquals("Should return status 200", Status.OK.getStatusCode(), response.getStatus());
		assertNotNull("Should return user account list", response.getEntity().toString());
		System.out.println(response.getStatus());
	}
	
	/*
	 * Dummy data inserted before test but not up till userId=110 so it will be successful
	 * */
	@Test
	public void userAccoutListFailedTest(){		
		Response response = target("/accounts/user/110").request().get();
		assertEquals("Should return status 200 But empty list", Status.OK.getStatusCode(), response.getStatus());		
		Account[] accountList  = response.readEntity(Account[].class);
		assertEquals(0, accountList.length);
	}
	
	/*
	 * Dummy data inserted before test so it will be successful
	 * */
	@Test
	public void getAllAccounts() {
		Response response = target("/accounts").request().get();
		assertEquals("Should return status 200", Status.OK.getStatusCode(), response.getStatus());
		assertNotNull("Should return account list", response.getEntity().toString());
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
	}
}