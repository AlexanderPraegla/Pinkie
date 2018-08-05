package de.altenerding.biber.pinkie.business.weeklyimage.control;

import de.altenerding.biber.pinkie.business.notification.control.MessageSender;
import de.altenerding.biber.pinkie.business.notification.entity.NotificationType;
import de.altenerding.biber.pinkie.business.notification.entity.Placeholder;
import de.altenerding.biber.pinkie.business.weeklyimage.entity.WeeklyImage;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;

public class WeeklyImageProcessor {

	private Logger logger;
	@PersistenceContext
	private EntityManager em;
	@Inject
	private MessageSender messageSender;

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

		Map<Placeholder, String> placeholders = new HashMap<>();
		messageSender.sendNotifications(NotificationType.WEEKLY_IMAGE_UPLOADED, placeholders);
	}

	public void updateText(WeeklyImage weeklyImage) {
		logger.info("Updating text for weekly image with id={}", weeklyImage.getId());
		em.merge(weeklyImage);
		em.flush();
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
