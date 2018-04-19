package de.altenerding.biber.pinkie.business.notification.boundary;

import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.notification.control.NotificationProcessor;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class NotificationService {

	@Inject
	private Logger logger;
	@Inject
	private NotificationProcessor notificationProcessor;

	public void sendPasswortResetEmail(Member member, String passwordNew) {
		logger.info("Dummy method to send email to user if password was resetted");
		notificationProcessor.sendPasswortResetEmail(member, passwordNew);
	}
}
