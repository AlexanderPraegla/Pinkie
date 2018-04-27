package de.altenerding.biber.pinkie.business.announcement.control;

import de.altenerding.biber.pinkie.business.announcement.entity.Announcement;
import de.altenerding.biber.pinkie.business.notification.control.MessageSender;
import de.altenerding.biber.pinkie.business.notification.entity.NotificationType;
import de.altenerding.biber.pinkie.business.notification.entity.Placeholder;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AnnouncementProcessor {

	@PersistenceContext
	private EntityManager em;
	private Logger logger;
    @Inject
    private MessageSender messageSender;


	public void saveAnnouncement(Announcement anncouncement) throws Exception {
		logger.info("Creating new announcement");
		em.persist(anncouncement);
		em.flush();
		logger.info("Created announcement with id={}", anncouncement.getId());

        Map<Placeholder, String> placeholders = new HashMap<>();
        messageSender.sendNotifications(NotificationType.ANNOUNCEMENT, placeholders);
	}

	public void updateAnnouncement(Announcement announcement) throws Exception {
		logger.info("Updating announcement with id={}", announcement.getId());
		em.merge(announcement);
		em.flush();
	}

	public void archiveAnnouncement(Announcement announcement) throws Exception {
		logger.info("Deleting announcement with id={}", announcement.getId());
		announcement.setArchivedOn(new Date());
		em.merge(announcement);
		em.flush();
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
