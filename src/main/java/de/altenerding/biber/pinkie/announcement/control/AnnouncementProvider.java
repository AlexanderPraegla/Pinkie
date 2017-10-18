package de.altenerding.biber.pinkie.announcement.control;

import de.altenerding.biber.pinkie.announcement.entity.Announcement;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class AnnouncementProvider {

	@PersistenceContext
	private EntityManager em;
	private Logger logger;

	public List<Announcement> getAnnouncements() {
		logger.info("Loading all announcements from database");
		return em.createNamedQuery("Announcement.findAll", Announcement.class).getResultList();
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
