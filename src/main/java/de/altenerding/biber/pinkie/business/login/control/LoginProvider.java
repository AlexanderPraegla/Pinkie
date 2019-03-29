package de.altenerding.biber.pinkie.business.login.control;

import de.altenerding.biber.pinkie.business.login.entity.Login;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class LoginProvider {

	private Logger logger;
	@PersistenceContext
	private EntityManager em;

	public Login getLoginByAlias(String alias) throws Exception {
		alias = alias.toLowerCase();
		logger.info("Getting login credentials for alias={}", alias);
		List<Login> logins = em.createNamedQuery("Login.getByAlias", Login.class).setParameter("alias", alias).getResultList();

		if (logins.size() < 1) {
			logger.error("No login credentials found for alias={}", alias);
			return null;
		}

		return logins.get(0);
	}

	public boolean hasMemberOnetimePasswort(Member member) throws Exception {
        String alias = member.getAlias();
		logger.info("Checking if user with alias={}  has onetime password", alias);

		Login login = getLoginByAlias(alias);

		if (login == null) {
			logger.error("No login credentials found for alias={}", alias);
			throw new Exception("No login credentials found");
		}

		return login.isOnetimePassword();
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
