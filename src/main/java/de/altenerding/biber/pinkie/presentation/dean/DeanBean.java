package de.altenerding.biber.pinkie.presentation.dean;

import de.altenerding.biber.pinkie.business.dean.boundary.DeanService;
import de.altenerding.biber.pinkie.business.dean.entity.Dean;
import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.FileDirectory;
import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Role;
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
public class DeanBean implements Serializable {

	private long deanId;
	private Logger logger;
	private DeanService deanService;
	private FileService fileService;
	private List<Dean> deans;
	private Dean dean = new Dean();
	private Part file;

	public void initDean() {
		logger.info("Initializing dean");
		dean = deanService.getDeanById(deanId);
	}

	@Access(role = Role.ADMIN)
	public String updateDean() {
		logger.info("Updating dean");
		String result;
		try {
			Dean dean = deanService.getDeanById(deanId);
			dean.setDescription(this.dean.getDescription());

			if (!this.dean.getMember().equals(dean.getMember())) {
				dean.setMember(this.dean.getMember());
				dean.setDeanImage(null);
			}

			if (file != null) {
				String fileName = fileService.uploadImage(file, FileDirectory.PROFILE_IMAGE);
				dean.setDeanImage(fileName);
			}

			deanService.updateDean(dean);
			FacesMessages.info(dean.getDescription(), "Aktualisieret");
			result = "/public/club/deans.xhtml?faces-redirect=true";
		} catch (Exception e) {
			logger.error("Error while updating dean", e);
			FacesMessages.error("Es ist ein Fehler beim aktualisieren aufgetreten");
			result = "/secure/dean/deanEdit?faces-redirect=true&includeViewParams=true&deanId=" + deanId;
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return result;
	}

	@Access(role = Role.ADMIN)
	public String createDean() {
		deanService.createDean(this.dean);
		FacesMessages.info(dean.getDescription(), "Erstellt");
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return "/public/club/deans.xhtml?faces-redirect=true";
	}

	@Access(role = Role.ADMIN)
	public String updateDeansOrder() {
		logger.info("Updating deans order");
		deanService.updateDeans(deans);
		FacesMessages.info("Reihenfolge aktualisiert");
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return "/public/club/deans.xhtml?faces-redirect=true";
	}

	@Access(role = Role.ADMIN)
	public String archiveDean(Dean dean) {
		logger.info("Archiving dean");
		dean.setArchivedOn(new Date());
		deanService.updateDean(dean);
		FacesMessages.info(dean.getDescription(), "Archiviert");
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return "/secure/dean/deansEdit.xhtml?faces-redirect=true";
	}

	@Inject
	public void setDeanService(DeanService deanService) {
		this.deanService = deanService;
	}

	@Inject
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public List<Dean> getDeans() {
		if (deans == null) {
			deans = deanService.getCurrentDeans();
		}
		return deans;
	}

	public void setDeans(List<Dean> deans) {
		this.deans = deans;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public long getDeanId() {
		return deanId;
	}

	public void setDeanId(long deanId) {
		this.deanId = deanId;
	}

	public Dean getDean() {
		return dean;
	}

	public void setDean(Dean dean) {
		this.dean = dean;
	}
}
