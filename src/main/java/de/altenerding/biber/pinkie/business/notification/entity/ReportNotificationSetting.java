package de.altenerding.biber.pinkie.business.notification.entity;

import de.altenerding.biber.pinkie.business.team.entity.Team;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@NamedQueries({
        @NamedQuery(name = "ReportNotificationSetting.findActiveSettingsByTypeAndTeam",
                query = "SELECT n FROM ReportNotificationSetting n" +
                        " WHERE n.notificationType = :notificationType " +
                        " AND n.team.id = :teamId"),
        @NamedQuery(name = "ReportNotificationSetting.findByMemberId",
                query = "SELECT n FROM ReportNotificationSetting n WHERE n.member.id = :id AND n.communicationType = :communicationType ORDER BY n.team.orderId")
})
@Entity
@Table(name = "report_notification_settings")
@Cacheable(false)
public class ReportNotificationSetting extends NotificationSetting {

    @OneToOne(fetch = FetchType.LAZY)
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
