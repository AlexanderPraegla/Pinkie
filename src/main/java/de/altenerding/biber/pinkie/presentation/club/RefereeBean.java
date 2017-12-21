package de.altenerding.biber.pinkie.presentation.club;

import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.FileCategory;
import de.altenerding.biber.pinkie.business.file.entity.FileMapping;
import de.altenerding.biber.pinkie.business.file.entity.Image;
import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import de.altenerding.biber.pinkie.business.referee.boundary.RefereeService;
import de.altenerding.biber.pinkie.business.referee.entity.Referee;
import net.bootsfaces.utils.FacesMessages;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named
@RequestScoped
public class RefereeBean implements Serializable {

	private static final String REFEREE_IMAGE_GROUP_PICTURE_KEY = "referees.group.picture";
	private static final String REFEREES_PAGE_NAME = "referees.xhtml";
	private long refereeId;
	private Logger logger;
	private RefereeService refereeService;
	private FileService fileService;
	private List<Referee> referees;
	private Referee referee = new Referee();
	private Part file;

	public void initReferee() {
		logger.info("Initializing referee");
		referee = refereeService.getRefereeById(refereeId);
	}

	public FileMapping getImageMapping() {
		return fileService.getSingleFileMapping(REFEREES_PAGE_NAME, REFEREE_IMAGE_GROUP_PICTURE_KEY);
	}

	@Access(role = Role.ADMIN)
	public String updateReferee() {
		logger.info("Updating referee");
		String result;
		try {
			Referee referee = refereeService.getRefereeById(refereeId);

			if (!this.referee.getMember().equals(referee.getMember())) {
				referee.setMember(this.referee.getMember());
				referee.setImage(null);
			}

			if (file != null) {
				Image image = fileService.uploadImage(file, FileCategory.IMAGES_REFEREE_PROFILE);
				referee.setImage(image);
			}

			refereeService.updateReferee(referee);
			FacesMessages.info("Schiedsrichter aktualisieret");
			result = "/public/club/referees.xhtml?faces-redirect=true";
		} catch (Exception e) {
			logger.error("Error while updating referee", e);
			FacesMessages.error("Es ist ein Fehler beim aktualisieren aufgetreten");
			result = "/secure/referee/refereeEdit?faces-redirect=true&includeViewParams=true&refereeId=" + refereeId;
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return result;
	}

	@Access(role = Role.ADMIN)
	public String createReferee() {
		refereeService.createReferee(this.referee);
		FacesMessages.info("Schiedsrichter erstellt");
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return "/public/club/referees.xhtml?faces-redirect=true";
	}

	@Access(role = Role.ADMIN)
	public String updateRefereesOrder() {
		logger.info("Updating referees order");
		refereeService.updateReferees(referees);
		FacesMessages.info("Reihenfolge aktualisiert");
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return "/public/club/referees.xhtml?faces-redirect=true";
	}

	@Access(role = Role.ADMIN)
	public String archiveReferee(Referee referee) {
		logger.info("Archiving referee");
		referee.setArchivedOn(new Date());
		refereeService.updateReferee(referee);
		FacesMessages.info("Schiedsrichter archiviert");
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return "/secure/referee/refereesEdit.xhtml?faces-redirect=true";
	}

	@Access(role = Role.PRESS)
	public String uploadRefereeGroupImage() throws Exception {
		logger.info("Uploading new group image for referees");
		Image image = fileService.uploadImage(file, FileCategory.IMAGES_REFEREE_GROUP);
		FileMapping imageMapping = getImageMapping();

		if (imageMapping == null) {
			imageMapping = new FileMapping();
		}
		imageMapping.setKey(REFEREE_IMAGE_GROUP_PICTURE_KEY);
		imageMapping.setPage(REFEREES_PAGE_NAME);
		imageMapping.setFile(image);

		fileService.updateMapping(imageMapping);
		return "/public/club/referees.xhtml?faces-redirect=true";
	}

	@Inject
	public void setRefereeService(RefereeService refereeService) {
		this.refereeService = refereeService;
	}

	@Inject
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public List<Referee> getReferees() {
		if (referees == null) {
			referees = refereeService.getCurrentReferees();
		}
		return referees;
	}

	public void setReferees(List<Referee> referees) {
		this.referees = referees;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public long getRefereeId() {
		return refereeId;
	}

	public void setRefereeId(long refereeId) {
		this.refereeId = refereeId;
	}

	public Referee getReferee() {
		return referee;
	}

	public void setReferee(Referee referee) {
		this.referee = referee;
	}
}
