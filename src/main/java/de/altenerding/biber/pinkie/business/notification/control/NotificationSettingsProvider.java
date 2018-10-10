package de.altenerding.biber.pinkie.business.notification.control;

import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.notification.entity.AdministrationNotificationSetting;
import de.altenerding.biber.pinkie.business.notification.entity.CommunicationType;
import de.altenerding.biber.pinkie.business.notification.entity.GeneralNotificationSetting;
import de.altenerding.biber.pinkie.business.notification.entity.NotificationSetting;
import de.altenerding.biber.pinkie.business.notification.entity.NotificationType;
import de.altenerding.biber.pinkie.business.notification.entity.ReportNotificationSetting;
import de.altenerding.biber.pinkie.business.team.control.TeamProvider;
import de.altenerding.biber.pinkie.business.team.entity.Team;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class NotificationSettingsProvider {

    @PersistenceContext
    private EntityManager em;
    @Inject
    private Logger logger;
    @Inject
    private TeamProvider teamProvider;

    /* General notifications */

    public List<GeneralNotificationSetting> getGeneralNotificationSettingsByCommunicationType(NotificationType notificationType) {
        logger.info("Getting general notification settings for notificaton type = {}", notificationType);
        List<GeneralNotificationSetting> settings = em.createNamedQuery("GeneralNotificationSetting.findSettingsByType", GeneralNotificationSetting.class)
                .setParameter("notificationType", notificationType)
                .getResultList();
        logger.info("Found {} general notification settings for notificaton type = {}", settings.size(), notificationType);
        return settings;
    }

    public List<GeneralNotificationSetting> getGeneralNotificationSettingsByMember(Member member, CommunicationType communicationType) {
        List<GeneralNotificationSetting> generalNotificationSettingsByMember = getActiveGeneralNotificationSettingsByMember(member, communicationType);
        generalNotificationSettingsByMember.addAll(getMissingGeneralNotificationSettingsByMember(member, communicationType));

        generalNotificationSettingsByMember.sort(Comparator.comparingInt(o -> o.getNotificationType().getGroupOrder()));
        return generalNotificationSettingsByMember;
    }

    private List<GeneralNotificationSetting> getActiveGeneralNotificationSettingsByMember(Member member, CommunicationType communicationType) {
        Consumer<List<GeneralNotificationSetting>> fn =
                settings -> settings.forEach(setting -> setting.setDisplayedLabel(setting.getNotificationType().getDescription()));

        return getNotificationSettingsByMember(member,
                communicationType,
                GeneralNotificationSetting.class,
                "GeneralNotificationSetting.findByMemberId", fn);
    }

    private List<GeneralNotificationSetting> getMissingGeneralNotificationSettingsByMember(Member member, CommunicationType communicationType) {
        List<GeneralNotificationSetting> missingSettings = new ArrayList<>();
        List<GeneralNotificationSetting> generalSettings = getActiveGeneralNotificationSettingsByMember(member, communicationType);
        List<NotificationType> notificationTypes = NotificationType.getTypesByEntityClazz(GeneralNotificationSetting.class);

        for (NotificationType notificationType : notificationTypes) {
            boolean hasSetting = false;
            for (GeneralNotificationSetting generalSetting : generalSettings) {
                if (generalSetting.getNotificationType().equals(notificationType)) {
                    hasSetting = true;
                }
            }

            if (!hasSetting) {
                GeneralNotificationSetting generalNotificationSetting = new GeneralNotificationSetting();
                generalNotificationSetting.setMember(member);
                generalNotificationSetting.setActive(false);
                generalNotificationSetting.setDisplayedLabel(notificationType.getDescription());
                generalNotificationSetting.setCommunicationType(communicationType);
                generalNotificationSetting.setNotificationType(notificationType);
                missingSettings.add(generalNotificationSetting);
            }
        }

        return missingSettings;
    }

    /* Administration notifications */

    public List<AdministrationNotificationSetting> getAdministrationNotificationSettingsByCommunicationType(NotificationType notificationType) {
        logger.info("Getting administration notification settings for notificaton type = {}", notificationType);
        List<AdministrationNotificationSetting> settings = em.createNamedQuery("AdministrationNotificationSetting.findSettingsByType", AdministrationNotificationSetting.class)
                .setParameter("notificationType", notificationType)
                .getResultList();
        logger.info("Found {} administration notification settings for notificaton type = {}", settings.size(), notificationType);
        return settings;
    }

    public List<AdministrationNotificationSetting> getAdministrationNotificationSettingsByMember(Member member, CommunicationType communicationType) {
        List<AdministrationNotificationSetting> administrationNotificationSettingsByMember = getActiveAdministrationNotificationSettingsByMember(member, communicationType);
        administrationNotificationSettingsByMember.addAll(getMissingAdministrationNotificationSettingsByMember(member, communicationType));

        administrationNotificationSettingsByMember.sort(Comparator.comparingInt(o -> o.getNotificationType().getGroupOrder()));
        return administrationNotificationSettingsByMember;
    }

    private List<AdministrationNotificationSetting> getActiveAdministrationNotificationSettingsByMember(Member member, CommunicationType communicationType) {
        Consumer<List<AdministrationNotificationSetting>> fn =
                settings -> settings.forEach(setting -> setting.setDisplayedLabel(setting.getNotificationType().getDescription()));

        return getNotificationSettingsByMember(member,
                communicationType,
                AdministrationNotificationSetting.class,
                "AdministrationNotificationSetting.findByMemberId", fn);
    }

    private List<AdministrationNotificationSetting> getMissingAdministrationNotificationSettingsByMember(Member member, CommunicationType communicationType) {
        List<AdministrationNotificationSetting> missingSettings = new ArrayList<>();
        List<AdministrationNotificationSetting> generalSettings = getActiveAdministrationNotificationSettingsByMember(member, communicationType);
        List<NotificationType> notificationTypes = NotificationType.getTypesByEntityClazz(AdministrationNotificationSetting.class);

        for (NotificationType notificationType : notificationTypes) {
            boolean hasSetting = false;
            for (AdministrationNotificationSetting administrationNotificationSetting : generalSettings) {
                if (administrationNotificationSetting.getNotificationType().equals(notificationType)) {
                    hasSetting = true;
                }
            }

            if (!hasSetting) {
                AdministrationNotificationSetting administrationNotificationSetting = new AdministrationNotificationSetting();
                administrationNotificationSetting.setMember(member);
                administrationNotificationSetting.setActive(false);
                administrationNotificationSetting.setDisplayedLabel(notificationType.getDescription());
                administrationNotificationSetting.setCommunicationType(communicationType);
                administrationNotificationSetting.setNotificationType(notificationType);
                missingSettings.add(administrationNotificationSetting);
            }
        }

        return missingSettings;
    }

    /* Report notifications */

    public List<ReportNotificationSetting> getReportNotificationSettingsByCommunicationType(NotificationType notificationType, Team team) {
        logger.info("Getting report notification settings for notificaton type = {}", notificationType);
        List<ReportNotificationSetting> settings = em.createNamedQuery("ReportNotificationSetting.findActiveSettingsByTypeAndTeam", ReportNotificationSetting.class)
                .setParameter("notificationType", notificationType)
                .setParameter("teamId", team.getId())
                .getResultList();

        logger.info("Found {} report notification settings for notificaton type = {}", settings.size(), notificationType);
        return settings;
    }

    public List<ReportNotificationSetting> getReportNotificationSettingsByMember(Member member, CommunicationType communicationType) {
        List<ReportNotificationSetting> reportNotificationSettingsByMember = getActiveReportNotificationSettingsByMember(member, communicationType);

        reportNotificationSettingsByMember.addAll(getMissingReportNotificationSettingsByMember(member, communicationType));

        reportNotificationSettingsByMember.sort((o1, o2) -> (int) (o1.getTeam().getOrderId() - o2.getTeam().getOrderId()));
        return reportNotificationSettingsByMember;
    }

    private List<ReportNotificationSetting> getActiveReportNotificationSettingsByMember(Member member, CommunicationType communicationType) {
        Consumer<List<ReportNotificationSetting>> fn =
                settings -> settings.forEach(setting -> setting.setDisplayedLabel(setting.getTeam().getName()));

        return getNotificationSettingsByMember(member,
                communicationType,
                ReportNotificationSetting.class,
                "ReportNotificationSetting.findByMemberId", fn);
    }

    private List<ReportNotificationSetting> getMissingReportNotificationSettingsByMember(Member member, CommunicationType communicationType) {
        List<ReportNotificationSetting> missingSettings = new ArrayList<>();

        List<ReportNotificationSetting> activeReportSettings = getActiveReportNotificationSettingsByMember(member, communicationType);
        List<Team> currentTeams = teamProvider.getCurrentTeams();
        List<NotificationType> notificationTypes = NotificationType.getTypesByEntityClazz(ReportNotificationSetting.class);

        for (NotificationType notificationType : notificationTypes) {
            for (Team currentTeam : currentTeams) {
                boolean hasSetting = false;
                for (ReportNotificationSetting teamSetting : activeReportSettings) {
                    if (teamSetting.getTeam().equals(currentTeam)) {
                        hasSetting = true;
                    }
                }

                if (!hasSetting) {
                    ReportNotificationSetting reportNotificationSettings = new ReportNotificationSetting();
                    reportNotificationSettings.setTeam(currentTeam);
                    reportNotificationSettings.setDisplayedLabel(currentTeam.getName());
                    reportNotificationSettings.setMember(member);
                    reportNotificationSettings.setActive(false);
                    reportNotificationSettings.setCommunicationType(communicationType);
                    reportNotificationSettings.setNotificationType(notificationType);
                    missingSettings.add(reportNotificationSettings);
                }
            }
        }

        return missingSettings;
    }

    /* Helper methods */

    private <T extends NotificationSetting> List<T> getNotificationSettingsByMember(Member member, CommunicationType communicationType, Class<T> clazz, String namedQuery, Consumer<List<T>> editEntities) {
        logger.info("Loading notification settings for class={} and member id={}", clazz.getSimpleName(), member.getId());
        List<T> notificationSettings = em.createNamedQuery(namedQuery, clazz)
                .setParameter("id", member.getId())
                .setParameter("communicationType", communicationType)
                .getResultList();

        editEntities.accept(notificationSettings);
        notificationSettings.forEach(setting -> setting.setActive(true));

        logger.info("Found {} notification settings for class={}  for member id={}", notificationSettings.size(), clazz.getSimpleName(), member.getId());
        return notificationSettings;
    }
}
