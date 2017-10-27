package de.altenerding.biber.pinkie.business.weeklyimage.control;

import de.altenerding.biber.pinkie.business.weeklyimage.entity.WeeklyImage;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class WeeklyImageProvider {

	private Logger logger;
	@PersistenceContext
	private EntityManager em;

	public List<WeeklyImage> getlatestWeeklyImages() {
		logger.info("Loading the lastest not archived weekly images");
		return em.createNamedQuery("WeeklyImage.findAllNotArchived", WeeklyImage.class).getResultList();
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
