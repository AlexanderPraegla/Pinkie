package de.altenerding.biber.pinkie.business.referee.control;

import de.altenerding.biber.pinkie.business.referee.entity.Referee;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class RefereeProvider {

	private Logger logger;
	@PersistenceContext
	private EntityManager em;

	public List<Referee> getCurrentReferees() {
		logger.info("Loading current referees");
		return em.createNamedQuery("Referee.getCurrentDeans", Referee.class).getResultList();
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
