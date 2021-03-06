package de.altenerding.biber.pinkie.business.file.control;

import de.altenerding.biber.pinkie.business.config.entity.Config;
import de.altenerding.biber.pinkie.business.file.entity.FileCategory;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static de.altenerding.biber.pinkie.business.config.entity.ConfigProperty.RESOURCE_FOLDER;

public class FileUpload {

    private Logger logger;
    @Inject
    @Config(RESOURCE_FOLDER)
    private String resourceFolder;

    public String upload(Part file, FileCategory directory) throws Exception {
        return upload(file, directory.getDirectoryPath());
    }

    public String upload(Part file, String directoryPath) throws Exception {
        String folder = resourceFolder + directoryPath;
        String fileName = getFileName(file);

        logger.info("Upload File '{}' to {}", fileName, folder);

        String filePath = folder + File.separator + fileName;
        Path target = Paths.get(filePath);

        /*
        Checking if a file with the same name already exists.
        If the file exists a suffix like e.g. _1 is added, counting upwards
         */
        int counter = 1;
        String tmpFileName = fileName;
        while (Files.exists(target)) {
            tmpFileName = addFilenameSuffix(fileName, String.valueOf(counter));
            filePath = folder + File.separator + tmpFileName;
            target = Paths.get(filePath);
            counter++;
        }
        fileName = tmpFileName;

        try (InputStream filecontent = file.getInputStream()) {
            Files.copy(filecontent, target, StandardCopyOption.REPLACE_EXISTING);
        }
        logger.info("Upload File '{}' to {} successful", fileName, folder);

        return fileName;
    }

    public String uploadThumbnail(Part file, FileCategory directory) throws Exception {
        return uploadThumbnail(file, directory.getDirectoryPath(), directory.getThumbnailTargetSize());
    }

    public String uploadThumbnail(Part file, String directoryPath, int targetSize) throws Exception {
        String targetImageFolder = resourceFolder + directoryPath;
        String fileName = getThumbnailFileName(file);

        if (fileName == null) {
            throw new Exception("No valid file name available");
        }

        logger.info("Creating thumbnail '{}' in targetImageFolder '{}'", fileName, targetImageFolder);


        try (InputStream in = file.getInputStream()) {
            BufferedImage image = ImageIO.read(in);
            image = dropAlphaChannel(image);
            BufferedImage resize = Scalr.resize(image, Scalr.Method.QUALITY,
                    targetSize, Scalr.OP_ANTIALIAS);
            resize = dropAlphaChannel(resize);

            String filePath = targetImageFolder + File.separator + fileName;
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            ImageIO.write(resize, extension, Files.newOutputStream(Paths.get(filePath)));
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

    private BufferedImage dropAlphaChannel(BufferedImage src) {
        BufferedImage convertedImg = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);
        convertedImg.getGraphics().drawImage(src, 0, 0, null);

        return convertedImg;
    }

    private String getThumbnailFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                String filename = content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
                return addFilenameSuffix(filename, "thumb");
            }
        }
        return null;
    }

    private String addFilenameSuffix(String filename, String suffix) {
        String name = filename.substring(0, filename.lastIndexOf("."));
        String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
        return name + "_" + suffix + "." + extension;
    }

    @Inject
    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
