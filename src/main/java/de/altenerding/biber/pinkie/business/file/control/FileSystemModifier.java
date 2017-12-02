package de.altenerding.biber.pinkie.business.file.control;

import de.altenerding.biber.pinkie.business.file.entity.FileCategory;
import de.altenerding.biber.pinkie.business.systemproperty.SystemProperty;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemModifier {

	@Inject
	private Logger logger;
	@Inject
	@SystemProperty(name = "resourceFolder")
	private String resourceFolder;

	public void createFolder(FileCategory category, String folder) throws Exception {
		Path path = Paths.get(resourceFolder + category.getDirectoryPath() + folder);

		if (Files.notExists(path)) {
			logger.info("Creating directory: \'{}\'", path);
			Files.createDirectory(path);
		}
	}
}
