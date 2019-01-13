package com.az.rest.resource;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.az.rest.model.User;
import com.az.rest.service.UserService;
import com.az.rest.service.impl.UserServiceImpl;

@Path("/users")
public class UserResource {

	private UserService userService;
	
	@PostConstruct
	public void init() {
		userService = UserServiceImpl.getInstance();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> fetchAll() {				
		return userService.getAllUsers();
	}

	@GET
	@Path("user/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") int id) {
		User user = userService.getUserById(id);
		if(user==null)
			return Response.status(Status.NOT_FOUND).entity("Record not found").build();
		else 
			return Response.ok().entity(user).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(User user) {
		User tempUser = userService.createUser(user);
		if(tempUser!=null)
			return Response.status(Status.CREATED).entity(tempUser).build();
		else
			return Response.status(Status.NOT_ACCEPTABLE).build();
	}

	@PUT
	@Path("/user/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") long id, User user) {
		boolean updateFlag = userService.updateUser(id,user);
		if(updateFlag)
			return Response.status(Status.ACCEPTED).build();
		else
			return Response.status(Status.NOT_MODIFIED).build();
	}

	@DELETE
	@Path("/user/{id}")
	public Response delete(@PathParam("id") long id) {
		boolean deleteFlag = userService.deleteUser(id);
		if(deleteFlag)
			return Response.status(Status.ACCEPTED).build();
		else
			return Response.status(Status.NOT_FOUND).build();
	}
	
	@Path("/user/{id}/accounts")
	public AccountResource getUserAccountList(@PathParam("id") long id) {
		return new AccountResource();
		
	}
}