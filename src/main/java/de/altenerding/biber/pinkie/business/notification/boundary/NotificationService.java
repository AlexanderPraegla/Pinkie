package de.altenerding.biber.pinkie.business.notification.boundary;

import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.notification.control.NotificationProcessor;
import de.altenerding.biber.pinkie.business.notification.control.NotificationSettingsProvider;
import de.altenerding.biber.pinkie.business.notification.entity.GeneralNotificationSettings;
import de.altenerding.biber.pinkie.business.notification.entity.TeamNotificationSettings;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class NotificationService {

	@Inject
	private Logger logger;
	@Inject
	private NotificationProcessor notificationProcessor;
	@Inject
	private NotificationSettingsProvider notificationSettingsProvider;

	public void sendPasswortResetEmail(Member member, String passwordNew) {
		notificationProcessor.sendPasswortResetEmail(member, passwordNew);
	}

	public List<TeamNotificationSettings> getTeamNotificationSettingsByMember(Member member) {
		return notificationSettingsProvider.getTeamNotificationSettingsByMember(member);
	}

	public List<GeneralNotificationSettings> getGeneralNotificationSettingsById(Member member) {
		return notificationSettingsProvider.getGeneralNotificationSettingsByMember(member);
	}
}
