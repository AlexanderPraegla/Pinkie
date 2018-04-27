package de.altenerding.biber.pinkie.business.members.bounday;

import de.altenerding.biber.pinkie.business.login.control.Authenticator;
import de.altenerding.biber.pinkie.business.login.control.LoginCreator;
import de.altenerding.biber.pinkie.business.login.control.LoginModifier;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.notification.control.MessageSender;
import de.altenerding.biber.pinkie.business.notification.entity.CommunicationType;
import de.altenerding.biber.pinkie.business.notification.entity.NotificationType;
import de.altenerding.biber.pinkie.business.notification.entity.Placeholder;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class MemberService implements Serializable {

	@PersistenceContext
	private EntityManager em;
	@Inject
	private Logger logger;
	@Inject
	private LoginCreator loginCreator;
	@Inject
	private MessageSender messageSender;
	@Inject
	private LoginModifier loginModifier;
	@Inject
	private Authenticator authenticator;

	public Member getMemberById(long id) {
		logger.info("Getting member by id={}", id);
		try {
			return em.createNamedQuery("member.findById", Member.class).setParameter("id", id).getSingleResult();
		} catch (Exception e) {
			logger.error("Exception while leading member by id={}", id, e);
			return null;
		}
	}

	public Member getMemberByEmail(String email) {
		logger.info("Getting member by email={}", email);
		try {
			return em.createNamedQuery("member.findByEmail", Member.class).setParameter("email", email).getSingleResult();
		} catch (Exception e) {
			logger.error("Exception while leading member by email={}", email, e);
			return null;
		}
	}

	public Member getMemberByAlias(String alias) {
		logger.info("Getting member by alias={}", alias);
		try {
			return em.createNamedQuery("member.findByAlias", Member.class).setParameter("alias", alias).getSingleResult();
		} catch (Exception e) {
			logger.info("Exception while loading member by alias={}", alias, e);
			return null;
		}
	}

	public List<Member> getMembers() {
		return em.createNamedQuery("member.findAll", Member.class).getResultList();
	}

	/**
	 * Create new member and a login with random one time password.
	 * If new member has set a private email address a notification is send containing the one time password for the first login
	 *
	 * @param member New member to persist
	 * @return The created member
	 * @throws Exception
	 */
	public Member createMember(Member member) throws Exception {

		if (member.getAlias() == null) {
			String alias = member.getFirstName() + "." + member.getLastName();
			member.setAlias(alias);
		}

		logger.info("Persisting member with name={}", member.getFullName());
		em.persist(member);
		em.flush();

		String oneTimePassword = loginCreator.createLogin(member.getAlias());

		Map<Placeholder, String> placeholders = new HashMap<>();
		placeholders.put(Placeholder.ALIAS, member.getAlias());
		placeholders.put(Placeholder.PASSWORD, oneTimePassword);
		messageSender.sendSingleNotification(member, CommunicationType.EMAIL, NotificationType.MEMBER_NEW, placeholders);

		return member;
	}

	public void updateMember(Member memberNew) throws Exception {
		logger.info("Updating member with id={}", memberNew.getId());

		Member memberOld = getMemberById(memberNew.getId());

		if (!StringUtils.equals(memberOld.getAlias(), memberNew.getAlias())) {
			loginModifier.updateAlias(memberOld.getAlias(), memberNew.getAlias());

			Map<Placeholder, String> placeholders = new HashMap<>();
			placeholders.put(Placeholder.ALIAS, memberNew.getAlias());
			messageSender.sendSingleNotification(memberNew, CommunicationType.EMAIL, NotificationType.ALIAS_CHANGED, placeholders);
		}

		em.merge(memberNew);
		em.flush();

	}

	public void deleteMember(Member member) throws Exception {
		logger.info("Removing member with id={}", member.getId());
		member = em.merge(member);
		em.remove(member);
		em.flush();
		logger.info("Successfully removed member with id={}", member.getId());

		loginModifier.removeLoginForAlias(member.getAlias());

		messageSender.sendSingleNotification(member, CommunicationType.EMAIL, NotificationType.MEMBER_DELETED);
	}

	public void resetMemberPassword(Member member) throws Exception {
		String randomPassword = authenticator.getRandomPassword();
		loginModifier.savePassword(member.getAlias(), randomPassword, true);
		messageSender.sendPasswortResetEmail(member, randomPassword);
	}


	public void changePassword(Member member, String passwordOld, String passwordNew) throws Exception {
		logger.info("Changing password for alias={}", member.getAlias());

		if (!authenticator.validate(member.getAlias(), passwordOld)) {
			throw new Exception("Invalid password");
		}

		loginModifier.savePassword(member.getAlias(), passwordNew, false);

		messageSender.sendSingleNotification(member, CommunicationType.EMAIL, NotificationType.PASSWORD_CHANGED);
	}

	public void changeForgotPassword(Member member) throws Exception {
		String oneTimePassword = authenticator.getRandomPassword();
		loginModifier.savePassword(member.getAlias(), oneTimePassword, true);
		messageSender.sendPasswortResetEmail(member, oneTimePassword);
	}
}
