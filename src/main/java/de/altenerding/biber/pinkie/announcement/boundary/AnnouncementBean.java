package de.altenerding.biber.pinkie.announcement.boundary;

import de.altenerding.biber.pinkie.announcement.entity.Announcement;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ManagedBean
public class AnnouncementBean implements Serializable {

	private AnnouncementService announcementService;
	private Logger logger;

	public List<Announcement> getAnnouncements() {
		logger.info("Loading all announcements");
		return announcementService.getAnnouncements();
	}


	@Inject
	public void setAnnouncementService(AnnouncementService announcementService) {
		this.announcementService = announcementService;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
