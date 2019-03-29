package de.altenerding.biber.pinkie.business.nuLiga.control;

import de.altenerding.biber.pinkie.business.nuLiga.entity.ClubMeeting;
import de.altenerding.biber.pinkie.business.nuLiga.entity.GroupTableTeam;
import de.altenerding.biber.pinkie.business.team.entity.Team;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

public class NuLigaDataProvider {

    private Logger logger;
    @PersistenceContext
    private EntityManager em;
    private static final int RECENT_MATCHES_RESULTS_DAY_OFFSET = 7;

    public List<GroupTableTeam> getGroupTeamTable(String groupId) {
        logger.info("Loading team ranking for groupId={}", groupId);
        return em.createNamedQuery("GroupTableTeam.findAllByGroupId", GroupTableTeam.class)
                .setParameter("groupId", groupId)
                .getResultList();
    }

    public List<ClubMeeting> getTeamMeetings(Team team) {
        logger.info("Loading team schedule for groupId={} and teamId={}", team.getNuLigaGroupId(), team.getId());
        return em.createNamedQuery("ClubMeeting.getAllByTeamId", ClubMeeting.class)
                .setParameter("teamId", team.getId())
                .getResultList();
    }

    public List<ClubMeeting> getNextUpcomingMeetings(int maxResults) {
        logger.info("Getting next {} upcoming games all teams", maxResults);

        List<ClubMeeting> nextUpcomingMatchDay = getNextUpcomingMatchDay();
        if (nextUpcomingMatchDay.size() >= 3) {
            return nextUpcomingMatchDay.subList(0, maxResults);
        } else {
            return nextUpcomingMatchDay;
        }
    }

    public List<ClubMeeting> getNextUpcomingMatchDay() {
        logger.info("Getting the matches of the next match day");

        LocalDate nextMonday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDateTime targetDate = nextMonday.atStartOfDay();
        Date endDate = Date.from(targetDate.atZone(ZoneId.systemDefault()).toInstant());

        Date startDate = new Date();

        return em.createNamedQuery("ClubMeeting.nextUpcomingGames", ClubMeeting.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    public List<ClubMeeting> getAllUpcomingMatches() {
        logger.info("Getting all upcoming games for all teams");
        Date endDate = Date.from(LocalDate.now().plusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date startDate = new Date();
        return em.createNamedQuery("ClubMeeting.nextUpcomingGames", ClubMeeting.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    public List<ClubMeeting> getRecentResults(int maxResult) {
        logger.info("Getting recent team results for all teams");

        LocalDateTime dateTime = LocalDateTime.now().minusDays(RECENT_MATCHES_RESULTS_DAY_OFFSET);
        Date startDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = new Date();

        return em.createNamedQuery("ClubMeeting.recentResults", ClubMeeting.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setMaxResults(maxResult)
                .getResultList();
    }

    @Inject
    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
