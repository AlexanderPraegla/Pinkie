package de.altenerding.biber.pinkie.presentation.notification;

import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.notification.boundary.NotificationService;
import de.altenerding.biber.pinkie.business.notification.entity.GeneralNotificationSettings;
import de.altenerding.biber.pinkie.business.notification.entity.TeamNotificationSettings;
import de.altenerding.biber.pinkie.presentation.session.UserSessionBean;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class NotificationSettingsBean implements Serializable {

	@Inject
	private UserSessionBean userSessionBean;
	@Inject
	private NotificationService notificationService;
	private List<GeneralNotificationSettings> generalNotificationSettings;
	private List<TeamNotificationSettings> teamNotificationSettings;

	public void initNotifications() throws Exception {
		Member member = userSessionBean.getMember();
		if (member == null) {
			throw new Exception("No login available");
		}

		teamNotificationSettings = notificationService.getTeamNotificationSettingsByMember(member);
		generalNotificationSettings = notificationService.getGeneralNotificationSettingsById(member);
	}

	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public List<GeneralNotificationSettings> getGeneralNotificationSettings() {
		return generalNotificationSettings;
	}

	public void setGeneralNotificationSettings(List<GeneralNotificationSettings> generalNotificationSettings) {
		this.generalNotificationSettings = generalNotificationSettings;
	}

	public List<TeamNotificationSettings> getTeamNotificationSettings() {
		return teamNotificationSettings;
	}

	public void setTeamNotificationSettings(List<TeamNotificationSettings> teamNotificationSettings) {
		this.teamNotificationSettings = teamNotificationSettings;
	}
}
