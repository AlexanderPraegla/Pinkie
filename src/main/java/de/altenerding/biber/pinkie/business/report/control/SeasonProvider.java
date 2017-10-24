package de.altenerding.biber.pinkie.business.report.control;

import de.altenerding.biber.pinkie.business.report.entity.Season;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class SeasonProvider {

	private Logger logger;
	@PersistenceContext
	private EntityManager em;


	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public List<Season> getSeasons() {
		logger.info("Loading all available seasons");
		return em.createNamedQuery("Season.findAll", Season.class).getResultList();
	}
}
