package de.altenerding.biber.pinkie.business.members.control;

import de.altenerding.biber.pinkie.business.members.entity.Member;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.ForbiddenException;

public class LoginProvider {

	@PersistenceContext
	private EntityManager em;
	private Logger logger;

	public Member login(String email, String password) {
		try {
			Member member = em.createNamedQuery("member.findByEmail", Member.class).setParameter("email", email).getSingleResult();

			if (!member.getPassword().equals(password)) {
				logger.info("Password does not match for user with email={}", email);
				throw new ForbiddenException();
			}

			return member;

		} catch (Exception e) {
			logger.error("No user found for email={}", email);
			throw new ForbiddenException();
		}
	}
}
