package de.altenerding.biber.pinkie.business.notification.entity;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({
        @NamedQuery(name = "AdministrationNotificationSetting.findSettingsByType",
                query = "SELECT n FROM AdministrationNotificationSetting n" +
                        " WHERE n.notificationType = :notificationType"),
        @NamedQuery(name = "AdministrationNotificationSetting.findByMemberId",
                query = "SELECT n FROM AdministrationNotificationSetting n" +
                        " WHERE n.member.id = :id" +
                        " AND n.communicationType = :communicationType")
})
@Entity
@Table(name = "administation_notification_settings")
@Cacheable(false)
public class AdministrationNotificationSetting extends NotificationSetting {

}
