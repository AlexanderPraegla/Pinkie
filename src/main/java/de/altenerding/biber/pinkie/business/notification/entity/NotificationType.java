package de.altenerding.biber.pinkie.business.notification.entity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum NotificationType {
    PASSWORD_RESET(false, "", null, 0),
    MEMBER_NEW(false, "", null, 0),


    REPORT(true, "Benachrichtigen wenn ein Bericht hochgeladen wird", NotificationGroup.REPORTS, 0),
    ANNOUNCEMENT(true, "Benachrichtigen wenn auf der Startseite eine neue Ankündigung hinzugefügt wird", NotificationGroup.GENERAL, 0),
    WEEKLY_IMAGE_UPLOADED(true, "Benachrichtigen wenn ein neues Bild der Woche hochgeladen wird", NotificationGroup.GENERAL, 5),
    CLUB_VIDEO_UPLOADED(true, "Benachrichtigen wenn auf der Startseite ein neues Video hochgeladen wird", NotificationGroup.GENERAL, 10),
    ALBUM_CREATED(true, "Benachrichtigen wenn ein neues Album in der Gallerie angelegt wird", NotificationGroup.GENERAL, 15);

	private final boolean configurable;
    private final String description;
    private final NotificationGroup notificationGroup;
    private final int groupOrder;

    NotificationType(boolean configurable, String description, NotificationGroup notificationGroup, int groupOrder) {
		this.configurable = configurable;
        this.description = description;
        this.notificationGroup = notificationGroup;
        this.groupOrder = groupOrder;
	}

	public boolean isConfigurable() {
		return configurable;
	}

    public static List<NotificationType> getConfigurableNotificationTypes() {
		return Arrays.stream(values()).filter(NotificationType::isConfigurable).collect(Collectors.toList());
	}

    public static List<NotificationType> getTypesbyGroup(NotificationGroup notificationGroup) {
        return Arrays.stream(values()).filter(notificationType -> notificationType.getNotificationGroup().equals(notificationGroup)).collect(Collectors.toList());
    }

    public static List<NotificationType> getTypesByEntityClazz(Class entityClazz) {
        return Arrays.stream(values()).filter(notificationType -> notificationType.getEntityClazz().equals(entityClazz)).collect(Collectors.toList());
    }

    public String getDescription() {
        return description;
    }

    public Class getEntityClazz() {
        if (notificationGroup != null) {
            return notificationGroup.getEntityClazz();
        } else {
            return Class.class;
        }
    }

    public NotificationGroup getNotificationGroup() {
        return notificationGroup;
    }

    public int getGroupOrder() {
        return groupOrder;
    }
}
