package de.altenerding.biber.pinkie.business.team.control;

import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.season.boundary.SeasonService;
import de.altenerding.biber.pinkie.business.season.entity.Season;
import de.altenerding.biber.pinkie.business.team.entity.Team;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class TeamProvider {

	@PersistenceContext
	private EntityManager em;
	private Logger logger;
	private SeasonService seasonSerivce;

	public List<Team> getCurrentTeams() {
		logger.info("Loading all Teams of current season from database");
		Season season = seasonSerivce.getCurrentSeason();

        if (season == null) {
            return new ArrayList<>();
        }

		return em.createNamedQuery("Team.getCurrentTeams", Team.class)
				.setParameter("seasonId", season.getId())
				.getResultList();
	}

	public Team getTeamById(long id) {
		logger.info("Loading team with id={}", id);
		return em.createNamedQuery("Team.findById", Team.class).setParameter("id", id).getSingleResult();
	}

	public List<Member> getAllTrainers() {
		List<Member> trainers = em.createNamedQuery("Team.allTrainer", Member.class).getResultList();
		logger.info("Found {} trainers", trainers.size());
		return trainers;
	}

	public List<Team> getTeamsBySeason(Season season) {
		logger.info("Loading teams with season={}", season);
		return em.createNamedQuery("Teams.findBySeason", Team.class).setParameter("season", season).getResultList();
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Inject
	public void setSeasonSerivce(SeasonService seasonSerivce) {
		this.seasonSerivce = seasonSerivce;
	}
}
