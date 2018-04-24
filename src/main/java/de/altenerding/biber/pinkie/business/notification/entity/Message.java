package de.altenerding.biber.pinkie.business.notification.entity;

import de.altenerding.biber.pinkie.business.members.entity.Member;

import java.util.HashMap;
import java.util.Map;

public class Message {

	private CommunicationType communicationType;
	private NotificationType notificationType;
	private Member recipient;
    private final Map<Placeholder, String> placeholders = new HashMap<>();

	public CommunicationType getCommunicationType() {
		return communicationType;
	}

	public void setCommunicationType(CommunicationType communicationType) {
		this.communicationType = communicationType;
	}

	public NotificationType getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

	public Map<Placeholder, String> getPlaceholders() {
		return placeholders;
	}

	public void addPlaceholder(Placeholder placeholder, String value) {
		this.placeholders.put(placeholder, value);
	}

    public void addPlaceholders(Map<Placeholder, String> placeholders) {
        this.placeholders.putAll(placeholders);
    }

	public Member getRecipient() {
		return recipient;
	}

	public void setRecipient(Member recipient) {
		this.recipient = recipient;
	}
}
