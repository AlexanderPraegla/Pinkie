package de.altenerding.biber.pinkie.business.notification.control;

import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.notification.entity.CommunicationType;
import de.altenerding.biber.pinkie.business.notification.entity.GeneralNotificationSetting;
import de.altenerding.biber.pinkie.business.notification.entity.NotificationSetting;
import de.altenerding.biber.pinkie.business.notification.entity.ReportNotificationSetting;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class NotificationSettingsProcessor {

    @PersistenceContext
    private EntityManager em;
    @Inject
    private Logger logger;
    @Inject
    private NotificationSettingsProvider notificationSettingsProvider;

    public void deleteReportNotificationSetting(NotificationSetting notificationSetting) {
        notificationSetting = em.merge(notificationSetting);
        em.remove(notificationSetting);
        em.flush();
        logger.info("Deleted successfully report notification setting with id={}", notificationSetting.getId());
    }

    public NotificationSetting createNotificationSetting(NotificationSetting notificationSetting) {
        em.persist(notificationSetting);
        em.flush();
        logger.info("Persisted successfully new general notification setting with id={}", notificationSetting.getId());
        return notificationSetting;
    }

    public void updateNotificationSetting(NotificationSetting notificationSetting) {
        notificationSetting = em.merge(notificationSetting);
        em.flush();
        logger.info("Updated successfully report notification setting with id={}", notificationSetting.getId());
    }

    public void deleteReportNotificationSettingForMember(Member member) {
        logger.info("Deleting all notification settings for alias={}", member.getAlias());
        for (CommunicationType communicationType : CommunicationType.values()) {
            List<GeneralNotificationSetting> generalNotificationSettingsByMember = notificationSettingsProvider.getGeneralNotificationSettingsByMember(member, communicationType);
            List<ReportNotificationSetting> reportNotificationSettingsByMember = notificationSettingsProvider.getReportNotificationSettingsByMember(member, communicationType);

            for (GeneralNotificationSetting generalNotificationSetting : generalNotificationSettingsByMember) {
                generalNotificationSetting = em.merge(generalNotificationSetting);
                em.remove(generalNotificationSetting);
            }
            for (ReportNotificationSetting reportNotificationSetting : reportNotificationSettingsByMember) {
                reportNotificationSetting = em.merge(reportNotificationSetting);
                em.remove(reportNotificationSetting);
            }

            em.flush();
        }
    }
}
