package de.altenerding.biber.pinkie.business.notification.entity;

public enum NotificationGroup {
    GENERAL(GeneralNotificationSetting.class, "label.notificationGroup.GENERAL", "description.notificationGroup.GENERAL"),
    ADMINISTRATION(AdministrationNotificationSetting.class, "label.notificationGroup.ADMINISTRATION", "description.notificationGroup.ADMINISTRATION"),
    REPORTS(ReportNotificationSetting.class, "label.notificationGroup.REPORT", "description.notificationGroup.REPORTS");

    private final Class entityClazz;
    private final String labelKey;
    private final String descriptionKey;

    NotificationGroup(Class entityClazz, String labelKey, String descriptionKey) {
        this.entityClazz = entityClazz;
        this.labelKey = labelKey;
        this.descriptionKey = descriptionKey;
    }

    public Class getEntityClazz() {
        return entityClazz;
    }

    public String getLabelKey() {
        return labelKey;
    }

    public String getDescriptionKey() {
        return descriptionKey;
    }
}
