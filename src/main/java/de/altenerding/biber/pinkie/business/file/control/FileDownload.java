package de.altenerding.biber.pinkie.business.file.control;

import de.altenerding.biber.pinkie.business.file.entity.File;
import de.altenerding.biber.pinkie.business.systemproperty.SystemProperty;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
	@PersistenceContext
	private EntityManager em;

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

	public Path downloadById(String fileId, ServletOutputStream outputStream)  throws Exception  {
		File file = em.find(File.class, fileId);
		Path path = Paths.get(resourceFolder + file.getFullFilePath());

		logger.info("Download file={}", path.toString());
		// Call the download method with the given file

		if (Files.notExists(path)) {
			throw new NoSuchFileException(String.format("File with id=%s does not exist", fileId));
		}

		Files.copy(path, outputStream);
		return path;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
