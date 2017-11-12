package de.altenerding.biber.pinkie.business.login.control;

import de.altenerding.biber.pinkie.business.login.entity.Login;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.presentation.session.UserSessionBean;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class LoginProvider {

	private Logger logger;
	private UserSessionBean userSessionBean;
	@PersistenceContext
	private EntityManager em;

	Login getLoginByAlias(String alias) {
		logger.info("Getting login credentials for alias={}", alias);
		List<Login> logins = em.createNamedQuery("Login.getByAlias", Login.class).setParameter("alias", alias).getResultList();

		if (logins.size() < 1) {
			logger.error("No login credentials found for alias={}", alias);
			return null;
		}

		return logins.get(0);
	}

	public boolean hasMemberOnetimePasswort() {
		Member member = userSessionBean.getMember();

		Login login = getLoginByAlias(member.getEmail());

		return login.isOnetimePassword();
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Inject
	public void setUserSessionBean(UserSessionBean userSessionBean) {
		this.userSessionBean = userSessionBean;
	}
}
