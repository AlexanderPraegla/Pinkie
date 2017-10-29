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

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
