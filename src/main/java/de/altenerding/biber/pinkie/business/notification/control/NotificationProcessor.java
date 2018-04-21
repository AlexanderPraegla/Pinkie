package de.altenerding.biber.pinkie.business.notification.control;

import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.notification.entity.CommunicationType;
import de.altenerding.biber.pinkie.business.notification.entity.Message;
import de.altenerding.biber.pinkie.business.notification.entity.NotificationType;
import de.altenerding.biber.pinkie.business.notification.entity.Placeholder;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Event;
import javax.inject.Inject;

public class NotificationProcessor {

	@Inject
	private Event<Message> messageEvent;
	@Inject
	private Logger logger;

	public void sendPasswortResetEmail(Member member, String oneTimePassword) {
		logger.info("Resetting password for alias={}", member.getEmail());
		Message message = new Message();
		message.setCommunicationType(CommunicationType.EMAIL);
		message.setNotificationType(NotificationType.PASSWORD_RESET);
		message.setRecipient(member);
		message.addPlaceholder(Placeholder.PASSWORD, oneTimePassword);
		message.addPlaceholder(Placeholder.FIRSTNAME, member.getFirstName());

		messageEvent.fire(message);
	}
}
