package de.altenerding.biber.pinkie.business.notification.control;

import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.notification.entity.CommunicationType;
import de.altenerding.biber.pinkie.business.notification.entity.Message;
import de.altenerding.biber.pinkie.business.notification.entity.Placeholder;
import de.altenerding.biber.pinkie.business.notification.entity.TemplateType;

import javax.enterprise.event.Event;
import javax.inject.Inject;

public class NotificationProcessor {

	@Inject
	private Event<Message> messageEvent;

	public void sendPasswortResetEmail(Member member, String oneTimePassword) {
		Message message = new Message();
		message.setCommunicationType(CommunicationType.EMAIL);
		message.setTemplateType(TemplateType.PASSWORD_RESET);
		message.setRecipient(member);
		message.addPlaceholder(Placeholder.PASSWORD, oneTimePassword);
		message.addPlaceholder(Placeholder.FIRSTNAME, member.getFirstName());

		messageEvent.fire(message);
	}
}
