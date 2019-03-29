package de.altenerding.biber.pinkie.presentation.session;

import de.altenerding.biber.pinkie.business.login.boundary.AuthenticateService;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.members.entity.Role;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class UserSessionBean implements Serializable {

    private AuthenticateService authenticateService;

    private Member member = null;

    public boolean isUserInRole(Role role) {
        return authenticateService.authenticateLoggedInMemberRole(member, role);
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void logout() {
        member = null;
    }

    @Inject
    public void setAuthenticateService(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }
}
