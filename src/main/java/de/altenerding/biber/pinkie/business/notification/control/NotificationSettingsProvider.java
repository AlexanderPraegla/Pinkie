package de.altenerding.biber.pinkie.business.notification.control;

import de.altenerding.biber.pinkie.business.members.entity.Member;
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

    public List<ReportNotificationSetting> getReportNotificationSettingsByMember(Member member, CommunicationType communicationType) {
        List<ReportNotificationSetting> reportNotificationSettingsByMember = getActiveReportNotificationSettingsByMember(member, communicationType);

        reportNotificationSettingsByMember.addAll(getMissingReportNotificationSettingsByMember(member, communicationType));

        reportNotificationSettingsByMember.sort((o1, o2) -> (int) (o1.getTeam().getOrderId() - o2.getTeam().getOrderId()));
        return reportNotificationSettingsByMember;
    }

    public List<GeneralNotificationSetting> getGeneralNotificationSettingsByMember(Member member, CommunicationType communicationType) {
        List<GeneralNotificationSetting> generalNotificationSettingsByMember = getActiveGeneralNotificationSettingsByMember(member, communicationType);
        generalNotificationSettingsByMember.addAll(getMissingGeneralNotificationSettingsByMember(member, communicationType));

        generalNotificationSettingsByMember.sort(Comparator.comparingInt(o -> o.getNotificationType().getGroupOrder()));
        return generalNotificationSettingsByMember;
    }

    private List<ReportNotificationSetting> getActiveReportNotificationSettingsByMember(Member member, CommunicationType communicationType) {
        Consumer<List<ReportNotificationSetting>> fn =
                settings -> settings.forEach(setting -> setting.setDisplayedLabel(setting.getTeam().getName()));

        return getNotificationSettingsByMember(member,
                communicationType,
                ReportNotificationSetting.class,
                "ReportNotificationSetting.findByMemberId", fn);
    }

    private List<GeneralNotificationSetting> getActiveGeneralNotificationSettingsByMember(Member member, CommunicationType communicationType) {
        Consumer<List<GeneralNotificationSetting>> fn =
                settings -> settings.forEach(setting -> setting.setDisplayedLabel(setting.getNotificationType().getDescription()));

        return getNotificationSettingsByMember(member,
                communicationType,
                GeneralNotificationSetting.class,
                "GeneralNotificationSetting.findByMemberId", fn);
    }

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

    public List<Member> getMembersTeamNotificationSettingsByCommunicationType(CommunicationType communicationType, NotificationType notificationType, Team team) {
        logger.info("Getting team notification settings for communication type = {} and notificaton type = {}", communicationType, notificationType);
        List<Member> settings = em.createNamedQuery("ReportNotificationSetting.findActiveSettingsByTypeAndTeam", Member.class)
                .setParameter("communicationType", communicationType)
                .setParameter("notificationType", notificationType)
                .setParameter("teamId", team.getId())
                .getResultList();

        logger.info("Found {} team notification settings for communication type = {} and notificaton type = {}", settings.size(), communicationType, notificationType);
        return settings;
    }

    public List<Member> getMembersGeneralNotificationSettingsByCommunicationType(CommunicationType communicationType, NotificationType notificationType) {
        logger.info("Getting general notification settings for communication type = {} and notificaton type = {}", communicationType, notificationType);
        List<Member> settings = em.createNamedQuery("GeneralNotificationSetting.findActiveSettingsByType", Member.class)
                .setParameter("communicationType", communicationType)
                .setParameter("notificationType", notificationType)
                .getResultList();
        logger.info("Found {} general notification settings for communication type = {} and notificaton type = {}", settings.size(), communicationType, notificationType);
        return settings;
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

    private List<ReportNotificationSetting> getMissingReportNotificationSettingsByMember(Member member, CommunicationType communicationType) {
        List<ReportNotificationSetting> missingSettings = new ArrayList<>();

        List<ReportNotificationSetting> reportSettings = getActiveReportNotificationSettingsByMember(member, communicationType);
        List<Team> currentTeams = teamProvider.getCurrentTeams();
        List<NotificationType> notificationTypes = NotificationType.getTypesByEntityClazz(ReportNotificationSetting.class);

        for (NotificationType notificationType : notificationTypes) {
            for (Team currentTeam : currentTeams) {
                boolean hasSetting = false;
                for (ReportNotificationSetting teamSetting : reportSettings) {
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
}
