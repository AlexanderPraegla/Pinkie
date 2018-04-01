package de.altenerding.biber.pinkie.business.nuLiga.control;

import de.altenerding.biber.pinkie.business.nuLiga.entity.StandingEntry;
import de.altenerding.biber.pinkie.business.nuLiga.entity.TeamScheduleEntry;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class NuLigaDataProvider {

	private Logger logger;
	@PersistenceContext
	private EntityManager em;

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

	public List<TeamScheduleEntry> getUpcomingGames() {
		logger.info("Getting all upcoming games for all teams");
		return em.createNamedQuery("TeamScheduleEntry.upcomingGames", TeamScheduleEntry.class)
				.getResultList();
	}

	public List<TeamScheduleEntry> getRecentResults() {
		logger.info("Getting recent team results for all teams");

		int daysOffset = 5;
		LocalDateTime dateTime = LocalDateTime.now().minusDays(daysOffset);
		Date startDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
		Date endDate = new Date();
		List<TeamScheduleEntry> resultList = em.createNamedQuery("TeamScheduleEntry.recentResults", TeamScheduleEntry.class)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getResultList();

		return resultList;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
