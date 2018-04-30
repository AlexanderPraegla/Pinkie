package de.altenerding.biber.pinkie.business.file.boundary;

import de.altenerding.biber.pinkie.business.file.control.FileDeletion;
import de.altenerding.biber.pinkie.business.file.control.FileDownload;
import de.altenerding.biber.pinkie.business.file.control.FileMappingControl;
import de.altenerding.biber.pinkie.business.file.control.FileSystemModifier;
import de.altenerding.biber.pinkie.business.file.control.FileUpload;
import de.altenerding.biber.pinkie.business.file.entity.Document;
import de.altenerding.biber.pinkie.business.file.entity.FileCategory;
import de.altenerding.biber.pinkie.business.file.entity.FileMapping;
import de.altenerding.biber.pinkie.business.file.entity.Image;
import de.altenerding.biber.pinkie.business.file.entity.Mapping;
import de.altenerding.biber.pinkie.business.file.entity.TextMapping;
import de.altenerding.biber.pinkie.business.file.entity.Video;
import de.altenerding.biber.pinkie.business.systemproperty.SystemProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Stateless
public class FileService {

	private Logger logger;
	private FileUpload fileUpload;
	private FileDownload fileDownload;
	private FileMappingControl fileMappingControl;
	@Inject
	private FileSystemModifier fileSystemModifier;
    @Inject
    private FileDeletion fileDeletion;
	@Inject
	@SystemProperty(name = "resourceFolder")
	private String resourceFolder;
	@PersistenceContext
	private EntityManager em;

	@PostConstruct
	public void init() {
		//Check if file directories exists
		for (FileCategory fileCategory : FileCategory.values()) {
			logger.info("Checking if directory={} in path={} exists", fileCategory.getDirectoryPath(), resourceFolder);
			Path folderPath = Paths.get(resourceFolder + fileCategory.getDirectoryPath());
			if (Files.notExists(folderPath)) {
				logger.info("Directory \'{}\' does not exist", fileCategory.getDirectoryPath());
				try {
					Files.createDirectories(folderPath);
					logger.info("Directory \'{}\' created", fileCategory.getDirectoryPath());
				} catch (IOException e) {
					logger.error("Could not create directory \'{}\'", fileCategory.getDirectoryPath(), e);
				}
			} else {
				logger.info("Directory \'{}\' exists", fileCategory.getDirectoryPath());
			}
		}
	}

	public Image uploadImage(Part file, FileCategory directory) throws Exception {
		return uploadImage(file, directory, null);
	}

	public Image uploadImage(Part file, FileCategory directory, String description) throws Exception {
		String fileName = fileUpload.upload(file, directory);
		Image image = new Image(directory, fileName, description);
		em.persist(image);
		em.flush();
		em.detach(image);
		return image;
	}

	public Image uploadAlbumImage(Part file, String folder) throws Exception {
		String fileName = fileUpload.upload(file, FileCategory.ALBUMS.getDirectoryPath() + folder + File.separator);
		fileName = folder + File.separator + fileName;
		Image image = new Image(FileCategory.ALBUMS, fileName, null);
		em.persist(image);
		em.flush();
		em.detach(image);
		return image;
	}

	public Video uploadVideo(Part file, FileCategory directory, String description) throws Exception {
		String fileName = fileUpload.upload(file, directory);
		Video video = new Video(directory, fileName, description);
		em.persist(video);
		em.flush();
		em.detach(video);
		return video;
	}

	public Document uploadDocument(Part file, FileCategory directory, String displayedName) throws Exception {
		String fileName = fileUpload.upload(file, directory);
		displayedName = StringUtils.isBlank(displayedName) ? fileName : displayedName;
		Document document = new Document(directory, fileName, displayedName);
		em.persist(document);
		em.flush();
		em.detach(document);
		return document;
	}

    public void deleteImage(Image image) {
        image = em.merge(image);
        em.remove(image);
        em.flush();
        fileDeletion.deleteImage(image);
    }

	public java.io.File getFileById(long fileId) throws Exception {
		return fileDownload.getFileById(fileId);
	}

	public Map<String, List<Mapping>> getMappingForPage(String page) {
		return fileMappingControl.getMappingForPage(page);
	}

	public void updateMapping(Mapping mapping) {
		fileMappingControl.updateMapping(mapping);
	}

	public FileMapping getSingleFileMapping(String page, String key) {
		return fileMappingControl.getSingleFileMapping(page, key);
	}

	public TextMapping getSingleTextMapping(String page, String key) {
		return fileMappingControl.getSingleTextMapping(page, key);
	}

	public List<FileMapping> getMultipeFileMappings(String page, String key) {
		return fileMappingControl.getMultipeFileMappings(page, key);
	}

	public void createFolder(FileCategory category, String folder) throws Exception {
		fileSystemModifier.createFolder(category, folder);
	}

	@Inject
	public void setFileUpload(FileUpload fileUpload) {
		this.fileUpload = fileUpload;
	}

	@Inject
	public void setFileDownload(FileDownload fileDownload) {
		this.fileDownload = fileDownload;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Inject
	public void setFileMappingControl(FileMappingControl fileMappingControl) {
		this.fileMappingControl = fileMappingControl;
	}
}
