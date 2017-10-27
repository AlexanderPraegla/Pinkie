package de.altenerding.biber.pinkie.business.team.control;

import de.altenerding.biber.pinkie.business.team.entity.Team;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class TeamProcessor {

	@PersistenceContext
	private EntityManager em;
	private Logger logger;


	public void updateTeam(Team team) {
		logger.info("Updating team with id={}", team.getId());
		em.merge(team);
		em.flush();
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
