package de.altenerding.biber.pinkie.business.dean.control;

import de.altenerding.biber.pinkie.business.dean.entity.Dean;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class DeanProcessor {

	private Logger logger;
	@PersistenceContext
	private EntityManager em;

	public void updateDean(Dean dean) {
		logger.info("Updating dean with id={}", dean.getId());
		em.merge(dean);
		em.flush();
	}

	public void updateDeans(List<Dean> deans) {
		for (Dean dean : deans) {
			updateDean(dean);
		}
	}

	public void createDean(Dean dean) {
		logger.info("Creating new dean entry");
		em.persist(dean);
		em.flush();
		logger.info("Created new dean with id={}", dean.getId());
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
