package de.altenerding.biber.pinkie.business.notification.entity;

import de.altenerding.biber.pinkie.business.team.entity.Team;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@NamedQueries({
		@NamedQuery(name = "TeamNotificationSettings.findByType",
				query = "SELECT n FROM TeamNotificationSettings n WHERE n.communicationType = :communicationType AND n.notificationType = :templateType"),
		@NamedQuery(name = "TeamNotificationSettings.findByMemberId",
				query = "SELECT n FROM TeamNotificationSettings n WHERE n.member.id = :id")
})
@Entity
@Table(name = "team_notification_settings")
public class TeamNotificationSettings extends GeneralNotificationSettings {

	@OneToOne(fetch = FetchType.LAZY)
	private Team team;

	public TeamNotificationSettings() {
		setNotificationType(NotificationType.TEAM_REPORT);
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
}
