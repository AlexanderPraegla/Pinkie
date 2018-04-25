package de.altenerding.biber.pinkie.business.notification.entity;

public enum Placeholder {
	FIRSTNAME("[FIRSTNAME]"),
    PASSWORD("[PASSWORD]"),
    TEAM_NAME("[TEAM_NAME]"),
    REPORT_TITLE("[REPORT_TITLE]"),
	AUTHOR("[AUTHOR]"),
	ALBUM_TITLE("[ALBUM_TITLE]"),
	ALIAS("[ALIAS]");

	private final String placeholder;

	Placeholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public String getPlaceholder() {
		return placeholder;
	}
}
