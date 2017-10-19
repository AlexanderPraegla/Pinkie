package de.altenerding.biber.pinkie.business.announcement.boundary;

import de.altenerding.biber.pinkie.business.announcement.control.AnnouncementProcessor;
import de.altenerding.biber.pinkie.business.announcement.control.AnnouncementProvider;
import de.altenerding.biber.pinkie.business.announcement.entity.Announcement;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class AnnouncementService {

	private AnnouncementProvider announcementProvider;
	private AnnouncementProcessor announcementProcessor;


	public List<Announcement> getAnnouncements() throws Exception {
		return announcementProvider.getAnnouncements();
	}

	public void saveAnnouncement(Announcement anncouncement) throws Exception {
		announcementProcessor.saveAnnouncement(anncouncement);
	}

	public void updateAnnouncement(Announcement announcement) throws Exception {
		announcementProcessor.updateAnnouncement(announcement);
	}

	public void deleteAnnouncement(Announcement announcement) throws Exception {
		announcementProcessor.deleteAnnouncement(announcement);
	}

	@Inject
	public void setAnnouncementProvider(AnnouncementProvider announcementProvider) {
		this.announcementProvider = announcementProvider;
	}

	@Inject
	public void setAnnouncementProcessor(AnnouncementProcessor announcementProcessor) {
		this.announcementProcessor = announcementProcessor;
	}
}
