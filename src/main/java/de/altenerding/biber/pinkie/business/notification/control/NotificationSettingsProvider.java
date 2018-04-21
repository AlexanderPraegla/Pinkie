package de.altenerding.biber.pinkie.business.notification.control;

import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.notification.entity.GeneralNotificationSettings;
import de.altenerding.biber.pinkie.business.notification.entity.TeamNotificationSettings;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class NotificationSettingsProvider {

	@PersistenceContext
	private EntityManager em;
	@Inject
	private Logger logger;

	public List<TeamNotificationSettings> getTeamNotificationSettingsByMember(Member member) {
		logger.info("Loading team notification settings for member id={}", member.getId());
		List<TeamNotificationSettings> teamNotificationSettings = em.createNamedQuery("TeamNotificationSettings.findByMemberId", TeamNotificationSettings.class)
				.setParameter("id", member.getId())
				.getResultList();

		logger.info("Found {} team notification settings for member id={}", teamNotificationSettings.size(), member.getId());
		return teamNotificationSettings;
	}

	public List<GeneralNotificationSettings> getGeneralNotificationSettingsByMember(Member member) {
		logger.info("Loading general notification settings for member id={}", member.getId());
		List<GeneralNotificationSettings> generalNotificationSettings = em.createNamedQuery("GeneralNotificationSettings.findByMemberId", GeneralNotificationSettings.class)
				.setParameter("id", member.getId())
				.getResultList();

		logger.info("Found {} general notification settings for member id={}", generalNotificationSettings.size(), member.getId());
		return generalNotificationSettings;
	}
}
