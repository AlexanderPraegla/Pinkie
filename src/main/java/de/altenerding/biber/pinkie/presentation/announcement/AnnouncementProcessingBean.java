package de.altenerding.biber.pinkie.presentation.announcement;

import de.altenerding.biber.pinkie.business.announcement.boundary.AnnouncementService;
import de.altenerding.biber.pinkie.business.announcement.entity.Announcement;
import net.bootsfaces.utils.FacesMessages;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
			FacesMessages.info("Ankündigung aktualisiert");
		} catch (Exception e) {
			logger.error("Error while updating announcement with id={}", anncouncement.getId(), e);
			FacesMessages.error("Es ist ein Fehler beim Speichern der Ankündigung aufgetreten");
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return "index.xhtml";
	}

	public void deleteAnnouncement() {
		try {
			announcementService.deleteAnnouncement(anncouncement);
			FacesMessages.info("Ankündigung gelöscht");
		} catch (Exception e) {
			logger.error("Error while deleting announcement with id={}", anncouncement.getId(), e);
			FacesMessages.error("Es ist ein Fehler beim löschen der Ankündigung aufgetreten");
		}
	}

	public String saveAnnouncement() {
		try {
 			announcementService.saveAnnouncement(anncouncement);
			FacesMessages.info( "Ankündigung gespeichert");
		} catch (Exception e) {
			logger.error("Error while saving announcement with id={}", anncouncement.getId(), e);
			FacesMessages.error("Es ist ein Fehler beim speichern der Ankündigung aufgetreten");
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return "index.xhtml?faces-redirect=true";
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
