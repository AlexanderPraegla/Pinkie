package de.altenerding.biber.pinkie.business.login.control;

import de.altenerding.biber.pinkie.business.login.entity.Login;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import de.altenerding.biber.pinkie.presentation.session.UserSessionBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.Base64;

public class Authenticator implements Serializable{

	private UserSessionBean userSessionBean;
	private SecurityProvider securityProvider;
	private LoginProvider loginProvider;
	private Logger logger;

	public boolean validate(String alias, String password) throws Exception {
		logger.info("Checking login credentials for alias={}", alias);

		Login login = loginProvider.getLoginByAlias(alias);

		if (login.getLoginCount() >= 3) {
			logger.error("Login tries already at {}", login.getLoginCount());
			return false;
		}

		byte[] salt = Base64.getDecoder().decode(login.getSalt());
		String hashedPassword = securityProvider.hashPassword(password.toCharArray(), salt);

		if (StringUtils.equals(login.getPassword(), hashedPassword)) {
			login.setLoginCount(0);
			return true;
		} else {
			login.setLoginCount(login.getLoginCount() + 1);
			return false;
		}
	}

	public boolean authenticateRole(Role role) {
		if (userSessionBean.getMember() == null) {
			return false;
		}

		Member member = userSessionBean.getMember();

		if (member.getRole() == Role.ADMIN) {
			return true;
		}

		//noinspection SimplifiableIfStatement
		if (member.getRole() == Role.PRESS && (role == Role.PRESS || role == Role.MEMBER)) {
			return true;
		}

		return member.getRole() == role;

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
	public void setUserSessionBean(UserSessionBean userSessionBean) {
		this.userSessionBean = userSessionBean;
	}

	@Inject
	public void setLoginProvider(LoginProvider loginProvider) {
		this.loginProvider = loginProvider;
	}
}
