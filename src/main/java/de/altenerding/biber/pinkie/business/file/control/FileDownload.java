package de.altenerding.biber.pinkie.business.file.control;

import de.altenerding.biber.pinkie.business.file.entity.File;
import de.altenerding.biber.pinkie.business.systemproperty.SystemProperty;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownload {

	private Logger logger;
	@Inject
	@SystemProperty(name = "resourceFolder")
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
