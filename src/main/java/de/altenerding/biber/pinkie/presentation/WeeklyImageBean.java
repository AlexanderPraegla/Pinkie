package de.altenerding.biber.pinkie.presentation;

import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.FileDirectory;
import de.altenerding.biber.pinkie.business.weeklyimage.boundary.WeeklyImageService;
import de.altenerding.biber.pinkie.business.weeklyimage.entity.WeeklyImage;
import net.bootsfaces.utils.FacesMessages;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.Part;
import java.util.List;

@ManagedBean
@RequestScoped
public class WeeklyImageBean {

	private Logger logger;
	private Part file;
	private WeeklyImage weeklyImage = new WeeklyImage();
	private List<WeeklyImage> weeklyImages;
	private FileService fileService;
	private WeeklyImageService weeklyImageService;


	public String saveWeeklyImage() {
		try {
			String fileName = fileService.uploadImage(file, FileDirectory.WEEKLY_IMAGE);
			weeklyImage.setFileName(fileName);
			weeklyImageService.saveWeeklyImage(weeklyImage);
			FacesMessages.info( "Bild der Woche hinzugefügt");
		} catch (Exception e) {
			logger.error("Error while saving announcement with id={}", weeklyImage.getId(), e);
			FacesMessages.error(e.getMessage());
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return "index.xhtml?faces-redirect=true";
	}

	public String archive(long imageId) {
		weeklyImageService.archiveWeeklyImage(imageId);
		FacesMessages.info( "Bild archiviert");
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return "index.xhtml?faces-redirect=true";
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Inject
	public void setWeeklyImageService(WeeklyImageService weeklyImageService) {
		this.weeklyImageService = weeklyImageService;
	}

	@Inject
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	public void setWeeklyImage(WeeklyImage weeklyImage) {
		this.weeklyImage = weeklyImage;
	}

	public WeeklyImage getWeeklyImage() {
		return weeklyImage;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public List<WeeklyImage> getWeeklyImages() {
		if (weeklyImages == null) {
			weeklyImages = weeklyImageService.getlatestWeeklyImages();
		}
		return weeklyImages;
	}

	public void setWeeklyImages(List<WeeklyImage> weeklyImages) {
		this.weeklyImages = weeklyImages;
	}
}
