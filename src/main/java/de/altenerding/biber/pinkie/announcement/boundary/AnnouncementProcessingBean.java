package de.altenerding.biber.pinkie.announcement.boundary;

import de.altenerding.biber.pinkie.announcement.entity.Announcement;
import net.bootsfaces.utils.FacesMessages;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class AnnouncementProcessingBean implements Serializable {

	private AnnouncementService announcementService;
	private Logger logger;
	private Announcement anncouncement = new Announcement();

	public String saveEditedAnnouncement() {
		try {
			announcementService.updateAnnouncement(anncouncement);
			FacesMessages.info("Ankündigung erfolgreich aktualisiert");
		} catch (Exception e) {
			logger.error("Error while updating announcement with id={}", anncouncement.getId(), e);
			FacesMessages.error("Es ist ein Fehler beim Speichern der Ankündigung aufgetreten");
		}
		return "index.xhtml";
	}

	public void deleteAnnouncement() {
		try {
			announcementService.deleteAnnouncement(anncouncement);
			FacesMessages.info("Ankündigung erfolgreich gelöscht");
		} catch (Exception e) {
			logger.error("Error while deleting announcement with id={}", anncouncement.getId(), e);
			FacesMessages.error("Es ist ein Fehler beim löschen der Ankündigung aufgetreten");
		}
	}

	public void saveAnnouncement() {
		try {
 			announcementService.saveAnnouncement(anncouncement);
			FacesMessages.info( "Ankündigung erfolgreich gespeichert");
		} catch (Exception e) {
			logger.error("Error while saving announcement with id={}", anncouncement.getId(), e);
			FacesMessages.error("Es ist ein Fehler beim speichern der Ankündigung aufgetreten");
		}
	}

	public Announcement getAnncouncement() {
		return anncouncement;
	}

	public void setAnncouncement(Announcement anncouncement) {
		this.anncouncement = anncouncement;
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
