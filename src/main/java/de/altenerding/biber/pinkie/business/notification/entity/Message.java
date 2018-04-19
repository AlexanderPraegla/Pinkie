package de.altenerding.biber.pinkie.business.notification.entity;

import de.altenerding.biber.pinkie.business.members.entity.Member;

import java.util.HashMap;
import java.util.Map;

public class Message {

	private CommunicationType communicationType;
	private TemplateType templateType;
	private Member recipient;
	private Map<Placeholder, String> placeholders = new HashMap<>();

	public CommunicationType getCommunicationType() {
		return communicationType;
	}

	public void setCommunicationType(CommunicationType communicationType) {
		this.communicationType = communicationType;
	}

	public TemplateType getTemplateType() {
		return templateType;
	}

	public void setTemplateType(TemplateType templateType) {
		this.templateType = templateType;
	}

	public Map<Placeholder, String> getPlaceholders() {
		return placeholders;
	}

	public void setPlaceholders(Map<Placeholder, String> placeholders) {
		this.placeholders = placeholders;
	}

	public void addPlaceholder(Placeholder placeholder, String value) {
		this.placeholders.put(placeholder, value);
	}

	public Member getRecipient() {
		return recipient;
	}

	public void setRecipient(Member recipient) {
		this.recipient = recipient;
	}
}