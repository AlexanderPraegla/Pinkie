package de.altenerding.biber.pinkie.presentation.announcement;

import de.altenerding.biber.pinkie.business.announcement.boundary.AnnouncementService;
import de.altenerding.biber.pinkie.business.announcement.entity.Announcement;
import net.bootsfaces.utils.FacesMessages;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
public class AnnouncementBean implements Serializable {

	private AnnouncementService announcementService;
	private Logger logger;
	private List<Announcement> announcements;

	@PostConstruct
	public void init() {
		try {
			announcements = announcementService.getAnnouncements();
		} catch (Exception e) {
			logger.error("Error while loading announcements", e);
			FacesMessages.error("Fehler beim Laden der Ankündigungen");
		}
	}

	public List<Announcement> getAnnouncements() {
		logger.info("Getting all announcements");
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