package de.altenerding.biber.pinkie.business.dean.control;

import de.altenerding.biber.pinkie.business.dean.entity.Dean;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class DeanProvider {

	private Logger logger;
	@PersistenceContext
	private EntityManager em;

	public List<Dean> getCurrentDeans() {
		logger.info("Loading current deans");
		return em.createNamedQuery("Dean.getCurrentDeans", Dean.class).getResultList();
	}

	public Dean getDeanById(long deanId) {
		logger.info("Loading dean with id={}");
		Dean dean = em.find(Dean.class, deanId);
		em.detach(dean);
		return dean;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
