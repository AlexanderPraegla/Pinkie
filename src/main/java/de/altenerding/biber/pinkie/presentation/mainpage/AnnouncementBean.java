package de.altenerding.biber.pinkie.presentation.mainpage;

import de.altenerding.biber.pinkie.business.announcement.boundary.AnnouncementService;
import de.altenerding.biber.pinkie.business.announcement.entity.Announcement;
import net.bootsfaces.utils.FacesMessages;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class AnnouncementBean implements Serializable {

	private AnnouncementService announcementService;
	private Logger logger;
	private List<Announcement> announcements;

	@PostConstruct
	public void init() {
		try {
			announcements = announcementService.getLatestAnnouncements();
		} catch (Exception e) {
			logger.error("Error while loading announcements", e);
			FacesMessages.error("Fehler beim Laden der News");
		}
	}

	public List<Announcement> getAnnouncements() {
		if (announcements == null) {
			init();
		}

		return announcements;
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
