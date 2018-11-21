package de.altenerding.biber.pinkie.business.file.control;

import de.altenerding.biber.pinkie.business.config.entity.Config;
import de.altenerding.biber.pinkie.business.file.entity.File;
import de.altenerding.biber.pinkie.business.file.entity.FileCategory;
import de.altenerding.biber.pinkie.business.file.entity.Image;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static de.altenerding.biber.pinkie.business.config.entity.ConfigProperty.RESOURCE_FOLDER;

public class FileDeletion {

    private Logger logger;
    @Inject
    @Config(RESOURCE_FOLDER)
    private String resourceFolder;


    public void deleteImage(Image image) throws IOException {
        logger.info("Deleting image with id={}", image.getId());
        String filePath = resourceFolder + FileCategory.ALBUMS.getDirectoryPath() + image.getFileName();
        Path path = Paths.get(filePath);
        Files.delete(path);
        logger.info("Deleted successfully image with id={}", image.getId());
    }

    public void deleteFile(File file) throws IOException {
        logger.info("Deleting image with id={}", file.getId());
        String filePath = resourceFolder + file.getDirectory().getDirectoryPath() + file.getFileName();
        Path path = Paths.get(filePath);
        Files.delete(path);
        logger.info("Deleted successfully file with id={}", file.getId());
    }


    @Inject
    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
