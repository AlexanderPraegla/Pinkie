package de.altenerding.biber.pinkie.business.file.control;

import de.altenerding.biber.pinkie.business.file.entity.FileCategory;
import de.altenerding.biber.pinkie.business.systemproperty.SystemProperty;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUpload {

    private static final String THUMBNAIL_TYPE_FORMAT = "jpg";
	private Logger logger;
	@Inject
	@SystemProperty(name = "resourceFolder")
	private String resourceFolder;

	public String upload(Part file, FileCategory directory) throws Exception {
		return upload(file, directory.getDirectoryPath());
	}

    public String upload(Part file, String directoryPath) throws Exception {
        String folder = resourceFolder + directoryPath;
        String fileName = getFileName(file);

        logger.info("Upload File '{}' to {}", fileName, folder);

        String filePath = folder + File.separator + fileName;
        try (InputStream filecontent = file.getInputStream()) {
            Files.copy(filecontent, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        }
        logger.info("Upload File '{}' to {} successful", fileName, folder);

        return fileName;
    }

    public String uploadThumbnail(Part file, FileCategory directory) throws Exception {
        return uploadThumbnail(file, directory.getDirectoryPath(), directory.getThumbnailTargetSize());
    }

    public String uploadThumbnail(Part file, String directoryPath, int targetSize) throws Exception {
        String folder = resourceFolder + directoryPath;
        String fileName = getThumbnailFileName(file);

        if (fileName == null) {
            throw new Exception("No valid file name available");
        }

        logger.info("Creating thumbnail '{}' in folder '{}'", fileName, folder);


        try (InputStream in = file.getInputStream()) {
            BufferedImage image = ImageIO.read(in);
            BufferedImage resize = Scalr.resize(image, Scalr.Method.QUALITY,
                    targetSize, Scalr.OP_ANTIALIAS);

            String filePath = folder + File.separator + fileName;
            ImageIO.write(resize, THUMBNAIL_TYPE_FORMAT, Files.newOutputStream(Paths.get(filePath)));
        }

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

    private String getThumbnailFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                String filename = content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
                String name = filename.substring(0, filename.lastIndexOf("."));
                return name + "_thumb." + THUMBNAIL_TYPE_FORMAT;
            }
        }
        return null;
    }

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
