package com.az.rest.resource;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.az.rest.model.Transaction;
import com.az.rest.service.TransactionService;
import com.az.rest.service.impl.TransactionServiceImpl;

@Path("transactions")
public class TransactionResource {
		
	private TransactionService transactionService;
	
	@PostConstruct
	public void init() {		
		transactionService = TransactionServiceImpl.getInstance();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Transaction> fetchAll() {				
		return transactionService.getAllTransactions();
	}

	@GET
	@Path("transaction/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Transaction getTransactionById(@PathParam("id") long id) {
		return transactionService.getTransactionById(id);
	}

	@GET
	@Path("/user/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Transaction> getUserTransactionList(@PathParam("id") long id) {		
		return transactionService.getUserTransactionListByUserId(id);		
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response transferMoney(Transaction transaction) {
		return transactionService.transferMoney(transaction);		
	}
}
