package de.altenerding.biber.pinkie.business.notification.control;

import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.notification.entity.CommunicationTemplate;
import de.altenerding.biber.pinkie.business.notification.entity.Email;
import de.altenerding.biber.pinkie.business.notification.entity.Message;
import de.altenerding.biber.pinkie.business.notification.entity.Placeholder;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Map;

public class MessageSender {

	@Inject
	private TemplateProvider templateProvider;
	@Inject
	private Event<Email> emailEvent;
	@Inject
	private Logger logger;

	public void sendMessage(@Observes Message message) throws Exception {
		CommunicationTemplate template = templateProvider.getCommunicationTemplate(message.getCommunicationType(), message.getTemplateType());

		switch (message.getCommunicationType()) {
			case EMAIL:
				Email email = new Email();
				Member recipient = message.getRecipient();
				String privateEmail = recipient.getPrivateEmail();
				if (StringUtils.isEmpty(privateEmail)) {
					logger.error("No private email available for user {} with id={}", recipient.getFullName(), recipient.getId());
					throw new Exception("No private email available");
				}

				email.setRecipient(privateEmail);
				String subject = replacePlaceholders(template.getSubject(), message.getPlaceholders());
				email.setSubject(subject);
				String body = replacePlaceholders(template.getBody(), message.getPlaceholders());
				email.setBody(body);

				emailEvent.fire(email);
				break;
			case PUSH:
				logger.error("No Push messages implemented yet");
				break;
		}
	}

	private String replacePlaceholders(String text, Map<Placeholder, String> placeholders) {
		for (Map.Entry<Placeholder, String> entry : placeholders.entrySet()) {
			text = text.replace(entry.getKey().getPlaceholder(), entry.getValue());
		}
		return text;
	}
}
