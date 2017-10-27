package de.altenerding.biber.pinkie.business.weeklyimage.control;

import de.altenerding.biber.pinkie.business.weeklyimage.entity.WeeklyImage;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class WeeklyImageProcessor {

	private Logger logger;
	@PersistenceContext
	private EntityManager em;

	public void archiveWeeklyImage(long weeklyImageId) {
		logger.info("Archving weekly image with id={}", weeklyImageId);
		em.createNamedQuery("WeeklyImage.archiveImage", WeeklyImage.class)
				.setParameter("id", weeklyImageId)
				.executeUpdate();
	}

	public void saveWeeklyImage(WeeklyImage weeklyImage) {
		logger.info("Persisting new weekly image");
		em.persist(weeklyImage);
		em.flush();
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
