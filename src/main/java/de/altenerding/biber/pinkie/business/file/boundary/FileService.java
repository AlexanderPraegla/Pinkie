package de.altenerding.biber.pinkie.business.file.boundary;

import de.altenerding.biber.pinkie.business.file.control.FileDownload;
import de.altenerding.biber.pinkie.business.file.control.FileMappingControl;
import de.altenerding.biber.pinkie.business.file.control.FileUpload;
import de.altenerding.biber.pinkie.business.file.entity.*;
import de.altenerding.biber.pinkie.business.systemproperty.SystemProperty;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Stateless
public class FileService {

	private Logger logger;
	private FileUpload fileUpload;
	private FileDownload fileDownload;
	private FileMappingControl fileMappingControl;
	@Inject
	@SystemProperty(name = "resourceFolder")
	private String resourceFolder;
	@PersistenceContext
	private EntityManager em;

	@PostConstruct
	public void init() {
		//Check if file directories exists
		for (FileCategory fileCategory : FileCategory.values()) {
			logger.info("Checking if directory={} in path={} exists", fileCategory.getDirectoryPath(), resourceFolder);
			Path folderPath = Paths.get(resourceFolder + fileCategory.getDirectoryPath());
			if (Files.notExists(folderPath)) {
				logger.info("Directory \'{}\' does not exist", fileCategory.getDirectoryPath());
				try {
					Files.createDirectory(folderPath);
					logger.info("Directory \'{}\' created", fileCategory.getDirectoryPath());
				} catch (IOException e) {
					logger.error("Could not create directory \'{}\'", fileCategory.getDirectoryPath(), e);
				}
			} else {
				logger.info("Directory \'{}\' exists", fileCategory.getDirectoryPath());
			}
		}
	}

	public Image uploadImage(Part file, FileCategory directory, String description) throws Exception {
		validateFileMimeType(file, "image");
		String fileName = fileUpload.upload(file, directory);
		Image image = new Image(directory, fileName, description);
		em.persist(image);
		em.flush();
		em.detach(image);
		return image;
	}

	public Video uploadVideo(Part file, FileCategory directory, String description) throws Exception {
		String fileName = fileUpload.upload(file, directory);
		Video video = new Video(directory, fileName, description);
		em.persist(video);
		em.flush();
		em.detach(video);
		return video;
	}

	public Document uploadDocument(Part file, FileCategory directory, String displayedName) throws Exception {
		String fileName = fileUpload.upload(file, directory);
		displayedName = displayedName == null ? fileName : displayedName;
		Document document = new Document(directory, fileName, displayedName);
		em.persist(document);
		em.flush();
		em.detach(document);
		return document;
	}

	@SuppressWarnings("SameParameterValue")
	private void validateFileMimeType(Part file, String fileType) throws Exception {
		if (file == null || file.getSize() <= 0 || file.getContentType().isEmpty()) {
			throw new Exception("Bitte ein gültiges Bild");
		} else if (!file.getContentType().startsWith(fileType)) {
			throw new Exception("Bitte eine Bilddatei auswählen");
		}
	}

	public java.io.File getFileById(String fileId) throws Exception {
		return fileDownload.getFileById(fileId);
	}

	public FileMapping getFileMappingbyKeyPage(String page, String key) throws Exception {
		return fileMappingControl.getFileMappingbyKeyPage(page, key);
	}

	public void replaceFileMapping(FileMapping fileMapping) {
		fileMappingControl.replaceFileMapping(fileMapping);
	}

	public void updateFileMapping(FileMapping fileMapping) {
		fileMappingControl.updateFileMapping(fileMapping);
	}

	@Inject
	public void setFileUpload(FileUpload fileUpload) {
		this.fileUpload = fileUpload;
	}

	@Inject
	public void setFileDownload(FileDownload fileDownload) {
		this.fileDownload = fileDownload;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Inject
	public void setFileMappingControl(FileMappingControl fileMappingControl) {
		this.fileMappingControl = fileMappingControl;
	}
}
