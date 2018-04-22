package de.altenerding.biber.pinkie.presentation.notification;

import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import de.altenerding.biber.pinkie.business.notification.boundary.NotificationSettingService;
import de.altenerding.biber.pinkie.business.notification.entity.CommunicationType;
import de.altenerding.biber.pinkie.business.notification.entity.GeneralNotificationSetting;
import de.altenerding.biber.pinkie.business.notification.entity.NotificationSetting;
import de.altenerding.biber.pinkie.business.notification.entity.ReportNotificationSetting;
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
    private NotificationSettingService notificationSettingService;
    private List<GeneralNotificationSetting> generalNotificationSettings;
    private List<ReportNotificationSetting> reportNotificationSettings;

    public void initCurrentNotifications() throws Exception {
		Member member = userSessionBean.getMember();
		if (member == null) {
			throw new Exception("No login available");
		}

        reportNotificationSettings = notificationSettingService.getReportNotificationSettingsByMember(member, CommunicationType.EMAIL);
        generalNotificationSettings = notificationSettingService.getGeneralNotificationSettingsByMember(member, CommunicationType.EMAIL);
    }

    public List<ReportNotificationSetting> getReportNotificationSettings(CommunicationType communicationType) {
        Member member = userSessionBean.getMember();
        if (reportNotificationSettings == null) {
            reportNotificationSettings = notificationSettingService.getReportNotificationSettingsByMember(member, communicationType);
        }

        return reportNotificationSettings;
    }

    public List<GeneralNotificationSetting> getGeneralNotificationSettings(CommunicationType communicationType) {
        Member member = userSessionBean.getMember();
        if (generalNotificationSettings == null) {
            generalNotificationSettings = notificationSettingService.getGeneralNotificationSettingsByMember(member, communicationType);
        }

		return generalNotificationSettings;
	}

    @Access(role = Role.MEMBER)
    public void deleteOrCreateNotificationSetting(NotificationSetting notificationSetting) {
        if (notificationSetting.getId() == 0) {
            notificationSetting = notificationSettingService.createNotificationSetting(notificationSetting);
            notificationSetting.setActive(true);
        } else {
            notificationSettingService.deleteReportNotificationSetting(notificationSetting);
            notificationSetting.setActive(false);
            notificationSetting.setId(0);
        }
	}
}
