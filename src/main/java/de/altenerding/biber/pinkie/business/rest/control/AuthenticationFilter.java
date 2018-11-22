package de.altenerding.biber.pinkie.business.rest.control;

import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.rest.entity.ApiError;
import de.altenerding.biber.pinkie.business.rest.entity.ResponseCode;
import de.altenerding.biber.pinkie.business.security.AuthenticatedMember;
import de.altenerding.biber.pinkie.business.security.Secured;

import javax.annotation.Priority;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Filter for every REST API call that is secured.
 * The token is extracted from the header an authenticated against the members.
 * If the authentication is successful the member is fired to and RequestScoped bean {@link de.altenerding.biber.pinkie.business.login.control.AuthenticatedUserProducer}
 */
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String REALM = "token";
    private static final String AUTHENTICATION_SCHEME = "Bearer ";

    /**
     * Setting the authenticated member in request scope
     */
    @Inject
    @AuthenticatedMember
    private Event<Member> memberAuthenticatedEvent;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);


        Member member = new Member();
        member.setFirstName("Alex Test");
        member.setLastName("Praegla Test");
        memberAuthenticatedEvent.fire(member);
        // Validate the token
        //abort every request until implementation
        abortWithUnauthorized(requestContext);
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {
        return true;
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {

        // Abort the filter chain with a 401 status code response
        ApiError apiError = new ApiError(ResponseCode.TOKEN_INVALID);
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .entity(apiError)
                        .type(MediaType.APPLICATION_JSON)
                        .build());
    }

    private void validateToken(String token) throws Exception {
        // Check if the token was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid
    }
}