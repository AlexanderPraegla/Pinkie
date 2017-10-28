package de.altenerding.biber.pinkie.business.team.control;

import de.altenerding.biber.pinkie.business.season.control.SeasonProvider;
import de.altenerding.biber.pinkie.business.season.entity.Season;
import de.altenerding.biber.pinkie.business.team.entity.Team;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class TeamProvider {

	@PersistenceContext
	private EntityManager em;
	private Logger logger;
	private SeasonProvider seasonProvider;

	public List<Team> getTeams() {
		logger.info("Loading all Teams of current season from database");
		Season season = seasonProvider.getCurrentSeason();
		return em.createNamedQuery("Team.getCurrentTeams", Team.class)
				.setParameter("seasonId", season.getId())
				.getResultList();
	}

	public Team getTeamById(long id) {
		logger.info("Loading team with id={}", id);
		return em.createNamedQuery("Team.findById", Team.class).setParameter("id", id).getSingleResult();
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Inject
	public void setSeasonProvider(SeasonProvider seasonProvider) {
		this.seasonProvider = seasonProvider;
	}
}
