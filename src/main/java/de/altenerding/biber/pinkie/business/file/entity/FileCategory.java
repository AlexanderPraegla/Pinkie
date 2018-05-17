package de.altenerding.biber.pinkie.business.file.entity;

import java.util.HashMap;
import java.util.Map;

public enum FileCategory {
    SPONSOR("sponsors/", 600),

    ALBUMS("albums/", 400),

    DOCUMENTS_ANNOUNCEMENT("documents/announcement/", 0),
    DOCUMENTS("documents/global/", 0),

    VIDEOS("videos/", 0),

    IMAGES("images/global/", 600),
    IMAGES_MAINPAGE("images/mainpage/", 600),
    IMAGES_REPORT("images/reports/", 300),
    IMAGES_MEMBER_PROFILE("images/memberProfile/", 300),
    IMAGES_REFEREE_PROFILE("images/refereeProfile/", 300),
    IMAGES_DEAN_PROFILE("images/deanProfile/", 300),
    IMAGES_TEAM_GROUP("images/teamGroup/", 800),
    IMAGES_TRAINER_GROUP("images/trainerGroup/", 800),
    IMAGES_REFEREE_GROUP("images/refereeGroup/", 800);

    private final int thumbnailTargetSize;

	public static final Map<String, FileCategory> lookUp = new HashMap<>();

	static {
		for (FileCategory fileCategory : FileCategory.values()) {
			lookUp.put(fileCategory.directoryPath, fileCategory);
		}
	}

	private final String directoryPath;

    FileCategory(String directoryPath, int thumbnailTargetSize) {
		this.directoryPath = directoryPath;
        this.thumbnailTargetSize = thumbnailTargetSize;
	}

	public String getDirectoryPath() {
		return directoryPath;
	}

	public static FileCategory get(String directoryPath) {
		return lookUp.get(directoryPath);
	}

    public int getThumbnailTargetSize() {
        return thumbnailTargetSize;
    }
}
