package de.altenerding.biber.pinkie.business.referee.control;

import de.altenerding.biber.pinkie.business.referee.entity.Referee;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class RefereeProcessor {

	private Logger logger;
	@PersistenceContext
	private EntityManager em;

	public void updateReferee(Referee referee) {
		logger.info("Updating referee with id={}", referee.getId());
		em.merge(referee);
		em.flush();
	}

	public void updateReferees(List<Referee> referees) {
		for (Referee referee : referees) {
			updateReferee(referee);
		}
	}

	public void createReferee(Referee referee) {
		logger.info("Creating new referee entry");
		em.persist(referee);
		em.flush();
		logger.info("Created new referee with id={}", referee.getId());
	}

	public void removeReferee(Referee referee) {
		logger.info("Deleting referee with id={}", referee.getId());
		referee = em.merge(referee);
		em.remove(referee);
		em.flush();
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
