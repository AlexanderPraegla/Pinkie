package de.altenerding.biber.pinkie.business.file.control;

import de.altenerding.biber.pinkie.business.file.entity.FileCategory;
import de.altenerding.biber.pinkie.business.file.entity.Image;
import de.altenerding.biber.pinkie.business.systemproperty.SystemProperty;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class FileDeletion {

    private Logger logger;
    @Inject
    @SystemProperty(name = "resourceFolder")
    private String resourceFolder;


    public void deleteImage(Image image) {
        logger.info("Deleting image with id={}", image.getId());
        String filePath = resourceFolder + FileCategory.ALBUMS.getDirectoryPath() + image.getFileName();
        logger.warn("File \'{}\' will be not deleted from disk yet!", filePath);
    }


    @Inject
    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
