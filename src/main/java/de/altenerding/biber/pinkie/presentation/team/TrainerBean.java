package de.altenerding.biber.pinkie.presentation.team;

import de.altenerding.biber.pinkie.business.file.boundary.FileService;
import de.altenerding.biber.pinkie.business.file.entity.FileDirectory;
import de.altenerding.biber.pinkie.business.file.entity.FileMapping;
import de.altenerding.biber.pinkie.business.members.entity.Member;
import de.altenerding.biber.pinkie.business.team.boundary.TeamService;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.util.List;


@Named
@RequestScoped
public class TrainerBean {

	private static final String TRAINER_GROUP_PICTURE_KEY = "trainers.group.picture";
	private static final String TRAINERS_PAGE_NAME = "trainers.xhtml";

	private TeamService teamService;
	private FileService fileService;
	private Logger logger;
	private List<Member> trainers;
	private FileMapping fileMapping;
	private String fileDescription;
	private Part file;

	public String uploadTrainerGroupImage() throws Exception {
		logger.info("Uploading new group image for trainers");
		if (file != null) {
			String fileName = fileService.uploadImage(file, FileDirectory.IMAGES);
			fileMapping.setImageFilePath(fileName);
			fileMapping.setKey(TRAINER_GROUP_PICTURE_KEY);
			fileMapping.setPage(TRAINERS_PAGE_NAME);
			fileService.replaceFileMapping(fileMapping);
		} else {
			fileService.updateFileMapping(fileMapping);
		}

		return "trainers.xhtml?faces-redirect=true";
	}

	@Inject
	public void setTeamService(TeamService teamService) {
		this.teamService = teamService;
	}

	@Inject
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public List<Member> getTrainers() {
		if (trainers == null) {
			trainers = teamService.getAllTrainers();
		}
		return trainers;
	}

	public void setTrainers(List<Member> trainers) {
		this.trainers = trainers;
	}

	public FileMapping getFileMapping() {
		if (fileMapping == null) {
			fileMapping = fileService.getFileMappingbyKeyPage(TRAINERS_PAGE_NAME, TRAINER_GROUP_PICTURE_KEY);
		}

		return fileMapping;
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

	public String getFileDescription() {
		return fileDescription;
	}

	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}
}
