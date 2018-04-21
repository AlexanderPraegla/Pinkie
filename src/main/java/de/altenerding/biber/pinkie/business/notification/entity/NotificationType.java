package de.altenerding.biber.pinkie.business.notification.entity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum NotificationType {
	PASSWORD_RESET(false, ""),
	MEMBER_NEW(false, ""),


	TEAM_REPORT(true, "Benachrichtigung wenn für Mannschaft ein Bericht hochgeladen wird"),
	ANNOUNCEMENT(true, "Benachrichtigung wenn auf der Startseite eine neue Ankündigung hinzugefügt wird"),
	WEEKLY_IMAGE_UPLOADED(true, "Benachrichtigung wenn ein neues Bild der Woche hochgeladen wird"),
	CLUB_VIDEO_UPLOADED(true, "Benachrichtigung wenn auf der Startseite ein neues Video hochgeladen wird"),
	ALBUM_CREATED(true, "Benachrichtigung wenn ein neues Album in der Gallerie angelegt wird");

	private final boolean configurable;
	private final String configurationDescription;

	NotificationType(boolean configurable, String configurationDescription) {
		this.configurable = configurable;
		this.configurationDescription = configurationDescription;
	}

	public boolean isConfigurable() {
		return configurable;
	}

	public String getConfigurationDescription() {
		return configurationDescription;
	}

	public List<NotificationType> getConfigurableNotificationTypes() {
		return Arrays.stream(values()).filter(NotificationType::isConfigurable).collect(Collectors.toList());
	}
}
