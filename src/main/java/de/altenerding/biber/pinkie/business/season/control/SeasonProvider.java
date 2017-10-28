package de.altenerding.biber.pinkie.business.season.control;

import de.altenerding.biber.pinkie.business.season.entity.Season;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class SeasonProvider {

	private Logger logger;
	@PersistenceContext
	private EntityManager em;

	public List<Season> getSeasons() {
		logger.info("Loading all available seasons");
		return em.createNamedQuery("Season.findAll", Season.class).getResultList();
	}

	public Season getCurrentSeason() {
		logger.info("Loading current season");
		return em.createNamedQuery("Season.findAll", Season.class).setMaxResults(1).getResultList().get(0);
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
