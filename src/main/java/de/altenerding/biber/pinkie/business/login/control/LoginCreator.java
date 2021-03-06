package de.altenerding.biber.pinkie.business.login.control;

import de.altenerding.biber.pinkie.business.login.entity.Login;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Base64;
import java.util.Random;

public class LoginCreator {

    private Logger logger;
    @PersistenceContext
    private EntityManager em;
    private SecurityProvider securityProvider;
    private Authenticator authenticator;

    /**
     * Creating a new login entry with hashed password and salt
     *
     * @param alias login alias
     * @return The saved one time password for notification purpose
     * @throws Exception If an error occurs during hashing or salting
     */
	public String createLogin(String alias) {
		alias = alias.toLowerCase();
        logger.info("Creating new login for alias={}", alias);
        byte[] salt = new byte[64];
        new Random().nextBytes(salt);

        Login login = new Login();
        login.setAlias(alias);
        login.setSalt(Base64.getEncoder().encodeToString(salt));
        login.setOnetimePassword(true);

        String randomPassword = authenticator.getRandomPassword();
        login.setPassword(securityProvider.hashPassword(randomPassword.toCharArray(), salt));
        em.persist(login);
        em.flush();

        return randomPassword;
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
    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }
}
