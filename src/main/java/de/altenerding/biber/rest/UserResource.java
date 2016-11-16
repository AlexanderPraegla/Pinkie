package de.altenerding.biber.rest;

import de.altenerding.biber.service.user.bounday.UserService;
import de.altenerding.biber.service.user.entity.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	@GET
	@Path("/")
	public Response getDefaultUserInJSON() {
		UserService userService = new UserService();
		User user = userService.getDefaultUser();
		Response.ResponseBuilder builder = Response.status(Response.Status.OK);
		return builder.entity(user).build();
	}

	@POST
	@Path("/login")
	public Response login(@FormParam("name") String name, @FormParam("password") String password) {
		return Response.status(200).build();
	}
}
