package de.altenerding.biber.pinkie.announcement.boundary;

import de.altenerding.biber.pinkie.announcement.entity.Announcement;
import net.bootsfaces.utils.FacesMessages;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class AnnouncementProcessingBean implements Serializable {

	@ManagedProperty(value="#{announcementBean}")
	private AnnouncementBean announcementBean;
	private AnnouncementService announcementService;
	private Logger logger;
	private Announcement anncouncement = new Announcement();

	public String saveEditedAnnouncement() {
		try {
			announcementService.updateAnnouncement(anncouncement);
			FacesMessages.info("Ankündigung erfolgreich aktualisiert");
			announcementBean.reloadAnncouncements();
		} catch (Exception e) {
			logger.error("Error while updating announcement with id={}", anncouncement.getId(), e);
			FacesMessages.error("Es ist ein Fehler beim Speichern der Ankündigung aufgetreten");
		}
		return "index.xhtml";
	}

	public String deleteAnnouncement() {
		try {
			announcementService.deleteAnnouncement(anncouncement);
			FacesMessages.info("Ankündigung erfolgreich gelöscht");
			announcementBean.reloadAnncouncements();
		} catch (Exception e) {
			logger.error("Error while deleting announcement with id={}", anncouncement.getId(), e);
			FacesMessages.error("Es ist ein Fehler beim löschen der Ankündigung aufgetreten");
		}
		return "index.xhtml";
	}

	public void saveAnnouncement() {
		try {
 			announcementService.saveAnnouncement(anncouncement);
			FacesMessages.info("Ankündigung erfolgreich gespeichert");
			announcementBean.reloadAnncouncements();
		} catch (Exception e) {
			logger.error("Error while saving announcement with id={}", anncouncement.getId(), e);
			FacesMessages.error("Es ist ein Fehler beim speichern der Ankündigung aufgetreten");
		}
//		return "index.xhtml";
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

	public void setAnnouncementBean(AnnouncementBean announcementBean) {
		this.announcementBean = announcementBean;
	}
}
