package de.altenerding.biber.pinkie.announcement.boundary;

import de.altenerding.biber.pinkie.announcement.control.AnnouncementProvider;
import de.altenerding.biber.pinkie.announcement.entity.Announcement;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class AnnouncementService {

	private AnnouncementProvider announcementProvider;


	public List<Announcement> getAnnouncements() {
		return announcementProvider.getAnnouncements();
	}

	@Inject
	public void setAnnouncementProvider(AnnouncementProvider announcementProvider) {
		this.announcementProvider = announcementProvider;
	}
}
