package de.altenerding.biber.pinkie.business.file.entity;

import java.util.HashMap;
import java.util.Map;

public enum FileCategory {
	ALBUMS("/albums"),

	DOCUMENTS_ANNOUNCEMENT("documents/announcement/"),

	VIDEOS("videos/"),

	IMAGES_MAINPAGE("images/mainpage/"),
	IMAGES_REPORT("images/reports/"),
	IMAGES_MEMBER_PROFILE("images/memberProfile/"),
	IMAGES_REFEREE_PROFILE("images/refereeProfile/"),
	IMAGES_DEAN_PROFILE("images/deanProfile/"),
	IMAGES_TEAM_GROUP("images/teamGroup/"),
	IMAGES_TRAINER_GROUP("images/trainerGroup/"),
	IMAGES_REFEREE_GROUP("images/refereeGroup/")
	;


	public static final Map<String, FileCategory> lookUp = new HashMap<>();

	static {
		for (FileCategory fileCategory : FileCategory.values()) {
			lookUp.put(fileCategory.directoryPath, fileCategory);
		}
	}

	private final String directoryPath;

	FileCategory(String directoryPath) {
		this.directoryPath = directoryPath;
	}

	public String getDirectoryPath() {
		return directoryPath;
	}

	public static FileCategory get(String directoryPath) {
		return lookUp.get(directoryPath);
	}
}
