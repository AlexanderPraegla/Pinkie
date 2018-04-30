package de.altenerding.biber.pinkie.business.season.boundary;

import de.altenerding.biber.pinkie.business.season.entity.Season;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class SeasonService {

	private Logger logger;
	@PersistenceContext
	private EntityManager em;

	public List<Season> getSeasons() {
		logger.info("Loading all available seasons");
		return em.createNamedQuery("Season.findAll", Season.class).getResultList();
	}

	public Season getCurrentSeason() {
		logger.info("Loading current season");
        List<Season> resultList = em.createNamedQuery("Season.findAll", Season.class).setMaxResults(1).getResultList();
        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
	}

	public Season createSeason(String name) {
		logger.info("Creating new season with name={}", name);
		Season season = new Season();
		season.setName(name);
		em.persist(season);
		logger.info("Created season for name={} with id={}", name, season.getId());
		return season;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
