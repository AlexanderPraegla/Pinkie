package de.altenerding.biber.pinkie.user.bounday;

import de.altenerding.biber.pinkie.user.entity.User;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;


@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    UserService userService = new UserService();

    @GET
    public Response getUsers() {
        List<User> users = userService.getUsers();
        return Response.ok(users).build();
    }

    @GET
    @Path("{id}")
    public Response getUser(@PathParam("id") long id) {
        Optional<User> user = userService.getUser(id);

        if (!user.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(user).build();
    }

    @POST
    @Path("/login")
    public Response login(@FormParam("name") String name, @FormParam("password") String password) {
        return Response.ok().build();
    }
}
