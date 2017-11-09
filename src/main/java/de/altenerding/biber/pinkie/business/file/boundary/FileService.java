package de.altenerding.biber.pinkie.business.file.boundary;

import de.altenerding.biber.pinkie.business.file.control.FileDownload;
import de.altenerding.biber.pinkie.business.file.control.FileMappingControl;
import de.altenerding.biber.pinkie.business.file.control.FileUpload;
import de.altenerding.biber.pinkie.business.file.entity.FileDirectory;
import de.altenerding.biber.pinkie.business.file.entity.FileMapping;
import de.altenerding.biber.pinkie.business.systemproperty.SystemProperty;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
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

	@PostConstruct
	public void init() {
		//Check if file directories exists
		for (FileDirectory fileDirectory : FileDirectory.values()) {
			logger.info("Checking if directory={} in path={} exists", fileDirectory.getName(), resourceFolder);
			Path folderPath = Paths.get(resourceFolder + fileDirectory.getName());
			if (Files.notExists(folderPath)) {
				logger.info("Directory \'{}\' does not exist", fileDirectory.getName());
				try {
					Files.createDirectory(folderPath);
					logger.info("Directory \'{}\' created", fileDirectory.getName());
				} catch (IOException e) {
					logger.error("Could not create directory \'{}\'", fileDirectory.getName(), e);
				}
			} else {
				logger.info("Directory \'{}\' exists", fileDirectory.getName());
			}
		}
	}

	public String uploadFile(Part file, FileDirectory directory) throws Exception {
		return fileUpload.upload(file, directory);
	}

	public String uploadImage(Part file, FileDirectory directory) throws Exception {
		validateFileMimeType(file, "image");
		return fileUpload.upload(file, directory);
	}

	@SuppressWarnings("SameParameterValue")
	private void validateFileMimeType(Part file, String fileType) throws Exception {
		if (file == null || file.getSize() <= 0 || file.getContentType().isEmpty()) {
			throw new Exception("Bitte ein gültiges Bild");
		} else if (!file.getContentType().startsWith(fileType)) {
			throw new Exception("Bitte eine Bilddatei auswählen");
		}
	}

	public Path download(String fileName, ServletOutputStream outputStream) throws Exception {
		return fileDownload.download(fileName, outputStream);
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
