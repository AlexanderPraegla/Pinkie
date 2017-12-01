package de.altenerding.biber.pinkie.presentation.mainpage;

import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.FileCategory;
import de.altenerding.biber.pinkie.business.file.entity.FileMapping;
import de.altenerding.biber.pinkie.business.file.entity.Video;
import de.altenerding.biber.pinkie.business.members.entity.Access;
import de.altenerding.biber.pinkie.business.members.entity.Role;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
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

	@PostConstruct
	public void init() {
	}

	public FileMapping getVideoMapping() {
		return fileService.getSingleFileMapping(START_PAGE_NAME, START_PAGE_VIDEO_KEY);
	}

	@Access(role = Role.PRESS)
	public String uploadVideo() throws Exception {
		logger.info("Uploading new video");
		Video video = fileService.uploadVideo(file, FileCategory.VIDEOS, videoDescription);
		FileMapping videoMapping = getVideoMapping();

		if (videoMapping == null) {
			videoMapping = new FileMapping();
			videoMapping.setPage(START_PAGE_NAME);
			videoMapping.setKey(START_PAGE_VIDEO_KEY);
		}

		videoMapping.setFile(video);

		fileService.updateMapping(videoMapping);
		return "startpage";
	}

	@Access(role = Role.PRESS)
	public String archiveVideo() {
		logger.info("Archiving video of mainpage");
		FileMapping video = getVideoMapping();
		video.setArchivedOn(new Date());
		fileService.updateMapping(video);
		return "startpage";
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Inject
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
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

	public FileMapping getFileMapping() {
		return fileMapping;
	}

	public void setFileMapping(FileMapping fileMapping) {
		this.fileMapping = fileMapping;
	}
}
