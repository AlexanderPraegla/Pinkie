package de.altenerding.biber.pinkie.business.file.control;

import de.altenerding.biber.pinkie.business.systemproperty.SystemProperty;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownload {

	private Logger logger;
	@Inject
	@SystemProperty(name = "resourceFolder")
	private String resourceFolder;

	public Path download(String fileName, ServletOutputStream outputStream) throws Exception {
		Path path = Paths.get(resourceFolder + fileName);
		logger.info("Download file={}", path.toString());
		// Call the download method with the given file

		if (Files.notExists(path)) {
			throw new NoSuchFileException(String.format("File %s does not exist", fileName));
		}

		Files.copy(path, outputStream);
		return path;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
