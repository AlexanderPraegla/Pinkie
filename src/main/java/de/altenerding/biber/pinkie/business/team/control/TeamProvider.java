package de.altenerding.biber.pinkie.business.team.control;

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

	public List<Team> getTeams() {
		logger.info("Loading all Teams from database");
		return em.createNamedQuery("Team.findAll", Team.class).getResultList();
	}

	public Team getTeamById(long id) {
		logger.info("Loading team with id={}", id);
		return em.createNamedQuery("Team.findById", Team.class).setParameter("id", id).getSingleResult();
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
