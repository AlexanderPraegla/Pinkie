package de.altenerding.biber.pinkie.presentation.announcement;

import de.altenerding.biber.pinkie.business.announcement.boundary.AnnouncementService;
import de.altenerding.biber.pinkie.business.announcement.entity.Announcement;
import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.FileDirectory;
import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import net.bootsfaces.utils.FacesMessages;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.Serializable;

@Named
@ViewScoped
public class AnnouncementProcessingBean implements Serializable {

	private AnnouncementService announcementService;
	private FileService fileService;
	private Logger logger;
	private Announcement anncouncement = new Announcement();
	private Part file;
	private String documentDisplayedName;

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

	@Access(role = Role.ADMIN)
	public String saveAnnouncement() {
		try {
			if (file != null) {
				String fileName = fileService.uploadFile(file, FileDirectory.ANNOUNCEMENT_DOCUMENTS);
				anncouncement.setAnnouncementDocument(fileName);
				fileName = StringUtils.isBlank(documentDisplayedName) ? fileName : documentDisplayedName;
				anncouncement.setDocumentDisplayedName(fileName);
			}

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
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public String getDocumentDisplayedName() {
		return documentDisplayedName;
	}

	public void setDocumentDisplayedName(String documentDisplayedName) {
		this.documentDisplayedName = documentDisplayedName;
	}
}
