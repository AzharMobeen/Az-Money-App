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
	@Path("transaction/{fromIBAN}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Transaction> getById(@PathParam("fromIBAN") String fromIBAN) {
		return transactionService.getAllTransactions();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Transaction transaction) {
		return transactionService.moneyTransfer(transaction);		
	}

	/*@PUT
	@Path("/transaction/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") long id, Transaction transaction) {
		boolean updateFlag = transactionService.updateTransaction(transaction);
		if(updateFlag)
			return Response.status(Status.ACCEPTED).build();
		else
			return Response.status(Status.NOT_MODIFIED).build();
	}*/

	/*@DELETE
	@Path("/transaction/{id}")
	public Response delete(@PathParam("id") long id) {
		boolean deleteFlag = transactionService.deleteTransaction(id);
		if(deleteFlag)
			return Response.status(Status.ACCEPTED).build();
		else
			return Response.status(Status.NOT_FOUND).build();
	}*/
}
