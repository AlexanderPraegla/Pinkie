package de.altenerding.biber.pinkie.user.bounday;

import de.altenerding.biber.pinkie.user.entity.User;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
        return Response.ok(user).build();
    }

    @POST
    @Path("/login")
    public Response login(@FormParam("name") String name, @FormParam("password") String password) {
        return Response.ok().build();
    }
}
