package de.altenerding.biber.pinkie.business.file.control;

import de.altenerding.biber.pinkie.business.config.entity.Config;
import de.altenerding.biber.pinkie.business.file.entity.File;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.nio.file.Path;
import java.nio.file.Paths;

import static de.altenerding.biber.pinkie.business.config.entity.ConfigProperty.RESOURCE_FOLDER;

public class FileDownload {

	private Logger logger;
	@Inject
	@Config(RESOURCE_FOLDER)
	private String resourceFolder;
	@PersistenceContext
	private EntityManager em;

	public java.io.File getFileById(long fileId) {
		File file = em.find(File.class, fileId);
		Path path = Paths.get(resourceFolder + file.getFullFilePath());
		return path.toFile();
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
