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
		alias = alias.toLowerCase();
		logger.info("Setting new password for login for alias={}", alias);

		Login login = loginProvider.getLoginByAlias(alias);

        if (login == null) {
            logger.error("No login credentials found for alias={}", alias);
            return;
        }

		byte[] salt = Base64.getDecoder().decode(login.getSalt());
		login.setSalt(Base64.getEncoder().encodeToString(salt));
		login.setOnetimePassword(isOnetimePassword);
		login.setLoginCount(0);
		login.setPassword(securityProvider.hashPassword(passwordNew.toCharArray(), salt));

        updateLogin(login);
    }

    public void updateAlias(String aliasOld, String aliasNew) throws Exception {
        aliasOld = aliasOld.toLowerCase();
        aliasNew = aliasNew.toLowerCase();
        logger.info("Changing alias from {} to {}", aliasOld, aliasNew);
        Login login = loginProvider.getLoginByAlias(aliasOld);

        if (login == null) {
            logger.error("No login credentials found for old alias={}", aliasOld);
            return;
        }

        login.setAlias(aliasNew);
        updateLogin(login);
        logger.info("Successfully changed alias from {} to {}", aliasOld, aliasNew);
    }

    void updateLogin(Login login) {
        em.merge(login);
        em.flush();
    }

	public void removeLoginForAlias(String alias) throws Exception {
		alias = alias.toLowerCase();
        logger.info("Removing login for alias={}", alias);
		Login login = loginProvider.getLoginByAlias(alias);

        if (login == null) {
            logger.error("No login credentials found for alias={}", alias);
            return;
        }

		login = em.merge(login);
		em.remove(login);
		em.flush();
		logger.info("Successfully removed login for alias={}", alias);
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
