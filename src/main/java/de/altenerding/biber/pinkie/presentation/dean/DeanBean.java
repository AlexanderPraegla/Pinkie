package de.altenerding.biber.pinkie.presentation.dean;

import de.altenerding.biber.pinkie.business.dean.boundary.DeanService;
import de.altenerding.biber.pinkie.business.dean.entity.Dean;
import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.FileDirectory;
import net.bootsfaces.utils.FacesMessages;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.Part;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@RequestScoped
public class DeanBean implements Serializable {

	@ManagedProperty(value = "#{param.deanId}")
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
			result = "deans.xhtml?faces-redirect=true";
		} catch (Exception e) {
			logger.error("Error while updating dean", e);
			FacesMessages.error("Es ist ein Fehler beim aktualisieren aufgetreten");
			result = "deanEdit?faces-redirect=true&includeViewParams=true&deanId=" + deanId;
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return result;
	}

	public String createDean() {
		deanService.createDean(this.dean);
		FacesMessages.info(dean.getDescription(), "Erstellt");
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return "deans.xhtml?faces-redirect=true";
	}

	public String updateDeansOrder() {
		logger.info("Updating deans order");
		deanService.updateDeans(deans);
		FacesMessages.info("Reihenfolge aktualisiert");
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return "deans.xhtml?faces-redirect=true";
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
