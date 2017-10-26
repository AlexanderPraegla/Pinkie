package de.altenerding.biber.pinkie.business.nuLiga.control;

import de.altenerding.biber.pinkie.business.nuLiga.entity.StandingEntry;
import de.altenerding.biber.pinkie.business.nuLiga.entity.TeamScheduleEntry;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class NuLigaProvider {

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

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
