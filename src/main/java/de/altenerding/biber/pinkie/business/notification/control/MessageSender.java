package de.altenerding.biber.pinkie.business.notification.control;

import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.notification.entity.CommunicationTemplate;
import de.altenerding.biber.pinkie.business.notification.entity.CommunicationType;
import de.altenerding.biber.pinkie.business.notification.entity.Email;
import de.altenerding.biber.pinkie.business.notification.entity.GeneralNotificationSetting;
import de.altenerding.biber.pinkie.business.notification.entity.Message;
import de.altenerding.biber.pinkie.business.notification.entity.NotificationSetting;
import de.altenerding.biber.pinkie.business.notification.entity.NotificationType;
import de.altenerding.biber.pinkie.business.notification.entity.Placeholder;
import de.altenerding.biber.pinkie.business.notification.entity.ReportNotificationSetting;
import de.altenerding.biber.pinkie.business.team.entity.Team;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class MessageSender {

	@Inject
	private TemplateProvider templateProvider;
	@Inject
	private NotificationSettingsProvider notificationSettingsProvider;
	@Inject
	private Event<Message> messageEvent;
	@Inject
	private Event<Email> emailEvent;
	@Inject
	private Logger logger;

	public void sendPasswortResetEmail(Member member, String oneTimePassword) {
		logger.info("Resetting password for alias={}", member.getAlias());
		Message message = new Message();
		message.setCommunicationType(CommunicationType.EMAIL);
		message.setNotificationType(NotificationType.PASSWORD_RESET);
		message.setRecipient(member);
		message.addPlaceholder(Placeholder.PASSWORD, oneTimePassword);
		message.addPlaceholder(Placeholder.FIRSTNAME, member.getFirstName());

		messageEvent.fire(message);
	}

	public void sendSingleNotification(Member member, CommunicationType communicationType, NotificationType notificationType, Map<Placeholder, String> placeholders) {
		Message message = createMessage(member, communicationType, notificationType, placeholders);
		messageEvent.fire(message);
	}

	public void sendNotifications(NotificationType notificationType, Map<Placeholder, String> placeholders) {
		List<GeneralNotificationSetting> notificationSettings = notificationSettingsProvider.getGeneralNotificationSettingsByCommunicationType(notificationType);
		sendNotifications(notificationSettings, placeholders);
	}

	public void sendReportNotifications(NotificationType notificationType, Team team, Map<Placeholder, String> placeholders) {
		List<ReportNotificationSetting> notificationSettings = notificationSettingsProvider.getReportNotificationSettingsByCommunicationType(notificationType, team);
		sendNotifications(notificationSettings, placeholders);

	}

	private void sendNotifications(List<? extends NotificationSetting> notificationSettings, Map<Placeholder, String> placeholders) {
		for (NotificationSetting setting : notificationSettings) {
			Message message = createMessage(setting.getMember(), setting.getCommunicationType(), setting.getNotificationType(), placeholders);
			messageEvent.fire(message);
		}
	}

	private Message createMessage(Member member, CommunicationType communicationType, NotificationType notificationType, Map<Placeholder, String> placeholders) {
		Message message = new Message();
		message.setCommunicationType(communicationType);
		message.setNotificationType(notificationType);
		message.setRecipient(member);
		message.addPlaceholders(placeholders);
		message.addPlaceholder(Placeholder.FIRSTNAME, member.getFirstName());

		return message;
	}

	public void sendMessage(@Observes Message message) throws Exception {
		CommunicationTemplate template = templateProvider.getCommunicationTemplate(message.getCommunicationType(), message.getNotificationType());

		switch (message.getCommunicationType()) {
			case EMAIL:
				Email email = new Email();
				Member recipient = message.getRecipient();
				if (StringUtils.isEmpty(recipient.getEmail())) {
					logger.error("No private email available for user {} with id={}", recipient.getFullName(), recipient.getId());
					logger.error("Sending NO email!");
					break;
				}

				email.setRecipient(recipient.getEmail());
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
