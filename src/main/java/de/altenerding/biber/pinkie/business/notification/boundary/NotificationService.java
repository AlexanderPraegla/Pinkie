package de.altenerding.biber.pinkie.business.notification.boundary;

import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.notification.control.NotificationProcessor;
import de.altenerding.biber.pinkie.business.notification.control.NotificationSettingsProvider;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

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
}
