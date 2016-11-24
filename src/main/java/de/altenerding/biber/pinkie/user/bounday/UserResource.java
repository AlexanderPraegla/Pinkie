package de.altenerding.biber.pinkie.user.bounday;

import de.altenerding.biber.pinkie.user.entity.User;

import javax.inject.Inject;
import javax.ws.rs.GET;
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
    @Inject
    UserService userService;

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

        return Response.ok(user.get()).build();
    }
}
