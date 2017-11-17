package de.altenerding.biber.pinkie.presentation.announcement;

import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.FileCategory;
import de.altenerding.biber.pinkie.business.file.entity.FileMapping;
import de.altenerding.biber.pinkie.business.file.entity.Video;
import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.util.Date;

@Named
@RequestScoped
public class VideoBean {

	private static final String START_PAGE_VIDEO_KEY = "startpage.video";
	private static final String START_PAGE_NAME = "index.xhtml";
	private FileService fileService;
	private Logger logger;
	private FileMapping fileMapping;
	private String videoDescription;
	private Part file;

	@Inject
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public FileMapping getFileMapping() throws Exception {
		logger.info("Loading video reference for mainpage");
		if (fileMapping == null) {
			fileMapping = fileService.getFileMappingbyKeyPage(START_PAGE_NAME, START_PAGE_VIDEO_KEY);
		}

		return fileMapping;
	}

	@Access(role = Role.PRESS)
	public String uploadVideo() throws Exception {
		logger.info("Uploading new video");
		Video video = fileService.uploadVideo(file, FileCategory.VIDEOS, videoDescription);
		FileMapping fileMapping = new FileMapping();
		fileMapping.setFile(video);
		fileMapping.setPage(START_PAGE_NAME);
		fileMapping.setKey(START_PAGE_VIDEO_KEY);
		fileService.replaceFileMapping(fileMapping);

		return "/index.xhtml?faces-redirect=true";
	}

	@Access(role = Role.PRESS)
	public String archiveVideo(FileMapping fileMapping) {
		logger.info("Archiving video of mainpage");
		fileMapping.setArchivedOn(new Date());
		fileService.updateFileMapping(fileMapping);
		return "/index.xhtml?faces-redirect=true";
	}

	public void setFileMapping(FileMapping fileMapping) {
		this.fileMapping = fileMapping;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public String getVideoDescription() {
		return videoDescription;
	}

	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}
}
