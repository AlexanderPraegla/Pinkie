package de.altenerding.biber.pinkie.business.login.boundary;

import de.altenerding.biber.pinkie.business.login.control.Authenticator;
import de.altenerding.biber.pinkie.business.login.control.LoginProvider;
import de.altenerding.biber.pinkie.business.login.entity.Login;
import de.altenerding.biber.pinkie.business.login.entity.MemberHasOneTimePasswordException;
import de.altenerding.biber.pinkie.business.members.bounday.MemberService;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import de.altenerding.biber.pinkie.business.security.AuthenticatedMember;
import de.altenerding.biber.pinkie.presentation.session.UserSessionBean;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.io.Serializable;

@Stateless
public class AuthenticateService implements Serializable {

    private Authenticator authenticator;
    private LoginProvider loginProvider;
    private Logger logger;
    @Inject
    @AuthenticatedMember
    private Event<Member> memberAuthenticatedEvent;
    @Inject
    private UserSessionBean userSessionBean;
    @Inject
    private MemberService memberService;

    public boolean login(String alias, String password) throws Exception {
        if (authenticator.validatePassword(alias, password)) {
            Member member = memberService.getMemberByAlias(alias);
            /*
            * Firing authenticated user to producer bean for rest calls
            */
            userSessionBean.setMember(member);
            memberAuthenticatedEvent.fire(member);
            logger.info("Login successful for member alias={}", member.getAlias());

            if (hasMemberOnetimePasswort(member)) {
                throw new MemberHasOneTimePasswordException();
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean authenticateLoggedInMemberRole(Member member, Role role) {
        return authenticator.isMemberInRole(member, role);
    }

    public boolean hasMemberOnetimePasswort(Member member) throws Exception {
        return loginProvider.hasMemberOnetimePasswort(member);
    }

    public Login getLoginByAlias(String alias) throws Exception {
        return loginProvider.getLoginByAlias(alias);
    }

    @Inject
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Inject
    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Inject
    public void setLoginProvider(LoginProvider loginProvider) {
        this.loginProvider = loginProvider;
    }
}
