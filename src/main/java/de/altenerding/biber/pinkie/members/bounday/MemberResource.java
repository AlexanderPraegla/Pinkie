package de.altenerding.biber.pinkie.members.bounday;

import de.altenerding.biber.pinkie.members.entity.Member;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/members")
@Produces(MediaType.APPLICATION_JSON)
public class MemberResource {

	@Inject
    private MemberService memberService;

    @GET
    public Response getUsers() {
        List<Member> users = memberService.getMembers();
        return Response.ok(users).build();
    }

    @GET
    @Path("{id}")
    public Response getUser(@PathParam("id") long id) {
        Member user = memberService.getMemberById(id);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(user).build();
    }
}
