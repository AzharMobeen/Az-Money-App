package com.az.rest.test.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
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
import com.az.rest.model.Transaction;
import com.az.rest.resource.TransactionResource;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class TransactionResourceTest extends JerseyTest {
	
	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(TransactionResource.class);
	}
	
	@BeforeClass
	public static void initDBWithTableData() {
		DBManager.getInstance();
	}

	/*
	 * Dummy insertion done before test so it will be successful
	 * */
	@Test
	public void getTransactionByIdSuccessTest() {
		Response output = target("/transactions/transaction/5").request().get();
		assertEquals("Should return status 200", Status.OK.getStatusCode(), output.getStatus());
		assertNotNull("Should return user object as json", output.getEntity());		
		System.out.println(output.getStatus());		
	}
	
	/*
	 * Dummy insertion done before test but not up till transactionId = 100 so it will be successful
	 * */
	@Test
	public void getTransactionByIdFailedTest() {
		Response output = target("/transactions/100").request().get();
		assertEquals("Should return status 404", Status.NOT_FOUND.getStatusCode(), output.getStatus());		
		System.out.println(output.getStatus());		
	}

	/*
	 * Dummy insertion done before test and both IBAN available in DB with balance
	 * so it will be successful
	 * */
	@Test
	public void transferMoneySuccessTest() {
		Transaction transaction= new Transaction("PK111222334455502","AE111222334455502", new BigDecimal(2000),new BigDecimal(20));
		Response output = target("/transactions").request().post(Entity.entity(transaction, MediaType.APPLICATION_JSON));
		System.out.println(output.getStatus());
		assertEquals("Should return status 201", Status.CREATED.getStatusCode(), output.getStatus());
	}
	
	/*
	 * From IBAN doesn't have enough balance so it will be successful
	 * */		
	@Test
	public void transferMoneyFailedTest1() {
		Transaction transaction= new Transaction("PK111222334455502","AE111222334455502", new BigDecimal(2000000),new BigDecimal(20));
		Response response = target("/transactions").request().post(Entity.entity(transaction, MediaType.APPLICATION_JSON));
		System.out.println(response.getStatus());
		assertEquals("Should return status 406 because From IBAN doesn't have enough balance", Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
	}
	
	/*
	 * To IBAN Not found so it will be successful
	 * */	
	@Test
	public void transferMoneyFailedTest2() {
		Transaction transaction= new Transaction("PK111222334455502","AE55502", new BigDecimal(2070),new BigDecimal(20));
		Response response = target("/transactions").request().post(Entity.entity(transaction, MediaType.APPLICATION_JSON));
		System.out.println(response.getStatus());
		assertEquals("Should return status 404 because To IBAN Not found", Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}
	
	/*
	 * Both IBAN (from,to) should be different but bellow are same so it will be successful
	 * */
	@Test
	public void transferMoneyFailedTest3() {
		Transaction transaction= new Transaction("PK111222334455502","PK111222334455502", new BigDecimal(200),new BigDecimal(20));
		Response response = target("/transactions").request().post(Entity.entity(transaction, MediaType.APPLICATION_JSON));
		System.out.println(response.getStatus());
		assertEquals("Should return status 406 because Both IBAN (from,to) are same", Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
	}
	
	/*
	 * Dummy data inserted before test with UserID = 1 so it will be successful
	 * */
	@Test
	public void getUserTransactionListSuccessTest() {		
		Response response = target("/transactions/user/1").request().get();
		assertEquals("Should return status 200", Status.OK.getStatusCode(), response.getStatus());
		assertNotNull("Should return user transaction list", response.getEntity().toString());
		System.out.println(response.getStatus());
	}
	
	/*
	 * UserId = 110 not exist in DB so it will be successful
	 * */
	@Test
	public void getUserTransactionListFailedTest() throws JsonParseException, JsonMappingException, IOException{		
		Response response = target("/transactions/user/110").request().get();
		assertEquals("Should return status 200 But empty list", Status.OK.getStatusCode(), response.getStatus());		
		Transaction[] transactionList  = response.readEntity(Transaction[].class);
		assertEquals(0, transactionList.length);
	}
	
	/*
	 * Dummy data inserted before test so it will be successful
	 * */
	@Test
	public void fetchAll() {
		Response response = target("/transactions").request().get();
		assertEquals("Should return status 200", Status.OK.getStatusCode(), response.getStatus());
		assertNotNull("Should return account list", response.getEntity().toString());
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
	}
}