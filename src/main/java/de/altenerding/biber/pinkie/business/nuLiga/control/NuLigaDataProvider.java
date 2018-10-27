package de.altenerding.biber.pinkie.business.nuLiga.control;

import de.altenerding.biber.pinkie.business.nuLiga.entity.StandingEntry;
import de.altenerding.biber.pinkie.business.nuLiga.entity.TeamScheduleEntry;
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

    public List<StandingEntry> getTeamStandings(long teamId) {
        logger.info("Loading team standing for teamId={}", teamId);
        return em.createNamedQuery("StandingEntry.findAllByTeamId", StandingEntry.class)
                .setParameter("teamId", teamId)
                .getResultList();
    }

    public List<TeamScheduleEntry> getTeamSchedule(long teamId) {
        logger.info("Loading team schedule for teamId={}", teamId);
        return em.createNamedQuery("TeamScheduleEntry.getAllByTeamId", TeamScheduleEntry.class)
                .setParameter("teamId", teamId)
                .getResultList();
    }

    public List<TeamScheduleEntry> getNextUpcomingMatches(int maxResults) {
        logger.info("Getting next {} upcoming games all teams", maxResults);

        List<TeamScheduleEntry> nextUpcomingMatchDay = getNextUpcomingMatchDay();
        if (nextUpcomingMatchDay.size() >= 3) {
            return nextUpcomingMatchDay.subList(0, maxResults);
        } else {
            return nextUpcomingMatchDay;
        }
    }

    public List<TeamScheduleEntry> getNextUpcomingMatchDay() {
        logger.info("Getting the matches of the next match day");

        LocalDate nextMonday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDateTime targetDate = nextMonday.atStartOfDay();
        Date endDate = Date.from(targetDate.atZone(ZoneId.systemDefault()).toInstant());

        Date startDate = new Date();

        return em.createNamedQuery("TeamScheduleEntry.nextUpcomingGames", TeamScheduleEntry.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    public List<TeamScheduleEntry> getAllUpcomingMatches() {
        logger.info("Getting all upcoming games for all teams");
        return em.createNamedQuery("TeamScheduleEntry.upcomingGames", TeamScheduleEntry.class)
                .getResultList();
    }

    public List<TeamScheduleEntry> getRecentResults(int maxResult) {
        logger.info("Getting recent team results for all teams");

        LocalDateTime dateTime = LocalDateTime.now().minusDays(RECENT_MATCHES_RESULTS_DAY_OFFSET);
        Date startDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = new Date();

        return em.createNamedQuery("TeamScheduleEntry.recentResults", TeamScheduleEntry.class)
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
