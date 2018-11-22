package de.altenerding.biber.pinkie.business.login.control;

import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.security.AuthenticatedMember;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;

/**
 * Producer that holds the authenticated member after a successful token validation from a REST API call.
 */
@RequestScoped
public class AuthenticatedUserProducer {

    @Produces
    @RequestScoped
    @AuthenticatedMember
    private Member authenticatedMember;

    public void handleAuthenticationEvent(@Observes @AuthenticatedMember Member member) {
        this.authenticatedMember = member;
    }

}