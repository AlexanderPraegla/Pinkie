package de.altenerding.biber.pinkie.business.login.boundary;

import de.altenerding.biber.pinkie.business.login.control.Authenticator;
import de.altenerding.biber.pinkie.business.login.control.LoginCreator;
import de.altenerding.biber.pinkie.business.login.control.LoginModifier;
import de.altenerding.biber.pinkie.business.login.control.LoginProvider;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;

@Stateless
public class AuthenticateService implements Serializable {

    private Authenticator authenticator;
    private LoginCreator loginCreator;
    private LoginModifier loginModifier;
    private LoginProvider loginProvider;
    private Logger logger;

    public boolean validate(String alias, String password) throws Exception {
        return authenticator.validate(alias, password);
    }

    public boolean authenticateRole(Role role) {
        return authenticator.authenticateRole(role);
    }

    public boolean hasMemberOnetimePasswort(Member member) throws Exception {
        return loginProvider.hasMemberOnetimePasswort(member);
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
    public void setLoginCreator(LoginCreator loginCreator) {
        this.loginCreator = loginCreator;
    }

    @Inject
    public void setLoginModifier(LoginModifier loginModifier) {
        this.loginModifier = loginModifier;
    }

    @Inject
    public void setLoginProvider(LoginProvider loginProvider) {
        this.loginProvider = loginProvider;
    }
}
