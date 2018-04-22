package de.altenerding.biber.pinkie.business.notification.control;

import de.altenerding.biber.pinkie.business.notification.entity.NotificationSetting;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class NotificationSettingsProcessor {

    @PersistenceContext
    private EntityManager em;
    @Inject
    private Logger logger;

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
}
