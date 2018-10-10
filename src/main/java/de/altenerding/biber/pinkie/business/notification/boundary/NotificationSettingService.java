package de.altenerding.biber.pinkie.business.notification.boundary;

import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.notification.control.NotificationSettingsProcessor;
import de.altenerding.biber.pinkie.business.notification.control.NotificationSettingsProvider;
import de.altenerding.biber.pinkie.business.notification.entity.AdministrationNotificationSetting;
import de.altenerding.biber.pinkie.business.notification.entity.CommunicationType;
import de.altenerding.biber.pinkie.business.notification.entity.GeneralNotificationSetting;
import de.altenerding.biber.pinkie.business.notification.entity.NotificationSetting;
import de.altenerding.biber.pinkie.business.notification.entity.ReportNotificationSetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class NotificationSettingService {

    @Inject
    private NotificationSettingsProcessor notificationSettingsProcessor;
    @Inject
    private NotificationSettingsProvider notificationSettingsProvider;

    public List<ReportNotificationSetting> getReportNotificationSettingsByMember(Member member, CommunicationType communicationType) {
        return notificationSettingsProvider.getReportNotificationSettingsByMember(member, communicationType);
    }

    public List<GeneralNotificationSetting> getGeneralNotificationSettingsByMember(Member member, CommunicationType communicationType) {
        return notificationSettingsProvider.getGeneralNotificationSettingsByMember(member, communicationType);
    }

    public List<AdministrationNotificationSetting> getAdministrationNotificationSettingsByMember(Member member, CommunicationType communicationType) {
        return notificationSettingsProvider.getAdministrationNotificationSettingsByMember(member, communicationType);
    }

    public NotificationSetting createNotificationSetting(NotificationSetting notificationSetting) {
        return notificationSettingsProcessor.createNotificationSetting(notificationSetting);
    }

    public void updateNotificationSetting(NotificationSetting notificationSetting) {
        notificationSettingsProcessor.updateNotificationSetting(notificationSetting);
    }

    public void deleteReportNotificationSetting(NotificationSetting notificationSetting) {
        notificationSettingsProcessor.deleteReportNotificationSetting(notificationSetting);
    }
}
