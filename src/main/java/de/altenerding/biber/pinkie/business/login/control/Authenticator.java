package de.altenerding.biber.pinkie.business.login.control;

import de.altenerding.biber.pinkie.business.login.entity.Login;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.Base64;

public class Authenticator implements Serializable{

	private SecurityProvider securityProvider;
	private LoginProvider loginProvider;
	private LoginModifier loginModifier;
	private Logger logger;

	public boolean validatePassword(String alias, String password) throws Exception {
		alias = alias.toLowerCase();
		logger.info("Checking login credentials for alias={}", alias);

		Login login = loginProvider.getLoginByAlias(alias);

		if (login == null) {
			logger.error("No login credentials found for alias={}", alias);
			return false;
		}

		if (login.getLoginCount() >= 3) {
			logger.error("Login tries already at {}", login.getLoginCount());
			return false;
		}

		byte[] salt = Base64.getDecoder().decode(login.getSalt());
		String hashedPassword = securityProvider.hashPassword(password.toCharArray(), salt);

		if (StringUtils.equals(login.getPassword(), hashedPassword)) {
			login.setLoginCount(0);
			loginModifier.updateLogin(login);
			return true;
		} else {
			login.setLoginCount(login.getLoginCount() + 1);
			loginModifier.updateLogin(login);
			return false;
		}
	}

	public boolean isMemberInRole(Member member, Role role) {
		if (member == null) {
			return false;
		}

		if (member.getRole() == Role.ADMIN) {
			return true;
		}

		//noinspection SimplifiableIfStatement
		if (member.getRole() == Role.PRESS && (role == Role.PRESS || role == Role.MEMBER)) {
			return true;
		}

		return member.getRole() == role;

	}

    public String getRandomPassword() {
        return RandomStringUtils.random(20, true, true);
    }

	@Inject
	public void setSecurityProvider(SecurityProvider securityProvider) {
		this.securityProvider = securityProvider;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Inject
	public void setLoginProvider(LoginProvider loginProvider) {
		this.loginProvider = loginProvider;
	}

	@Inject
	public void setLoginModifier(LoginModifier loginModifier) {
		this.loginModifier = loginModifier;
	}
}
