package de.altenerding.biber.pinkie.business.config;

public enum FileFolder {
	IMAGE_OF_WEEK("/file/imageOfWeek/"),
	TEAM_IMAGE("/file/teamImages/"),
	PROFILE_IMAGE("/file/profileImages/"),
	DOCUMTENTS("/file/documents/");

	private final String name;

	FileFolder(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
