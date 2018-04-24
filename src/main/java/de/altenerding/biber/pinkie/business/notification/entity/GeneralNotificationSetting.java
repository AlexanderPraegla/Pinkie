package de.altenerding.biber.pinkie.business.notification.entity;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({
        @NamedQuery(name = "GeneralNotificationSetting.findSettingsByType",
                query = "SELECT n FROM GeneralNotificationSetting n" +
                        " WHERE n.notificationType = :notificationType"),
        @NamedQuery(name = "GeneralNotificationSetting.findByMemberId",
                query = "SELECT n FROM GeneralNotificationSetting n" +
                        " WHERE n.member.id = :id" +
                        " AND n.communicationType = :communicationType")
})
@Entity
@Table(name = "general_notification_settings")
@Cacheable(false)
public class GeneralNotificationSetting extends NotificationSetting {

}
