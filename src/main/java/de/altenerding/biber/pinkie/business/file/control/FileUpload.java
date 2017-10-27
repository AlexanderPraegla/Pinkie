package de.altenerding.biber.pinkie.business.file.control;

import de.altenerding.biber.pinkie.business.file.entity.FileDirectory;
import de.altenerding.biber.pinkie.business.systemproperty.SystemProperty;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUpload {

	private Logger logger;
	@Inject
	@SystemProperty(name = "resourceFolder")
	private String resourceFolder;

	public String upload(Part file) throws Exception {
		String folder = resourceFolder + FileDirectory.TEAM_IMAGE.getName();
		String fileName = getFileName(file);

			logger.info("Upload File '{}' to {}", fileName, folder);

		String filePath = folder + File.separator + fileName;
		try (InputStream filecontent = file.getInputStream()) {
			Files.copy(filecontent, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
		}
		logger.info("File upload to {} successful", filePath);
		return fileName;
	}

	private String getFileName(final Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(
						content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
