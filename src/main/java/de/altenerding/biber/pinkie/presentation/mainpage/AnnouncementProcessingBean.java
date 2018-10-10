package de.altenerding.biber.pinkie.presentation.mainpage;

import de.altenerding.biber.pinkie.business.announcement.boundary.AnnouncementService;
import de.altenerding.biber.pinkie.business.announcement.entity.Announcement;
import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.Document;
import de.altenerding.biber.pinkie.business.file.entity.FileCategory;
import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import de.altenerding.biber.pinkie.presentation.session.UserSessionBean;
import net.bootsfaces.utils.FacesMessages;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.Serializable;

@Named
@RequestScoped
public class AnnouncementProcessingBean implements Serializable {

	private AnnouncementService announcementService;
	private FileService fileService;
	private Logger logger;
	private Announcement anncouncement = new Announcement();
	private Part file;
	private String documentDisplayedName;
    private UserSessionBean userSessionBean;

	@Access(role = Role.PRESS)
	public String saveEditedAnnouncement(Announcement announcement) {
		try {
			uploadAnnouncementAttachment(announcement);

			announcementService.updateAnnouncement(announcement);
			FacesMessages.info("News aktualisiert");
		} catch (Exception e) {
			logger.error("Error while updating announcement with id={}", announcement.getId(), e);
			FacesMessages.error("Es ist ein Fehler beim Speichern der News aufgetreten");
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return "success";
	}

	@Access(role = Role.PRESS)
	public String archiveAnnouncement(Announcement anncouncement) {
		try {
			announcementService.archiveAnnouncement(anncouncement);
			FacesMessages.info("News archiviert");
		} catch (Exception e) {
			logger.error("Error while deleting announcement with id={}", anncouncement.getId(), e);
			FacesMessages.error("Es ist ein Fehler beim löschen der News aufgetreten");
		}
		return "success";
	}

    @Access(role = Role.PRESS)
    public String removeAnnouncementAttachment(Announcement anncouncement) {
        try {
            Document document = anncouncement.getDocument();
            anncouncement.setDocument(null);
            announcementService.updateAnnouncement(anncouncement);
            fileService.deleteDocument(document);
            FacesMessages.info("News archiviert");
        } catch (Exception e) {
            logger.error("Error while deleting announcement with id={}", anncouncement.getId(), e);
            FacesMessages.error("Es ist ein Fehler beim löschen der News aufgetreten");
        }
        return "success";
    }

	@Access(role = Role.PRESS)
	public String saveAnnouncement() {
		try {
			uploadAnnouncementAttachment(anncouncement);

            anncouncement.setAuthor(userSessionBean.getMember());
			announcementService.saveAnnouncement(anncouncement);
			FacesMessages.info( "News gespeichert");
		} catch (Exception e) {
			logger.error("Error while saving announcement with id={}", anncouncement.getId(), e);
			FacesMessages.error("Es ist ein Fehler beim speichern der News aufgetreten");
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return "success";
	}

	private void uploadAnnouncementAttachment(Announcement anncouncement) throws Exception {
		if (file != null) {
			Document document = fileService.uploadDocument(file, FileCategory.DOCUMENTS_ANNOUNCEMENT, documentDisplayedName);
			anncouncement.setDocument(document);
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
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

    @Inject
    public void setUserSessionBean(UserSessionBean userSessionBean) {
        this.userSessionBean = userSessionBean;
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
