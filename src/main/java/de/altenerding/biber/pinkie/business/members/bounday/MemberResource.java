package de.altenerding.biber.pinkie.business.members.bounday;

import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.security.AuthenticatedMember;
import de.altenerding.biber.pinkie.business.security.Secured;
import org.apache.logging.log4j.Logger;

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
@Secured
public class MemberResource {

	@Inject
    private MemberService memberService;
	@Inject
	private Logger logger;
    @Inject
    @AuthenticatedMember
    private Member member;

    @GET
    public Response getUsers() {
        logger.info("Loading all members");
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
