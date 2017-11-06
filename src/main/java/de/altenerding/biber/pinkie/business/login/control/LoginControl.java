package de.altenerding.biber.pinkie.business.login.control;

import de.altenerding.biber.pinkie.business.login.entity.Login;
import de.altenerding.biber.pinkie.business.members.control.MemberProvider;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.List;

public class LoginControl {

	private static final int KEY_LENGTH = 256;
	private static final int ITERATIONS = 500;
	private Logger logger;
	private MemberProvider memberProvider;
	@PersistenceContext
	private EntityManager em;

	public Member login(String alias, String password) throws IOException {
		logger.info("Checking login credentials for alias={}", alias);
		List<Login> logins = em.createNamedQuery("Login.getByAlias", Login.class).setParameter("alias", alias).getResultList();

		if (logins.size() < 1) {
			logger.error("No login credentials found for alias={}", alias);
			return null;
		}

		Login login = logins.get(0);

		if (login.getLoginCount() >= 3) {
			logger.error("Login tries already at {}", login.getLoginCount());
			return null;
		}

		byte[] salt = Base64.getDecoder().decode(login.getSalt());
		String hashedPassword = hashPassword(password.toCharArray(), salt);

		if (StringUtils.equals(login.getPassword(), hashedPassword)) {
			login.setLoginCount(0);
			em.merge(login);
			em.flush();
			return memberProvider.getMemberByEmail(alias);
		} else {
			login.setLoginCount(login.getLoginCount() + 1);
			em.merge(login);
			em.flush();
			return null;
		}
	}

	public void createLogin(String alias, String password) throws Exception {
		logger.info("Creating login for alias={}", alias);
		try {
			byte[] salt = new byte[64];
			SecureRandom.getInstanceStrong().nextBytes(salt);

			Login login = new Login();
			login.setAlias(alias);
			login.setSalt(Base64.getEncoder().encodeToString(salt));
			login.setPassword(hashPassword(password.toCharArray(), salt));

			em.persist(login);
			em.flush();
		} catch (NoSuchAlgorithmException e) {
			logger.info("Error while creating login", e);
			throw e;
		}
	}

	private String hashPassword(final char[] password, final byte[] salt) {

		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
			PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
			SecretKey key = skf.generateSecret(spec);
			byte[] hashedPassword = key.getEncoded();
			return Base64.getEncoder().encodeToString(hashedPassword);

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Inject
	public void setMemberProvider(MemberProvider memberProvider) {
		this.memberProvider = memberProvider;
	}
}
