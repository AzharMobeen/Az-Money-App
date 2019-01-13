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
import javax.ws.rs.core.Response.Status;

import com.az.rest.model.Account;
import com.az.rest.service.AccountService;
import com.az.rest.service.impl.ManagerService;

@Path("/accounts")
public class AccountResource {
	
	private ManagerService managerService;
	private AccountService accountService;
	
	@PostConstruct
	public void init() {
		managerService = ManagerService.getInstance();
		accountService = managerService.getAccountService();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Account> fetchAll() {				
		return accountService.getAllAccounts();
	}

	@GET
	@Path("account/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") long id) {
		Account account = accountService.getAccountById(id);
		if(account==null)
			return Response.status(Status.NOT_FOUND).entity("Record not found").build();
		else 
			return Response.ok().entity(account).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Account account) {
		Account tempAccount = accountService.createAccount(account);
		if(tempAccount!=null)
			return Response.status(Status.CREATED).entity("Successfully Created").build();
		else
			return Response.status(Status.NOT_ACCEPTABLE).entity("Not Acceptable because IBAN already exist").build();
	}	
}
