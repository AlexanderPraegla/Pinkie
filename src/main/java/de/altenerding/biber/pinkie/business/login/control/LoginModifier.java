package de.altenerding.biber.pinkie.business.login.control;

import de.altenerding.biber.pinkie.business.login.entity.Login;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Base64;

public class LoginModifier {

	private Logger logger;
	@PersistenceContext
	private EntityManager em;
	private SecurityProvider securityProvider;
	private LoginProvider loginProvider;

	public void savePassword(String alias, String passwordNew, boolean isOnetimePassword) throws Exception {
		logger.info("Setting new password for login for alias={}", alias);

		Login login = loginProvider.getLoginByAlias(alias);
		byte[] salt = Base64.getDecoder().decode(login.getSalt());
		login.setSalt(Base64.getEncoder().encodeToString(salt));
		login.setOnetimePassword(isOnetimePassword);
		login.setLoginCount(0);
		login.setPassword(securityProvider.hashPassword(passwordNew.toCharArray(), salt));

		em.merge(login);
		em.flush();
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Inject
	public void setSecurityProvider(SecurityProvider securityProvider) {
		this.securityProvider = securityProvider;
	}

	@Inject
	public void setLoginProvider(LoginProvider loginProvider) {
		this.loginProvider = loginProvider;
	}
}
